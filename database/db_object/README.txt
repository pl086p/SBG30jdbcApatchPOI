--Running as postgres user on postgres host:

user ~$ sudo su root
root ~$ cd /
root ~$ cd var/lib/pgsql
root pgsql$ cp -r /my-path/myDBobjects ./
root pgsql$ su - postgres 
-bash$ cd myDBobjects
-bash$ psql -d myDBname -f loadDBobjects.sql



