# 基础镜像：官方JDK17 slim版（轻量，适配Spring Boot3）
FROM openjdk:17-jdk-slim

# 维护者信息（可选）
LABEL maintainer="1714564639@qq.com"

# 工作目录
WORKDIR /app

# 将本地已下载的JAR包复制到容器内（关键：复用本地JAR，无需在线下载）
COPY sentinel-dashboard-1.8.7.jar /app/sentinel-dashboard.jar

# 暴露Sentinel控制台端口（固定8080，容器内端口）
EXPOSE 8080

# 环境变量默认值（可被docker-compose/run命令覆盖）
ENV SENTINEL_DASHBOARD_AUTH_USERNAME=sentinel
ENV SENTINEL_DASHBOARD_AUTH_PASSWORD=sentinel
ENV SERVER_PORT=8080
ENV JAVA_OPTS="-Djava.version=17 -Djava.awt.headless=true"

# 启动命令（兼容环境变量，灵活配置）
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/sentinel-dashboard.jar \
            --server.port=${SERVER_PORT} \
            --sentinel.dashboard.auth.username=${SENTINEL_DASHBOARD_AUTH_USERNAME} \
            --sentinel.dashboard.auth.password=${SENTINEL_DASHBOARD_AUTH_PASSWORD}"]