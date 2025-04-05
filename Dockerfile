# Use Java 21 JDK as base image
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy Maven wrapper & code
COPY . .

# Build the project (skip tests if not needed)
RUN ./mvnw clean package -DskipTests

# Run the Spring Boot app
CMD ["java", "-jar", "target/finflow-0.0.1-SNAPSHOT.jar"]
