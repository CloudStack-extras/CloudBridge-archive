package com.cloud.bridge.persist.dao;

import java.util.List;

import com.cloud.bridge.model.SSHKey;
import com.cloud.bridge.persist.EntityDao;

public class SSHKeysDao extends EntityDao<SSHKey> {
	
	public SSHKeysDao() {
		super(SSHKey.class);
	}
	
	public List<SSHKey> getKeysByUser(String accountName) {
		return queryEntities("from SSHKey where accountName=?", new Object[] { accountName });
	}
	
	public SSHKey getKeyByName(String keyName, String accountName) {
		return queryEntity("from SSHKey where keyName=? and accountName=?", new Object[] { keyName, accountName });
	}
	
}
