FROM openjdk:8-jdk-alpine

COPY ./target/user-service.jar /app/
EXPOSE 9082
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/user-service.jar", "--spring.profiles.active=prod"]