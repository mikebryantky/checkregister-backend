# Check Register

[![Codeship Status for mikebryantky/checkregister-backend](https://app.codeship.com/projects/76dfffa0-e647-0137-a879-266a1520ddda/status?branch=master)](https://app.codeship.com/projects/373572)

A simple check book application backend using REST services.


## SpringBoot
 * Make a copy of the __application-development-sample.yaml__ named __application-development.yaml__
 * Edit the  __application-development.yaml__ file so that the db_name, user, and passwords match your environment.
 * Make a copy of the __application-test-sample.yaml__ named __application-test.yaml__
 * Edit the  __application-test.yaml__ file so that the db_name, user, and passwords match your environment.
 
  
 
## MySQL
**NOTE:** Be sure that that empty database schema is created prior to running. Flyway migrations will not create
MySQL databases.

 Example:
```sql
CREATE DATABASE your_db_name
  CHARACTER SET utf8
  COLLATE utf8_general_ci;
```

To create a backup of the MySQL database, run:
```sql
mysqldump --complete-insert --lock-all-tables --extended-insert=false --default-character-set=utf8 -uxxUSERxx -pxxPASSxx your_db_name | gzip -9 > your_db_name.sql.gz
```
  

## Redis
The unit tests use an embedded Redis server tp simulate a Redis instance. For development and production profiles, you
must have an actual Redis server running.  I suggest running Redis in [Docker](https://hub.docker.com/_/redis).



## Maven
* To clean: __mvn clean__
* To build: __mvn package__ -Dspring.profiles.active=test
* To run: __mvn spring-boot:run -Dspring-boot.run.profiles=development__ (app runs at [http://localhost:8080/](http://localhost:8080/))
* To run unit tests: __mvn test -Dspring.profiles.active=test__



## Actuator
__Note:__ You must be logged before accessing any actuator endpoints.

* __Health:__ [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)
* __Info:__ [http://localhost:8080/actuator/info](http://localhost:8080/actuator/info)

See [here](https://docs.spring.io/spring-boot/docs/2.0.2.BUILD-SNAPSHOT/reference/htmlsingle/#production-ready) for a full list of actuator endpoints.


## Swagger 
To access Swagger documentation, while running the app go to [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Misc
* To deploy, run:
```bash
sudo java -Dspring.profiles.active=production -jar ./target/checkregister-backend.jar &
```

