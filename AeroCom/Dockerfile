# Use a base image with JDK 17
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven build files (pom.xml and dependencies) to the working directory
COPY pom.xml .
COPY src ./src

# Package the application using Maven
RUN ./mvnw clean package -DskipTests

# Expose the port your app will run on
EXPOSE 1030

# Run the packaged Spring Boot app
CMD ["java", "-jar", "target/<your-jar-file>.jar"]
