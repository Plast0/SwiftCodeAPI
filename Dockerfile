FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

RUN apt-get update && apt-get install -y maven

COPY /pom.xml .
RUN mvn dependency:go-offline

COPY /src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /app

RUN apt-get update && apt-get install -y maven

COPY --from=build /app/target/SwiftCode-0.0.1-SNAPSHOT.jar app.jar
COPY --from=build /app/pom.xml pom.xml
COPY --from=build /app/src src

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]