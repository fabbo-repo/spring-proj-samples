FROM docker.io/maven:3.8.6-eclipse-temurin-17 as build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY flyway.conf .
COPY ./src /app/src

ENTRYPOINT ["mvn", "flyway:migrate"]