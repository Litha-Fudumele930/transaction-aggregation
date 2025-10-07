# Stage 1: Build the application
FROM maven:3.8.7-eclipse-temurin-17 AS build

WORKDIR /app

# Copy Maven descriptor and dependencies
COPY pom.xml ./
COPY src ./src
COPY sonar-project.properties ./

# Build the application (skip tests for faster builds)
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/transaction-aggregation*.jar app.jar

# Expose default JHipster port
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
