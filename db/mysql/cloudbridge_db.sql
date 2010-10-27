SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ANSI';

USE `mysql`;

DROP PROCEDURE IF EXISTS `mysql`.`cloud_create_user_if_not_exists`;
DELIMITER $$
CREATE PROCEDURE `mysql`.`cloud_create_user_if_not_exists`()
BEGIN
  DECLARE foo BIGINT DEFAULT 0;
  SELECT COUNT(*)
  INTO foo
    FROM `mysql`.`user`
      WHERE `User` = 'cloud';
  
  IF foo = 0 THEN 
	CREATE USER cloud identified by 'cloud';         
  END IF;
  
END;$$
DELIMITER ;

CALL `mysql`.`cloud_create_user_if_not_exists`() ;
DROP PROCEDURE IF EXISTS `mysql`.`cloud_create_user_if_not_exists` ;

SET SQL_MODE=@OLD_SQL_MODE;

DROP DATABASE IF EXISTS cloudbridge;
CREATE DATABASE cloudbridge;
  
GRANT ALL ON cloudbridge.* to `cloud`@`localhost` identified by 'cloud';
GRANT ALL ON cloudbridge.* to `cloud`@`%` identified by 'cloud';

GRANT process ON *.* TO `cloud`@`localhost`;
GRANT process ON *.* TO `cloud`@`%`;

COMMIT;
