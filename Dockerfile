# Etapa 1: Build com Maven + Java 21
FROM maven:3.9.4-eclipse-temurin-21 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Runtime com Java 21
FROM eclipse-temurin:21-jdk-alpine

# Instala netcat para o script de wait
RUN apk add --no-cache netcat-openbsd

VOLUME /tmp
COPY --from=builder /app/target/*.jar app.jar
COPY wait-for.sh /wait-for.sh
RUN chmod +x /wait-for.sh

ENTRYPOINT ["/wait-for.sh", "mysql-db", "java", "-jar", "/app.jar"]