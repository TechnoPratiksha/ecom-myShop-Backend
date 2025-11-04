# ===============================
# 1️⃣ Build Stage - using Maven
# ===============================
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml first (to use Docker cache)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the project files
COPY src ./src

# Build the application (skip tests for faster build)
RUN mvn clean package -DskipTests

# ===============================
# 2️⃣ Runtime Stage - smaller image
# ===============================
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy only the built JAR from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose port (Render uses $PORT env variable, this is optional but OK)
EXPOSE 8081

# ✅ Pass Render's dynamic PORT to Spring Boot
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8081}"]
