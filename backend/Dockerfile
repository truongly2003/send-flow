# Stage 1: Build JAR với Maven official image 
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy tất cả code vào
COPY . .

# Build JAR, skip test 
RUN mvn clean package -DskipTests -Dmaven.test.skip=true

# Stage 2: Run với JRE 
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy JAR từ stage 1, đổi tên thống nhất thành app.jar
COPY --from=builder /app/target/sendflow-0.0.1-SNAPSHOT.jar app.jar

# Port Spring Boot
EXPOSE 8080

# Chạy với profile prod (thêm env nếu cần qua Render)
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]
