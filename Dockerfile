FROM openjdk:8-jdk-alpine
MAINTAINER postalservice
COPY target/postal-service-1.0-SNAPSHOT.jar postal-service.jar
ENTRYPOINT ["java","-jar","/postal-service.jar"]