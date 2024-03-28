# Stage 1: Build the application using Gradle
FROM openjdk:21-jdk-slim as build
WORKDIR /workspace/app
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN chmod +x ./gradlew
RUN ./gradlew build -x test

# Stage 2: Create the Docker container image with OpenJDK 21.0.1
FROM openjdk:21-jdk-slim
VOLUME /tmp
COPY --from=build /workspace/app/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
