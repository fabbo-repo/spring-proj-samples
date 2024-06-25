FROM docker.io/maven:3.8.6-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY ./src /app/src

RUN mvn clean install -Dmaven.test.skip=true


FROM docker.io/eclipse-temurin:17-jdk-alpine

ENV TZ=UTC
ENV SPRING_PROFILES_ACTIVE=dev

VOLUME /tmp

WORKDIR /app
COPY --from=build /app/target/homeworkapp.jar /app

ENTRYPOINT ["java", "-jar", "homeworkapp.jar"]