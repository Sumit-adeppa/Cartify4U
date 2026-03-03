# =========================
# Stage 1: Build the JAR
# =========================
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /build

# Copy pom.xml first to leverage Docker cache
COPY pom.xml .

# Download dependencies (cached if pom.xml unchanged)
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests


# =========================
# Stage 2: Run the app
# =========================
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy only the generated JAR from the build stage
COPY --from=build /build/target/*.jar app.jar

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "app.jar"]
   
