# Use the official OpenJDK 11 as the base image
FROM gradle:8.2.1-jdk11 AS build
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

# Use a separate image for running tests
FROM gradle:8.2.1-jdk11 AS test

# Copy the built JAR file from the build image
COPY --from=build /app/build/libs/leasing.jar /app/leasing.jar

# Copy the application source code (you may not need this in a production setup)
COPY . .

# Expose the port that the Spring Boot application will run on
EXPOSE 8081

# Run the Spring Boot application
CMD ["java", "-jar", "build/libs/leasing.jar"]
