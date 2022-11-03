Electricity broadcasting project by SpringBoot, OAuth2, and postgres table partitioning
$ DROP DATABASE if exists electricity;
$ CREATE DATABASE electricity;
$ CREATE USER apu WITH ENCRYPTED PASSWORD 'tigerit';
$ GRANT ALL PRIVILEGES ON DATABASE electricity to apu;
$ ALTER USER apu WITH SUPERUSER;

Or shortcut way to create a user
$ CREATE ROLE apu WITH LOGIN SUPERUSER PASSWORD 'tigerit';

Common Error:
Caused by: org.postgresql.util.PSQLException: 
The authentication type 10 is not supported. Check that you have configured the pg_hba.conf file to include the client's IP address or subnet, and that it is using an authentication scheme supported by the driver.

Probable Solution:
Open postgresql.conf file location in windows: C:\Program Files\PostgreSQL\15\data

Update:
host    all             all             127.0.0.1/32            scram-sha-256
to
host    all             all             127.0.0.1/32            trust
