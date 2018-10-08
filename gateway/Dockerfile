FROM openjdk:8-jdk-alpine

COPY ./target/gateway.jar /app/
EXPOSE 9080
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/gateway.jar"]