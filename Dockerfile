# Use Java 17 slim image
FROM openjdk:17-jdk-slim

# Set working directory in container
WORKDIR /app

# Copy the built JAR into the container
COPY target/HibernateBeautyECommerce-0.0.1-SNAPSHOT.jar app.jar

# Expose the port Spring Boot uses
EXPOSE 8080

# Command to run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
