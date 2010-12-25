package com.cloud.bridge.persist.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.cloud.bridge.model.UserCredentials;
import com.cloud.bridge.service.UserContext;
import com.cloud.bridge.service.core.s3.S3MetaDataEntry;
import com.cloud.bridge.service.core.s3.S3MultipartPart;
import com.cloud.bridge.util.ConfigurationHelper;

public class MultipartLoadDao {
	public static final Logger logger = Logger.getLogger(MultipartLoadDao.class);

	private Connection conn       = null;
	private String     dbName     = null;
	private String     dbUser     = null;
	private String     dbPassword = null;
	
	public MultipartLoadDao() {
	    File propertiesFile = ConfigurationHelper.findConfigurationFile("ec2-service.properties");
	    Properties EC2Prop = null;
	       
	    if (null != propertiesFile) {
	   	    EC2Prop = new Properties();
	    	try {
				EC2Prop.load( new FileInputStream( propertiesFile ));
			} catch (FileNotFoundException e) {
				logger.warn("Unable to open properties file: " + propertiesFile.getAbsolutePath(), e);
			} catch (IOException e) {
				logger.warn("Unable to read properties file: " + propertiesFile.getAbsolutePath(), e);
			}
		    dbName     = EC2Prop.getProperty( "dbName" );
		    dbUser     = EC2Prop.getProperty( "dbUser" );
		    dbPassword = EC2Prop.getProperty( "dbPassword" );
		}
	}
	
	/**
	 * If a multipart upload exists with the uploadId value then return a non-null creation date.
	 * 
	 * @param uploadId
	 * @return creation date of the multipart upload
	 * @throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException 
	 */
	public Date multipartExits( int uploadId ) 
	    throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
	    PreparedStatement statement = null;
        Date tod = null;
		
        openConnection();	
        try {            
		    statement = conn.prepareStatement ( "SELECT CreateTime FROM multipart_uploads WHERE ID=?" );
	        statement.setInt( 1, uploadId );
	        ResultSet rs = statement.executeQuery();
		    if (rs.next()) tod = rs.getDate( "CreateTime" );
            return tod;
        
        } finally {
            closeConnection();
        }
	}
	
	/**
	 * Create a new "in-process" multipart upload entry to keep track of its state.
	 * 
	 * @param accessKey
	 * @param bucketName
	 * @param key
	 * @param cannedAccess
	 * 
	 * @return if positive its the uploadId to be returned to the client
	 * 
	 * @throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException 
	 */
	public int initiateUpload( String accessKey, String bucketName, String key, String cannedAccess, S3MetaDataEntry[] meta ) 
	    throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
	    PreparedStatement statement = null;
		int uploadId = -1;
		
        openConnection();	
        try {
	        Date tod = new Date();
	        java.sql.Date sqlDate = new java.sql.Date( tod.getTime());

		    statement = conn.prepareStatement ( "INSERT INTO multipart_uploads (AccessKey, BucketName, NameKey, x_amz_acl, CreateTime) VALUES (?,?,?,?,?)" );
	        statement.setString( 1, accessKey );
	        statement.setString( 2, bucketName );
	        statement.setString( 3, key );
	        statement.setString( 4, cannedAccess );      
	        statement.setDate( 5, sqlDate );
            int count = statement.executeUpdate();
            statement.close();	
            
            // -> we need the newly entered ID 
		    statement = conn.prepareStatement ( "SELECT ID FROM multipart_uploads WHERE AccessKey=? AND BucketName=? AND NameKey=? AND x_amz_acl=? AND CreateTime=?" );
	        statement.setString( 1, accessKey );
	        statement.setString( 2, bucketName );
	        statement.setString( 3, key );
	        statement.setString( 4, cannedAccess );      
	        statement.setDate( 5, sqlDate );
	        ResultSet rs = statement.executeQuery();
		    if (rs.next()) {
		    	uploadId = rs.getInt( "ID" );
		        saveMultipartMeta( uploadId, meta );
		    }
            statement.close();			    
            return uploadId;
        
        } finally {
            closeConnection();
        }
	}
	
	/**
	 * Remember all the individual parts that make up the entire multipart upload so that once
	 * the upload is complete all the parts can be glued together into a single object.
	 * 
	 * @param uploadId
	 * @param partNumber
	 * @param md5
	 * @param storedPath
	 * @param size
	 * @throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	 */
	public void saveUploadPart( int uploadId, int partNumber, String md5, String storedPath, int size ) 
        throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
    {
        PreparedStatement statement = null;
	
        openConnection();	
        try {
            Date tod = new Date();
            java.sql.Date sqlDate = new java.sql.Date( tod.getTime());

	        statement = conn.prepareStatement ( "INSERT INTO multipart_parts (UploadID, partNumber, MD5, StoredPath, StoredSize, CreateTime) VALUES (?,?,?,?,?,?)" );
            statement.setInt(    1, uploadId );
            statement.setInt(    2, partNumber );
            statement.setString( 3, md5 );
            statement.setString( 4, storedPath );   
            statement.setInt(    5, size );
            statement.setDate(   6, sqlDate );
            int count = statement.executeUpdate();
            statement.close();	
            
        } finally {
            closeConnection();
        }
    }
	
	/**
	 * Return info on a range of upload parts that have already been stored in disk,
	 * 
	 * @param uploadId
	 * @param maxParts
	 * @param startAt
	 * @return an array of S3MultipartPart objects
	 * @throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	 */
	public S3MultipartPart[] getUploadParts( int uploadId, int maxParts, int startAt ) 
	    throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		S3MultipartPart[] parts = new S3MultipartPart[maxParts];
	    PreparedStatement statement = null;
	    int i = 0;
		
        openConnection();	
        try {
		    statement = conn.prepareStatement ( "SELECT partNumber, MD5, StoredSize, CreateTime  " +
		    		                            "FROM   multipart_parts " +
		    		                            "WHERE  UploadID=? " +
		    		                            "AND    ID > ? AND ID < ?" );
	        statement.setInt( 1, uploadId );
	        statement.setInt( 2, startAt  );
	        statement.setInt( 3, startAt + maxParts + 1 );
		    ResultSet rs = statement.executeQuery();
		    
		    while (rs.next() && i < maxParts) 
		    {
		    	Calendar tod = Calendar.getInstance();
		    	tod.setTime( rs.getDate( "CreateTime" ));
		    	
		    	parts[i] = new S3MultipartPart();
		    	parts[i].setPartNumber( rs.getInt( "partNumber" ));
		    	parts[i].setEtag( rs.getString( "MD5" ));
		    	parts[i].setLastModified( tod );
		    	parts[i].setSize( rs.getInt( "StoredSize" ));
		    	i++;
		    }
            statement.close();			    
            return parts;
        
        } finally {
            closeConnection();
        }
	}
        
	/**
	 * A multipart upload request can have zero to many meta data entries to be applied to the
	 * final object.   We need to remember all of the objects meta data until the multipart is complete.
	 * 
	 * @param uploadId - defines an in-process multipart upload
	 * @param meta - an array of meta data to be assocated with the uploadId value
	 * 
	 * @throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException 
	 */
	private void saveMultipartMeta( int uploadId, S3MetaDataEntry[] meta ) 
	    throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		if (null == meta) return;	
	    PreparedStatement statement = null;
		
        openConnection();	
        try {
            for( int i=0; i < meta.length; i++ ) 
            {
               S3MetaDataEntry entry = meta[i];
		       statement = conn.prepareStatement ( "INSERT INTO multipart_meta (UploadID, Name, Value) VALUES (?,?,?)" );
	           statement.setInt( 1, uploadId );
	           statement.setString( 2, entry.getName());
	           statement.setString( 3, entry.getValue());
               int count = statement.executeUpdate();
               statement.close();
            }
            
        } finally {
            closeConnection();
        }
	}
	
	private void openConnection() 
        throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        if (null == conn) {
	        Class.forName( "com.mysql.jdbc.Driver" ).newInstance();
            conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/"+dbName, dbUser, dbPassword );
        }
	}

    private void closeConnection() throws SQLException {
	    if (null != conn) conn.close();
	    conn = null;
    }
}
