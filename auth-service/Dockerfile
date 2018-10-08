FROM openjdk:8-jdk-alpine

COPY ./target/auth-service.jar /app/
EXPOSE 9081
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/auth-service.jar"]