# -------- 1) Build (Maven + JDK 21) --------
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /workspace


COPY pom.xml .
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline -B


COPY src ./src
RUN ./mvnw clean package -DskipTests -B



FROM eclipse-temurin:21-jre
WORKDIR /app
EXPOSE 8080

COPY --from=build /workspace/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
