USE cloudbridge;

-- This file (and cloudbridge_policy_alter.sql) can be applied to an existing cloudbridge 
-- database.   It is used to manage defined bucket access policies.
--
SET foreign_key_checks = 0;

DROP TABLE IF EXISTS bucket_policies;

-- Amazon S3 only allows one policy to be defined for a bucket.
-- When a bucket is deleted its associated policy will be deleted with it.
-- The maximum size of a policy is 20 KB
--
CREATE TABLE bucket_policies (
	ID BIGINT NOT NULL AUTO_INCREMENT,
	
	SBucketID BIGINT NOT NULL,
	Policy    VARCHAR(20000) NOT NULL,  -- policies are written in JSON 

	PRIMARY KEY(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET foreign_key_checks = 1;

