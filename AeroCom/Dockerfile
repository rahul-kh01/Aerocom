# Use an official OpenJDK image as a base
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the Maven wrapper and pom.xml to the container
COPY mvnw mvnw.cmd pom.xml ./

# Copy the .mvn directory as well
COPY .mvn .mvn

# Download the project dependencies using Maven
RUN ./mvnw dependency:go-offline

# Copy the source code to the container
COPY src ./src

# Build the application using Maven
RUN ./mvnw clean package -DskipTests

# Expose the port on which the application will run
EXPOSE 1030

# Run the Spring Boot application
CMD ["java", "-jar", "target/<your-app-name>.jar"]
