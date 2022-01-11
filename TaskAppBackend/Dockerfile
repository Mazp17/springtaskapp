FROM openjdk:17-jdk-alpine
MAINTAINER miguel.com
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} TaskAppBackend-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/TaskAppBackend-0.0.1-SNAPSHOT.jar"]
