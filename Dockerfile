FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY target/is-project-1.0.0.jar /app/is-project-1.0.0.jar
ENTRYPOINT ["java", "-jar", "is-project-1.0.0.jar"]
EXPOSE 8080