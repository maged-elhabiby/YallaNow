# Use the official OpenJDK image as the base image
FROM openjdk:21-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the target directory to the container
COPY target/feedservice-0.0.1-SNAPSHOT.jar feedservice.jar

# Expose port 8080 for the application
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "feedservice.jar"]