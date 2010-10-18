#!/usr/bin/env bash
# deploy-db-bridge.sh -- deploys the clouds3 database configuration.
#
# set -x

if [ ! -f clouds3_db.sql ]; then
  printf "Error: Unable to find clouds3_db.sql\n"
  exit 4
fi

if [ ! -f clouds3_schema.sql ]; then
  printf "Error: Unable to find clouds3_schema.sql\n"
  exit 5
fi

if [ ! -f clouds3_index.sql ]; then
  printf "Error: Unable to find clouds3_index.sql\n"
  exit 6;
fi

echo "Recreating Database."
mysql --user=root --password=$3 < clouds3_db.sql > /dev/null 2>/dev/null
mysqlout=$?
if [ $mysqlout -eq 1 ]; then
  printf "Please enter root password for MySQL.\n" 
  mysql --user=root --password < clouds3_db.sql
  if [ $? -ne 0 ]; then
    printf "Error: Cannot execute clouds3_db.sql\n"
    exit 10
  fi
elif [ $mysqlout -ne 0 ]; then
  printf "Error: Cannot execute clouds3_db.sql\n"
  exit 11
fi

mysql --user=cloud --password=cloud < clouds3_schema.sql
if [ $? -ne 0 ]; then
  printf "Error: Cannot execute clouds3_schema.sql\n"
  exit 11
fi


if [ $? -ne 0 ]
then
	exit 1
fi
  
echo "Creating Indice and Foreign Keys"
mysql --user=cloud --password=cloud < clouds3_index.sql
if [ $? -ne 0 ]; then
  printf "Error: Cannot execute clouds3_index.sql\n"
  exit 13
fi
