# ===============================================
# ETAPA 1: COMPILACIÓN (MAVEN)
# ===============================================
# Usamos una imagen de Maven con JDK 17 para compilar el proyecto
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copiamos el pom.xml y descargamos las dependencias (para aprovechar el cache de Docker)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos el código fuente y compilamos el JAR saltándonos los tests para ir más rápido
COPY src ./src
RUN mvn clean package -DskipTests

# ===============================================
# ETAPA 2: EJECUCIÓN (JRE)
# ===============================================
# Usamos una imagen más ligera solo con el JRE para correr la App
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copiamos el archivo JAR generado en la etapa anterior
# IMPORTANTE: Asegúrate de que el nombre coincida con el de tu pom.xml
COPY --from=build /app/target/techmovil-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto 8080 que es donde corre Spring Boot
EXPOSE 8080

# Comando para arrancar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]