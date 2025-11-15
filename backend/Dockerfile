# Sử dụng Java 17 (hoặc phù hợp với project của mày)
FROM eclipse-temurin:17-jdk-jammy


# Thư mục làm việc bên trong container
WORKDIR /app

# Copy file jar từ thư mục target (khi đã build xong bằng Maven)
COPY target/*.jar app.jar

# Mở cổng 8080
EXPOSE 8080

# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
