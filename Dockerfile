
FROM openjdk:17-jdk-slim

COPY target/transactions-vr-1.0-SNAPSHOT.jar /app.jar

COPY src/main/resources/application.properties /application.properties

RUN apt-get update && \
    apt-get install -y docker-compose

CMD ["java", "-jar", "/app.jar"]
