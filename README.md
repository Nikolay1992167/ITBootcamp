# ITBootcamp
# User application

Simple KRUD application
Performs basic operations on an object. The following is a list of mandatory requirements that must be considered in the data model.
The project is assembled using maven, consists of 3 modules.
The project at startup should create test data in the database.
## Author: [Nikolay Minich](https://github.com/Nikolay1992167/ITBootcamp)

### Technologies that I used on the project:

* Java 17
* Maven 3.8.8
* Springframework: boot:spring-boot-starter-data-jpa
* Springframework: boot:spring-boot-starter-validation
* Springframework: boot:spring-boot-starter-web
* Liquibase: liquibase-cor
* Mysql8: mysql-connector-j
* Lombok: lombok
* Springdoc: springdoc-openapi-starter-webmvc-ui
* Springframework: boot: spring-boot-starter-test
* Junit.jupiter: junit-jupiter-api
* Mockito: mockito-junit-jupiter

### Instructions to run application locally:

1. You must have [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html),
   [Intellij IDEA Ultimate](https://www.jetbrains.com/idea/download/) and [Postgresql](https://www.postgresql.org/download/)
2. In [application.yml](src/main/resources/application.yml) enter your name database, username and password from your
   local postgresql in line №6, №7, №8
5. Run application.
6. Application is ready to work

### Unit tests

The tests are located in web/src/test/java.

### Docker container

Files for application containerization are given. Didn't work with multi-model app containerization. There is an error starting the main class. The container is created but not launched.

## Functionalities

In summary the application can:
***UserController [user.http](web/src/main/resources/user.http)***