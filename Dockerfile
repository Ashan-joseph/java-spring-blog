FROM eclipse-temurin:21-jre

WORKDIR /app

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} blog.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/blog.jar"]