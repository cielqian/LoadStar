FROM openjdk:8-jdk-alpine

COPY ./target/registry.jar /app/
EXPOSE 9001
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/registry.jar"]