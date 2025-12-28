# 1️⃣ Imagem base com JDK
FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app

# 2️⃣ Copia todos os arquivos do projeto para o container
COPY . .

# 3️⃣ Dá permissão de execução para o gradlew
RUN chmod +x ./gradlew

# 4️⃣ Build do JAR (ignora testes)
RUN ./gradlew clean bootJar -x test

# 5️⃣ Imagem final leve
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# 6️⃣ Copia o JAR gerado do stage builder
COPY --from=builder /app/build/libs/plan-service-0.0.1-SNAPSHOT.jar app.jar

# 7️⃣ Porta
EXPOSE 7070

# 8️⃣ Rodar aplicação
ENTRYPOINT ["java","-jar","/app/app.jar"]
