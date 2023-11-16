FROM docker.io/maven:3.8.6-eclipse-temurin-17 as build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY ./src /app/src

RUN mvn clean install -Dmaven.test.skip=true


FROM docker.io/eclipse-temurin:17-jdk-alpine

ENV SPRING_PROFILES_ACTIVE=dev

WORKDIR /app
COPY --from=build /app/target/ean-api.jar /app

ENTRYPOINT ["java", "-jar", "ean-api.jar"]
