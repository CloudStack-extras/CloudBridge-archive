USE cloudbridge;

ALTER TABLE bucket_policies ADD CONSTRAINT FOREIGN KEY policy_for_bucket(SBucketID) REFERENCES sbucket(ID) ON DELETE CASCADE;
ALTER TABLE bucket_policies ADD UNIQUE one_policy_per_bucket(SBucketID);
