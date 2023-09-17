FROM docker.io/eclipse-temurin:17-jdk-alpine

ENV SPRING_PROFILES_ACTIVE=prod

WORKDIR /app

COPY ./target/homeworkapp.jar /app

ENTRYPOINT ["java", "-jar", "homeworkapp.jar"]