package com.cloud.bridge.service.core.s3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class S3BucketPolicy {

	private List<S3PolicyStatement> statementList = new ArrayList<S3PolicyStatement>();

	public S3BucketPolicy() {
		
	}
	
	public S3PolicyStatement[] getStatements() {
		return statementList.toArray(new S3PolicyStatement[0]);
	}
	
	public void addStatement(S3PolicyStatement param) {
		statementList.add( param );
	}
	
	public String toString() {
		
		StringBuffer value = new StringBuffer();
		Iterator<S3PolicyStatement> itr = statementList.iterator();
		
		value.append( "Bucket Policy: \n" );
		while( itr.hasNext()) {
			S3PolicyStatement oneStatement = itr.next();
			value.append( oneStatement.toString());
			value.append( "\n" );
		}
		
		return value.toString();
	}
}
