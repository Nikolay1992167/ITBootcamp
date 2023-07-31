FROM maven:3.8.3-openjdk-17 as build

WORKDIR /app

COPY pom.xml .
COPY model/pom.xml model/pom.xml
COPY service/pom.xml service/pom.xml
COPY web/pom.xml web/pom.xml

RUN mvn -B -e -T 1C verify --fail-never

COPY model model
COPY service ervice
COPY web web

RUN mvn -B -e -T 1C package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/model/target/model-*.jar model.jar
COPY --from=build /app/service/target/service-*.jar service.jar
COPY --from=build /app/web/target/web-*.jar web.jar

EXPOSE 8080 8081 8082

CMD java -jar model.jar & java -jar service.jar & java -jar web.jar & wait