FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/elasticsearch-autocomplete-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/app.jar"]

#mvn clean package
#docker build -t my-java-app .

