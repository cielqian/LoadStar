FROM openjdk:8-jdk-alpine

COPY ./target/link-service.jar /app/
EXPOSE 9083
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/link-service.jar"]