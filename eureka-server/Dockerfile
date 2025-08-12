FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
RUN apk add --no-cache curl
COPY build/libs/eureka-server.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
