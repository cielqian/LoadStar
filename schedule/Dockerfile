FROM openjdk:8-jdk-alpine

COPY ./target/schedule-service.jar /app/
EXPOSE 9084
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/schedule-service.jar", "--spring.profiles.active=prod"]