# Stage 1 — Build con Maven
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copia prima solo il pom.xml per sfruttare la cache Docker
# (le dipendenze vengono riscaricate solo se il pom cambia)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia il sorgente e compila
COPY src ./src
RUN mvn package -DskipTests

# Stage 2 — Runtime leggero, solo il jar
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar", "-Dspring.profiles.active=prod"]