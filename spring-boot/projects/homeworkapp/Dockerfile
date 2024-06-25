FROM docker.io/eclipse-temurin:17-jdk-alpine

ENV TZ=UTC
ENV SPRING_PROFILES_ACTIVE=prod

VOLUME /tmp

WORKDIR /app

COPY ./target/homeworkapp.jar /app

ENTRYPOINT ["java", "-jar", "homeworkapp.jar"]