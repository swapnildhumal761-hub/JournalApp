# Stage 1: Build the Spring Boot application
FROM eclipse-temurin:17-jdk-focal AS builder

# Set working directory
WORKDIR /app

# Copy Maven wrapper and project files
COPY mvnw .
COPY .mvn .mvn

# Copy pom.xml
COPY pom.xml .

# Download dependencies
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src src

# Build the application
RUN ./mvnw clean package -DskipTests

# Stage 2: Runtime image
FROM eclipse-temurin:17-jre-focal

# Set working directory
WORKDIR /app

# Copy jar from builder stage
COPY --from=builder /app/target/journalApp-0.0.1-SNAPSHOT.jar app.jar

# Expose application port
EXPOSE 8081

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]