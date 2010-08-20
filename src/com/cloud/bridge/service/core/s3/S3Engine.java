/*
 * Copyright 2010 Cloud.com, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cloud.bridge.service.core.s3;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import javax.activation.DataHandler;

import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.Session;

import com.cloud.bridge.model.MHost;
import com.cloud.bridge.model.MHostMount;
import com.cloud.bridge.model.SAcl;
import com.cloud.bridge.model.SBucket;
import com.cloud.bridge.model.SHost;
import com.cloud.bridge.model.SMeta;
import com.cloud.bridge.model.SObject;
import com.cloud.bridge.model.SObjectItem;
import com.cloud.bridge.persist.PersistContext;
import com.cloud.bridge.persist.dao.MHostDao;
import com.cloud.bridge.persist.dao.MHostMountDao;
import com.cloud.bridge.persist.dao.SAclDao;
import com.cloud.bridge.persist.dao.SBucketDao;
import com.cloud.bridge.persist.dao.SHostDao;
import com.cloud.bridge.persist.dao.SMetaDao;
import com.cloud.bridge.persist.dao.SObjectDao;
import com.cloud.bridge.persist.dao.SObjectItemDao;
import com.cloud.bridge.service.S3BucketAdapter;
import com.cloud.bridge.service.S3FileSystemBucketAdapter;
import com.cloud.bridge.service.ServiceProvider;
import com.cloud.bridge.service.UserContext;
import com.cloud.bridge.service.exception.HostNotMountedException;
import com.cloud.bridge.service.exception.InternalErrorException;
import com.cloud.bridge.service.exception.NoSuchObjectException;
import com.cloud.bridge.service.exception.ObjectAlreadyExistsException;
import com.cloud.bridge.service.exception.OutOfServiceException;
import com.cloud.bridge.service.exception.OutOfStorageException;
import com.cloud.bridge.util.DateHelper;
import com.cloud.bridge.util.StringHelper;
import com.cloud.bridge.util.Tuple;

/**
 * @author Kelven Yang
 */
public class S3Engine {
    protected final static Logger logger = Logger.getLogger(S3Engine.class);
    
    private final int LOCK_ACQUIRING_TIMEOUT_SECONDS = 10;		// ten seconds
	
    private final Map<Integer, S3BucketAdapter> bucketAdapters = new HashMap<Integer, S3BucketAdapter>();
    
    public S3Engine() {
    	bucketAdapters.put(SHost.STORAGE_HOST_TYPE_LOCAL, new S3FileSystemBucketAdapter());
    }
    
    public S3CreateBucketResponse handleRequest(S3CreateBucketRequest request) {
    	S3CreateBucketResponse response = new S3CreateBucketResponse();
    	response.setBucketName(request.getBucketName());
    	
		if(PersistContext.acquireNamedLock("bucket.creation", LOCK_ACQUIRING_TIMEOUT_SECONDS)) {
			Tuple<SHost, String> shostTuple = null;
			boolean success = false;
			try {
				SBucketDao bucketDao = new SBucketDao();
				
				if(bucketDao.getByName(request.getBucketName()) != null)
					throw new ObjectAlreadyExistsException("Bucket already exists"); 
					
				shostTuple = allocBucketStorageHost(request.getBucketName());
				
				SBucket sbucket = new SBucket();
				sbucket.setName(request.getBucketName());
				sbucket.setCreateTime(DateHelper.currentGMTTime());
				sbucket.setOwnerCanonicalId(UserContext.current().getCanonicalUserId());
				sbucket.setShost(shostTuple.getFirst());
				shostTuple.getFirst().getBuckets().add(sbucket);
				bucketDao.save(sbucket);
				
				// explicitly commit the transaction
				PersistContext.commitTransaction();
				success = true;
			} finally {
				if(!success && shostTuple != null) {
					S3BucketAdapter bucketAdapter =  getStorageHostBucketAdapter(shostTuple.getFirst());
					bucketAdapter.deleteContainer(shostTuple.getSecond(), request.getBucketName());
				}
				PersistContext.releaseNamedLock("bucket.creation");
			}
		} else {
			throw new OutOfServiceException("Unable to acquire synchronization lock");
		}
		
		return response;
    }
    
    public S3Response handleRequest( S3DeleteBucketRequest request ) 
    {
    	S3Response response  = new S3Response();   	
		SBucketDao bucketDao = new SBucketDao();
		SBucket sbucket = bucketDao.getByName(request.getBucketName());
		
		if ( sbucket != null ) 
		{
			 Tuple<SHost, String> tupleBucketHost = getBucketStorageHost(sbucket);
			 S3BucketAdapter bucketAdapter = getStorageHostBucketAdapter(tupleBucketHost.getFirst());			
			 bucketAdapter.deleteContainer(tupleBucketHost.getSecond(), request.getBucketName());
			
			 // Cascade-deleting can delete related SObject/SObjectItem objects, but not SAcl and SMeta objects. We
			 // need to perform deletion of these objects related to bucket manually.
			 //
			 // Delete SMeta & SAcl objects: (1)Get all the objects in the bucket, (2)then all the items in each object, (3) then all meta & acl data for each item
			 Set<SObject> objectsInBucket = sbucket.getObjectsInBucket();
			 Iterator it = objectsInBucket.iterator();
			 while( it.hasNext()) 
			 {
			 	SObject oneObject = (SObject)it.next();
				Set<SObjectItem> itemsInObject = oneObject.getItems();
				Iterator is = itemsInObject.iterator();
				while( is.hasNext()) 
				{
					SObjectItem oneItem = (SObjectItem)is.next();
		    		deleteMetaData( oneItem.getId());
		    		deleteObjectAcls( oneItem.getId());
				}				
			 }			 
			 deleteBucketAcls( sbucket.getId());
			 bucketDao.delete( sbucket );		
			 response.setResultCode(204);
			 response.setResultDescription("OK");
		} 
		else 
		{    response.setResultCode(404);
			 response.setResultDescription("Bucket does not exist");
		}
    	return response;
    }
    
    public S3ListBucketResponse handleRequest(S3ListBucketRequest request) {
    	S3ListBucketResponse response = new S3ListBucketResponse();
		String bucketName = request.getBucketName();
		String prefix = request.getPrefix();
		if(prefix == null)
			prefix = StringHelper.EMPTY_STRING;
		String marker = request.getMarker();
		if(marker == null)
			marker = StringHelper.EMPTY_STRING;
			
		String delimiter = request.getDelimiter();
		int maxKeys = request.getMaxKeys();
		if(maxKeys <= 0)
			maxKeys = 1000;
		
		SBucketDao bucketDao = new SBucketDao();
		SBucket sbucket = bucketDao.getByName(bucketName);
		if(sbucket == null) 
			throw new NoSuchObjectException("Bucket " + bucketName + " does not exist");
		
		SObjectDao sobjectDao = new SObjectDao();
		
		// when we query, request one more item so that we know how to set isTruncated flag 
		List<SObject> l = sobjectDao.listBucketObjects(sbucket, prefix, marker, maxKeys + 1);   
		response.setBucketName(bucketName);
		response.setMarker(marker);
		response.setMaxKeys(maxKeys);
		response.setPrefix(prefix);
		response.setDelimiter(delimiter);
		response.setTruncated(l.size() > maxKeys);
		if(l.size() > maxKeys) {
			response.setNextMarker(l.get(l.size() - 1).getNameKey());
		}

		// SOAP response does not support versioning
		response.setContents(composeListBucketContentEntries(l, prefix, delimiter, maxKeys, false));
		response.setCommonPrefixes(composeListBucketPrefixEntries(l, prefix, delimiter, maxKeys));
		return response;
    }
    
    public S3ListAllMyBucketsResponse handleRequest(S3ListAllMyBucketsRequest request) {
    	S3ListAllMyBucketsResponse response = new S3ListAllMyBucketsResponse();
    	
    	SBucketDao bucketDao = new SBucketDao();
    	
    	List<SBucket> buckets = bucketDao.listBuckets(UserContext.current().getCanonicalUserId());
    	S3CanonicalUser owner = new S3CanonicalUser();
    	owner.setID(UserContext.current().getCanonicalUserId());
    	owner.setDisplayName("");
    	response.setOwner(owner);
    	
    	if(buckets != null) {
    		S3ListAllMyBucketsEntry[] entries = new S3ListAllMyBucketsEntry[buckets.size()];
    		int i = 0;
    		for(SBucket bucket : buckets) {
    			entries[i] = new S3ListAllMyBucketsEntry();
    			entries[i].setName(bucket.getName());
    			entries[i].setCreationDate(DateHelper.toCalendar(bucket.getCreateTime()));
    			i++;
    		}
    		
    		response.setBuckets(entries);
    	}
    	
    	return response;
    }
    
    public S3Response handleRequest(S3SetBucketAccessControlPolicyRequest request) {
    	S3Response response = new S3Response();
    	
    	SBucketDao bucketDao = new SBucketDao();
    	SBucket sbucket = bucketDao.getByName(request.getBucketName());
    	if(sbucket == null) {
    		response.setResultCode(404);
    		response.setResultDescription("Bucket does not exist");
    		return response;
    	}
 
    	SAclDao aclDao = new SAclDao();
    	aclDao.save("SBucket", sbucket.getId(), request.getAcl());
   	
    	response.setResultCode(200);
    	response.setResultDescription("OK");
    	return response;
    }
    
    public S3AccessControlPolicy handleRequest(S3GetBucketAccessControlPolicyRequest request) {
    	S3AccessControlPolicy policy = new S3AccessControlPolicy();
    	
    	SBucketDao bucketDao = new SBucketDao();
    	SBucket sbucket = bucketDao.getByName(request.getBucketName());
    	if(sbucket == null)
    		throw new NoSuchObjectException("Bucket " + request.getBucketName() + " does not exist");
    	
    	S3CanonicalUser owner = new S3CanonicalUser();
    	owner.setID(sbucket.getOwnerCanonicalId());
    	owner.setDisplayName("");
    	policy.setOwner(owner);
    	
    	SAclDao aclDao = new SAclDao();
    	List<SAcl> grants = aclDao.listGrants("SBucket", sbucket.getId());
    	policy.setGrants(S3Grant.toGrants(grants));
    	
    	return policy;
    }
    
    public S3PutObjectInlineResponse handleRequest(S3PutObjectInlineRequest request) {
    	S3PutObjectInlineResponse response = new S3PutObjectInlineResponse();
    	
		String bucketName = request.getBucketName();
		String key = request.getKey();
		long contentLength = request.getContentLength();
		S3MetaDataEntry[] meta = request.getMetaEntries();
		S3AccessControlList acl = request.getAcl();
		
		SBucketDao bucketDao = new SBucketDao();
		SBucket bucket = bucketDao.getByName(bucketName);
		if (bucket == null) throw new NoSuchObjectException("Bucket " + bucketName + " does not exist");

		Tuple<SObject, SObjectItem> tupleObjectItem = allocObjectItem(bucket, key, meta, acl);
		Tuple<SHost, String> tupleBucketHost = getBucketStorageHost(bucket);
		
		S3BucketAdapter bucketAdapter = getStorageHostBucketAdapter(tupleBucketHost.getFirst());
		String itemFileName = tupleObjectItem.getSecond().getStoredPath();
		DataHandler dataHandler = request.getData();
		InputStream is = null;
		try {
			// explicit transaction control to avoid holding transaction during file-copy process
			PersistContext.commitTransaction();
			
			is = dataHandler.getInputStream();
			String md5Checksum = bucketAdapter.saveObject(is, tupleBucketHost.getSecond(), bucket.getName(), itemFileName);
			response.setETag(md5Checksum);
			response.setLastModified(DateHelper.toCalendar( tupleObjectItem.getSecond().getLastModifiedTime()));
	        response.setVersion( tupleObjectItem.getSecond().getVersion());
			
			SObjectItemDao itemDao = new SObjectItemDao();
			SObjectItem item = itemDao.get( tupleObjectItem.getSecond().getId());
			item.setMd5(md5Checksum);
			item.setStoredSize(contentLength);
			PersistContext.getSession().save(item);
		} catch (IOException e) {
			logger.error("PutObjectInline failed due to " + e.getMessage(), e);
		} catch (OutOfStorageException e) {
			logger.error("PutObjectInline failed due to " + e.getMessage(), e);
		} finally {
			if(is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.error("Unable to close stream from data handler.", e);
				}
			}
		}
    	
    	return response;
    }

    public S3PutObjectResponse handleRequest(S3PutObjectRequest request) {
    	S3PutObjectResponse response = new S3PutObjectResponse();
    	
		String bucketName = request.getBucketName();
		String key = request.getKey();
		long contentLength = request.getContentLength();
		S3MetaDataEntry[] meta = request.getMetaEntries();
		S3AccessControlList acl = request.getAcl();
		
		SBucketDao bucketDao = new SBucketDao();
		SBucket bucket = bucketDao.getByName(bucketName);
		if(bucket == null) throw new NoSuchObjectException("Bucket " + bucketName + " does not exist");
		
		Tuple<SObject, SObjectItem> tupleObjectItem = allocObjectItem(bucket, key, meta, acl);
		Tuple<SHost, String> tupleBucketHost = getBucketStorageHost(bucket);
		
		S3BucketAdapter bucketAdapter =  getStorageHostBucketAdapter(tupleBucketHost.getFirst());
		String itemFileName = tupleObjectItem.getSecond().getStoredPath();
		InputStream is = null;
		try {
			// explicit transaction control to avoid holding transaction during file-copy process
			PersistContext.commitTransaction();
			
			is = request.getInputStream();
			String md5Checksum = bucketAdapter.saveObject(is, tupleBucketHost.getSecond(), bucket.getName(), itemFileName);
			response.setETag(md5Checksum);
			response.setLastModified(DateHelper.toCalendar( tupleObjectItem.getSecond().getLastModifiedTime()));
			
			SObjectItemDao itemDao = new SObjectItemDao();
			SObjectItem item = itemDao.get(tupleObjectItem.getSecond().getId());
			item.setMd5(md5Checksum);
			item.setStoredSize(contentLength);
			PersistContext.getSession().save(item);
		} catch (OutOfStorageException e) {
			logger.error("PutObject failed due to " + e.getMessage(), e);
		} finally {
			if(is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.error("Unable to close stream from data handler.", e);
				}
			}
		}
    	
    	return response;
    }

    public S3Response handleRequest(S3SetObjectAccessControlPolicyRequest request) {
    	S3Response response = new S3Response();
    	
    	SBucketDao bucketDao = new SBucketDao();
    	SBucket sbucket = bucketDao.getByName(request.getBucketName());
    	if(sbucket == null) {
    		response.setResultCode(404);
    		response.setResultDescription("Bucket " + request.getBucketName() + "does not exist");
    		return response;
    	}
    	
    	SObjectDao sobjectDao = new SObjectDao();
    	SObject sobject = sobjectDao.getByNameKey(sbucket, request.getKey());
    	
    	if(sobject == null) {
    		response.setResultCode(404);
    		response.setResultDescription("Object " + request.getKey() + " in bucket " + request.getBucketName() + " does not exist");
    		return response;
    	}
    	
    	SAclDao aclDao = new SAclDao();
    	aclDao.save("SObject", sobject.getId(), request.getAcl());
    	
    	response.setResultCode(200);
    	response.setResultDescription("OK");
    	return response;
    }
    
    public S3AccessControlPolicy handleRequest(S3GetObjectAccessControlPolicyRequest request) {
    	S3AccessControlPolicy policy = new S3AccessControlPolicy();
    	
    	SBucketDao bucketDao = new SBucketDao();
    	SBucket sbucket = bucketDao.getByName(request.getBucketName());
    	if(sbucket == null)
    		throw new NoSuchObjectException("Bucket " + request.getBucketName() + " does not exist");
    	
    	SObjectDao sobjectDao = new SObjectDao();
    	SObject sobject = sobjectDao.getByNameKey(sbucket, request.getKey());
    	if(sobject == null)
    		throw new NoSuchObjectException("Object " + request.getKey() + " does not exist");
    	
    	S3CanonicalUser owner = new S3CanonicalUser();
    	owner.setID(sobject.getOwnerCanonicalId());
    	owner.setDisplayName("");
    	policy.setOwner(owner);
    	
    	SAclDao aclDao = new SAclDao();
    	List<SAcl> grants = aclDao.listGrants("SObject", sobject.getId());
    	policy.setGrants(S3Grant.toGrants(grants));
    	
    	return policy;
    }
    
    public S3GetObjectResponse handleRequest(S3GetObjectRequest request) {
    	S3GetObjectResponse response = new S3GetObjectResponse();
    	int resultCode = 200;

    	// [A] Verify that the bucket and the object exist
		SBucketDao bucketDao = new SBucketDao();
		SBucket sbucket = bucketDao.getByName(request.getBucketName());
		if (sbucket == null) {
			response.setResultCode(404);
			response.setResultDescription("Bucket " + request.getBucketName() + " does not exist");
			return response;
		}
		
		SObjectDao objectDao = new SObjectDao();
		SObject sobject = objectDao.getByNameKey(sbucket, request.getKey());
		if (sobject == null) {
			response.setResultCode(404);
			response.setResultDescription("Object " + request.getKey() + " does not exist in bucket " + request.getBucketName());
			return response;
		}
		
		String deletionMark = sobject.getDeletionMark();
		if (null != deletionMark) {
		    response.setDeleteMarker( deletionMark );
			response.setResultCode(404);
			response.setResultDescription("Object " + request.getKey() + " has been deleted (1)");
			return response;
		}
		
		
		// [B] Versioning allow the client to ask for a specific version not just the latest
		SObjectItem item = null;
        int versioningStatus = sbucket.getVersioningStatus();
		String wantVersion = request.getVersion();
		if ( SBucket.VERSIONING_ENABLED == versioningStatus && null != wantVersion)
			 item = sobject.getVersion( wantVersion );
		else item = sobject.getLatestVersion(( SBucket.VERSIONING_ENABLED != versioningStatus ));    
    	
		if (item == null) {
    		response.setResultCode(404);
			response.setResultDescription("Object " + request.getKey() + " has been deleted (2)");
			return response;  		
    	}
		
		// -> extract the meta data that corresponds the specific versioned item 
		SMetaDao metaDao = new SMetaDao();
		List<SMeta> itemMetaData = metaDao.getByTarget( "SObjectItem", item.getId());
		if (null != itemMetaData) 
		{
			int i = 0;
			S3MetaDataEntry[] metaEntries = new S3MetaDataEntry[ itemMetaData.size() ];
		    ListIterator it = itemMetaData.listIterator();
		    while( it.hasNext()) {
		    	SMeta oneTag = (SMeta)it.next();
		    	S3MetaDataEntry oneEntry = new S3MetaDataEntry();
		    	oneEntry.setName( oneTag.getName());
		    	oneEntry.setValue( oneTag.getValue());
		    	metaEntries[i++] = oneEntry;
		    }
		    response.setMetaEntries( metaEntries );
		}
		
		
    	// TODO logic of IfMatch IfNoneMatch, IfModifiedSince, IfUnmodifiedSince (already done in the REST half)  	
		
		// [C] Return the contents of the object inline
		//  -> support a single byte range
		long bytesStart = request.getByteRangeStart();
		long bytesEnd   = request.getByteRangeEnd();
		if ( 0 <= bytesStart && 0 <= bytesEnd) {
		     response.setContentLength( bytesEnd - bytesStart );	
		     resultCode = 206;
		}
		else response.setContentLength( item.getStoredSize());
 
    	if(request.isReturnData()) 
    	{
    		response.setETag( item.getMd5());
    		response.setLastModified(DateHelper.toCalendar( item.getLastModifiedTime()));
    		response.setVersion( item.getVersion());
    		if (request.isInlineData()) 
    		{
    			Tuple<SHost, String> tupleSHostInfo = getBucketStorageHost(sbucket);
				S3BucketAdapter bucketAdapter = getStorageHostBucketAdapter(tupleSHostInfo.getFirst());
				
				if ( request.getByteRangeStart() >= 0 && request.getByteRangeEnd() >= 0)
					 response.setData(bucketAdapter.loadObjectRange(tupleSHostInfo.getSecond(), 
						   request.getBucketName(), item.getStoredPath(), bytesStart, bytesEnd ));
				else response.setData(bucketAdapter.loadObject(tupleSHostInfo.getSecond(), request.getBucketName(), item.getStoredPath()));
    		} 
    	}
    	
    	response.setResultCode( resultCode );
    	response.setResultDescription("OK");
    	return response;
    }
    
    /**
     * In one place we handle both versioning and non-versioning delete requests.
     */
    //@SuppressWarnings("deprecation")
	public S3Response handleRequest(S3DeleteObjectRequest request) {
		S3Response response = new S3Response();
		
		// -> verify that the bucket and object exist
		SBucketDao bucketDao = new SBucketDao();
		String bucketName = request.getBucketName();
		SBucket sbucket = bucketDao.getByName( bucketName );
		if (sbucket == null) {
			response.setResultCode(404);
			response.setResultDescription("Bucket does not exist");
			return response;
		}
		
		SObjectDao objectDao = new SObjectDao();
		SObject sobject = objectDao.getByNameKey( sbucket, request.getKey());
		if (sobject == null) {
			response.setResultCode(404);
			response.setResultDescription("Bucket does not exist");
			return response;
		}
		
		
		// -> versioning controls what delete means
		String storedPath = null;
		SObjectItem item = null;
        int versioningStatus = sbucket.getVersioningStatus();
		if ( SBucket.VERSIONING_ENABLED == versioningStatus ) 
	    {
			 String wantVersion = request.getVersion();
			 if (null == wantVersion) {
				 // -> if versioning is on and no versionId is given then we just write a deletion marker
				 sobject.setDeletionMark( UUID.randomUUID().toString());
				 objectDao.update( sobject );
			 }
			 else {	
				  // -> are we removing the delete marker?
				  String deletionMarker = sobject.getDeletionMark();
				  if (null != deletionMarker && wantVersion.equalsIgnoreCase( deletionMarker )) {
					  sobject.setDeletionMark( null );  
			    	  objectDao.update( sobject );	
			  		  response.setResultCode(204);
					  return response;
	              }
				  
				  // -> if versioning is on and the versionId is given then we delete the object matching that version
			      if ( null == (item = sobject.getVersion( wantVersion ))) {
			    	   response.setResultCode(404);
			    	   return response;
			      }
			      else {
			    	   // -> just delete the one item that matches the versionId from the database
			    	   storedPath = item.getStoredPath();
			    	   sobject.deleteItem( item.getId());
			    	   objectDao.update( sobject );	    	   
			      }
			 }
	    }
		else {
			 // -> if versioning if off then we do delete the null object
			 if ( null == (item = sobject.getLatestVersion( true ))) {
		    	  response.setResultCode(404);
		    	  return response;
		     }
		     else {
		    	  // -> if no item with a null version then we are done
		    	  if (null == item.getVersion()) {
		    	      // -> remove the entire object 
		    	      // -> cascade-deleting can delete related SObject/SObjectItem objects, but not SAcl and SMeta objects.
		    	      storedPath = item.getStoredPath();
		    		  deleteMetaData( item.getId());
		    		  deleteObjectAcls( item.getId());
		    	      objectDao.delete( sobject );
		    	  }
		     }
		}
		
		// -> delete the file holding the object
		if (null != storedPath) 
		{
			 Tuple<SHost, String> tupleBucketHost = getBucketStorageHost( sbucket );
			 S3BucketAdapter bucketAdapter =  getStorageHostBucketAdapter( tupleBucketHost.getFirst());		 
			 bucketAdapter.deleteObject( tupleBucketHost.getSecond(), bucketName, storedPath );		
		}
		
		response.setResultCode(204);
		return response;
    }
    

	private void deleteMetaData( long itemId ) {
	    SMetaDao metaDao = new SMetaDao();
	    List<SMeta> itemMetaData = metaDao.getByTarget( "SObjectItem", itemId );
	    if (null != itemMetaData) 
	    {
	        ListIterator it = itemMetaData.listIterator();
		    while( it.hasNext()) {
		       SMeta oneTag = (SMeta)it.next();
		       metaDao.delete( oneTag );
		    }
		}
	}

	private void deleteObjectAcls( long itemId ) {
	    SAclDao aclDao = new SAclDao();
	    List<SAcl> itemAclData = aclDao.listGrants( "SObject", itemId );
	    if (null != itemAclData) 
	    {
	        ListIterator it = itemAclData.listIterator();
		    while( it.hasNext()) {
		       SAcl oneTag = (SAcl)it.next();
		       aclDao.delete( oneTag );
		    }
		}
	}

	private void deleteBucketAcls( long bucketId ) {
	    SAclDao aclDao = new SAclDao();
	    List<SAcl> bucketAclData = aclDao.listGrants( "SBucket", bucketId );
	    if (null != bucketAclData) 
	    {
	        ListIterator it = bucketAclData.listIterator();
		    while( it.hasNext()) {
		       SAcl oneTag = (SAcl)it.next();
		       aclDao.delete( oneTag );
		    }
		}
	}

	private S3ListBucketPrefixEntry[] composeListBucketPrefixEntries(List<SObject> l, String prefix, String delimiter, int maxKeys) {
		List<S3ListBucketPrefixEntry> entries = new ArrayList<S3ListBucketPrefixEntry>();
		
		int count = 0;
		for(SObject sobject : l) {
			if(delimiter != null && !delimiter.isEmpty()) {
				String subName = StringHelper.substringInBetween(sobject.getNameKey(), prefix, delimiter);
				if(subName != null) {
					S3ListBucketPrefixEntry entry = new S3ListBucketPrefixEntry();
					if(prefix != null && prefix.length() > 0)
						entry.setPrefix(prefix + delimiter + subName);
					else
						entry.setPrefix(subName);
				}
			}
			
			count++;
			if(count >= maxKeys)
				break;
		}
		
		if(entries.size() > 0)
			return entries.toArray(new S3ListBucketPrefixEntry[0]);
		return null;
	}
	
	private S3ListBucketObjectEntry[] composeListBucketContentEntries(List<SObject> l, String prefix, String delimiter, int maxKeys, boolean enableVersion) {
		List<S3ListBucketObjectEntry> entries = new ArrayList<S3ListBucketObjectEntry>();
		int count = 0;
		for(SObject sobject : l) {
			if(delimiter != null && !delimiter.isEmpty()) {
				if(StringHelper.substringInBetween(sobject.getNameKey(), prefix, delimiter) != null)
					continue;
			}
			
			if(enableVersion) {
				Iterator<SObjectItem> it = sobject.getItems().iterator();
				while(it.hasNext()) {
					// TODO, should add version info
					SObjectItem item = (SObjectItem)it.next();
					entries.add(toListEntry(sobject, item));
				}
			} else {
				// this should be able to be optimized
				Iterator<SObjectItem> it = sobject.getItems().iterator();
				SObjectItem lastestItem = null;
				int maxVersion = 0;
				while(it.hasNext()) {
					SObjectItem item = (SObjectItem)it.next();
					int version = Integer.parseInt(item.getVersion());
					if(version > maxVersion) {
						maxVersion = version;
						lastestItem = item;
					}
				}
				if(lastestItem != null) {
					entries.add(toListEntry(sobject, lastestItem));
				}
			}
			
			count++;
			if(count >= maxKeys)
				break;
		}
		
		if(entries.size() > 0) 
			return entries.toArray(new S3ListBucketObjectEntry[0]);
		return null;
	}
    
	private static S3ListBucketObjectEntry toListEntry(SObject sobject, SObjectItem item) {
		// TODO, should add version info
		
		S3ListBucketObjectEntry entry = new S3ListBucketObjectEntry();
		entry.setKey(sobject.getNameKey());
		entry.setETag(item.getMd5());
		entry.setSize(item.getStoredSize());
		entry.setLastModified(DateHelper.toCalendar(item.getLastModifiedTime()));
		entry.setOwnerCanonicalId(sobject.getOwnerCanonicalId());
		entry.setOwnerDisplayName("");
		return entry;
	}
    
	public Tuple<SHost, String> getBucketStorageHost(SBucket bucket) {
		MHostMountDao mountDao = new MHostMountDao();
		
		SHost shost = bucket.getShost();
		if(shost.getHostType() == SHost.STORAGE_HOST_TYPE_LOCAL) {
			return new Tuple<SHost, String>(shost, shost.getExportRoot());
		}
		
		MHostMount mount = mountDao.getHostMount(ServiceProvider.getInstance().getManagementHostId(), shost.getId());
		if(mount != null) {
			return new Tuple<SHost, String>(shost, mount.getMountPath());
		}

		// need to redirect request to other node
		throw new HostNotMountedException("Storage host " + shost.getHost() + " is not locally mounted");
	}
    
	private Tuple<SHost, String> allocBucketStorageHost(String bucketName) {
		MHostDao mhostDao = new MHostDao();
		SHostDao shostDao = new SHostDao();
		
		MHost mhost = mhostDao.get(ServiceProvider.getInstance().getManagementHostId());
		if(mhost == null)
			throw new OutOfServiceException("Temporarily out of service");
			
		if(mhost.getMounts().size() > 0) {
			Random random = new Random();
			MHostMount[] mounts = (MHostMount[])mhost.getMounts().toArray();
			MHostMount mount = mounts[random.nextInt(mounts.length)];
			S3BucketAdapter bucketAdapter =  getStorageHostBucketAdapter(mount.getShost());
			bucketAdapter.createContainer(mount.getMountPath(), bucketName);
			return new Tuple<SHost, String>(mount.getShost(), mount.getMountPath());
		}
		
		// To make things simple, only allow one local mounted storage root
		String localStorageRoot = ServiceProvider.getInstance().getStartupProperties().getProperty("storage.root");
		if(localStorageRoot != null) {
			SHost localSHost = shostDao.getLocalStorageHost(mhost.getId(), localStorageRoot);
			if(localSHost == null)
				throw new InternalErrorException("storage.root is configured but not initialized");
			
			S3BucketAdapter bucketAdapter =  getStorageHostBucketAdapter(localSHost);
			bucketAdapter.createContainer(localSHost.getExportRoot(), bucketName);
			return new Tuple<SHost, String>(localSHost, localStorageRoot);
		}
		
		throw new OutOfStorageException("No storage host is available");
	}
	
	public S3BucketAdapter getStorageHostBucketAdapter(SHost shost) {
		S3BucketAdapter adapter = bucketAdapters.get(shost.getHostType());
		if(adapter == null) 
			throw new InternalErrorException("Bucket adapter is not installed for host type: " + shost.getHostType());
		
		return adapter;
	}

	
	@SuppressWarnings("deprecation")
	public Tuple<SObject, SObjectItem> allocObjectItem(SBucket bucket, String nameKey, S3MetaDataEntry[] meta, S3AccessControlList acl) 
	{
		SObjectDao     objectDao     = new SObjectDao();
		SObjectItemDao objectItemDao = new SObjectItemDao();
		SMetaDao       metaDao       = new SMetaDao();
		SAclDao        aclDao        = new SAclDao();
		SObjectItem    item          = null;
		int            versionSeq    = 1;
		int      versioningStatus    = bucket.getVersioningStatus();
		
		Session session = PersistContext.getSession();
				
		// -> if versoning is off them we over write a null object item
		SObject object = objectDao.getByNameKey(bucket, nameKey);
		if ( object != null ) 
		{
			 // -> if versioning is on create new object items
			 if ( SBucket.VERSIONING_ENABLED  == versioningStatus )
			 {
			      session.lock(object, LockMode.UPGRADE);
			      versionSeq = object.getNextSequence();
			      object.setNextSequence(versionSeq + 1);
		 	      session.save(object);
		 	     
			      item = new SObjectItem();
			      item.setTheObject(object);
			      object.getItems().add(item);
			      item.setVersion(String.valueOf(versionSeq));
			      Date ts = DateHelper.currentGMTTime();
			      item.setCreateTime(ts);
			      item.setLastAccessTime(ts);
			      item.setLastModifiedTime(ts);
			      session.save(item);
			 }
			 else
			 {    // -> find an object item with a null version, can be null
				  //    if bucket started out with versioning enabled and was then suspended
				  item = objectItemDao.getByObjectIdNullVersion( object.getId());
				  if (item == null)
				  {
				      item = new SObjectItem();
				      item.setTheObject(object);
				      object.getItems().add(item);
				      Date ts = DateHelper.currentGMTTime();
				      item.setCreateTime(ts);
				      item.setLastAccessTime(ts);
				      item.setLastModifiedTime(ts);
				      session.save(item);		  
				  }
			 }
		} 
		else 
		{    // -> there is no object nor an object item
			 object = new SObject();
			 object.setBucket(bucket);
			 object.setNameKey(nameKey);
			 object.setNextSequence(2);
			 object.setCreateTime(DateHelper.currentGMTTime());
			 object.setOwnerCanonicalId(UserContext.current().getCanonicalUserId());
			 session.save(object);
		
		     item = new SObjectItem();
		     item.setTheObject(object);
		     object.getItems().add(item);
		     if (SBucket.VERSIONING_ENABLED  == versioningStatus) item.setVersion(String.valueOf(versionSeq));
		     Date ts = DateHelper.currentGMTTime();
		     item.setCreateTime(ts);
		     item.setLastAccessTime(ts);
		     item.setLastModifiedTime(ts);
		     session.save(item);
		}
		
		
		// we will use the item DB id as the file name, MD5/contentLength will be stored later
		String suffix = null;
		int dotPos = nameKey.lastIndexOf('.');
		if (dotPos >= 0) suffix = nameKey.substring(dotPos);
		if ( suffix != null )
			 item.setStoredPath(String.valueOf(item.getId()) + suffix);
		else item.setStoredPath(String.valueOf(item.getId()));
		
		metaDao.save("SObjectItem", item.getId(), meta);
		aclDao.save("SObjectItem", item.getId(), acl);
		session.update(item);
		
		return new Tuple<SObject, SObjectItem>(object, item);
	}
}
