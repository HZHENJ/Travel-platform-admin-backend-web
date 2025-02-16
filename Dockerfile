# 使用 OpenJDK 17
FROM openjdk:17

# 设置工作目录
WORKDIR /app

# 复制 JAR 文件到容器
COPY target/backend-web-0.0.2-SNAPSHOT.jar /app/backend.jar

# 运行 Spring Boot 应用
CMD ["java", "-jar", "/app/backend.jar"]
