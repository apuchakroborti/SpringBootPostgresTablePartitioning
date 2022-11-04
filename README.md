This is a simple postgres table partitioning example projects using SpringBoot, 
Spring Data JPA [ 
We can use Spring Data JPA to reduce the amount of boilerplate code required to implement the data access object (DAO) layer.            
Spring Data JPA is not a JPA provider. It is a library/framework that adds an extra layer of abstraction on the top of our JPA provider (like Hibernate).
Spring Data JPA uses Hibernate as a default JPA provider. ],
Flyway, and PostgreSQL 

Electricity broadcasting project by SpringBoot, OAuth2, and postgres table partitioning \
Steps to run this project: \
Step 1. create a database with a user using password \
$ DROP DATABASE if exists electricity; \
$ CREATE DATABASE electricity; \
$ CREATE USER apu WITH ENCRYPTED PASSWORD 'tigerit'; \
$ GRANT ALL PRIVILEGES ON DATABASE electricity to apu; \
$ ALTER USER apu WITH SUPERUSER; 

Or shortcut way to create a user \
$ CREATE ROLE apu WITH LOGIN SUPERUSER PASSWORD 'tigerit';

Build project: $ mvn clean package
Run: mvn spring-boot: run 

Access and Refresh Token generation: for Admin user \
URL: http://127.0.0.1:8080/service-api/oauth/token \
Request Method: POST \
Headers: 
Content-Type: application/x-www-form-urlencoded \
Accept: application/json \
Authorization: Basic {{$token}} \
Here, $token: 'dGVzdC13ZWJhcHAtcnc6dGVzdC13ZWJhcHAtcnctMTIzNA==' \
How to make token: Base64Encoded string of 'test-webapp-rw:test-webapp-rw-1234' \
Body: 
grant_type: password \
username: YOUR_USERNAME (here, admin@gmail.com) \
password: YOUR_PASSWORD (1234) 
You will get the access and refresh token here

Now You can call the User related crud services: \
HEADERS:
Content-Type: application/json
Authorization: Bearer {{access_token}} //from step 5

To enroll a new user: \
POST http://127.0.0.1:8080/service-api/api/user \
To get an user info by id: \
GET http://127.0.0.1:8080/service-api/api/user/{id}  
To get paginated user by search criteria: \
GET http://127.0.0.1:9000/service-api/employee \
To update a employee: \
PUT http://127.0.0.1:9000/service-api/api/employee/{id}  
To delete a employee by id: \
DELETE http://127.0.0.1:9000/service-api/api/employee/{id} \
To update password: \
POST http://127.0.0.1:9000/service-api/api/employee/update-password \
To reset password by username: \
POST http://127.0.0.1:9000/service-api/api/employee/reset-password 

Insert load shedding test data:
POST http://127.0.0.1:8080/service-api/api/load-shedding/save \
GET http://127.0.0.1:8080/service-api/api/load-shedding/get-all 

You can see from pgadmin concole
Parent table: broadcasting_parent
partition tables:
broadcasting_child_01 to broadcasting_child_08
The child will contain data based on the values within a range of day  

Insert load shedding test data division wise partition tables:
POST http://127.0.0.1:8080/service-api/api/load-shedding/division/save \
GET http://127.0.0.1:8080/service-api/api/load-shedding/division/get-all 

You can see from pgadmin concole
Parent table: division_wise_broadcasting_parent
partition tables:
division_wise_broadcasting_child_01 to division_wise_broadcasting_child_08
The child will contain data based on the values of divisionId, for divisionId = 1 the child table 1 will have an entry division_wise_broadcasting_child_01    
on the other hand for the value of 8 division_wise_broadcasting_child_08 will have an entry

Common Error: \
Caused by: org.postgresql.util.PSQLException:  
The authentication type 10 is not supported. Check that you have configured the pg_hba.conf file to include the client's IP address or subnet, and that it is using an authentication scheme supported by the driver.

Probable Solution: \
Open postgresql.conf file location in windows: C:\Program Files\PostgreSQL\15\data

Update: \
host    all             all             127.0.0.1/32            scram-sha-256 \
to \
host    all             all             127.0.0.1/32            trust

Then restart the system
