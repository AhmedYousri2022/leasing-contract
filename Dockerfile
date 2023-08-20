# Use the official OpenJDK 11 as the base image
FROM openjdk:11-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle wrapper files
COPY gradlew .
COPY gradle gradle

# Copy the build.gradle and settings.gradle files
COPY build.gradle .
COPY settings.gradle .

# Copy the entire project directory (excluding files mentioned in .dockerignore)
COPY . .

# Build the Spring Boot application using Gradle
RUN ./gradlew build

# Expose the port that the Spring Boot application will run on
EXPOSE 8081

# Run the Spring Boot application
CMD ["java", "-jar", "build/libs/leasing.jar"]
