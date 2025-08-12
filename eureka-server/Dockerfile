FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY build/libs/eureka-server.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]