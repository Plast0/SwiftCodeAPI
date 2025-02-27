# SwiftCodeAPI
## Prerequisites
Make sure you have the following installed before running the project:
- [Java 21](https://jdk.java.net/21/)
- [Maven 3+](https://maven.apache.org/)
- [Docker](https://www.docker.com/)
- [MySQL](https://www.mysql.com/)

## Cloning the Repository
```console
git clone https://github.com/Plast0/SwiftCodeAPI
cd SwiftCodeAPI
```
## Docker
To build and run a docker container
```console
docker-compose up -d
```
## Test in Docker
To test the application in the container run
```console
docker exec -it swiftcode-app /bin/sh
mvn verify
```

## Configuration
The project uses the `application.properties` file. You can configure the database in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://host.docker.internal:3306/swift_db
spring.datasource.username=user
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
```

## Running the Application

Run the application in development mode:

```sh
mvn spring-boot:run
```

## Running Tests

To run tests, use:

```sh
mvn test
```
## Author
Piotr Barasiński
