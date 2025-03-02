FROM openjdk:21-jdk-slim
RUN addgroup --system spring && adduser --system spring --ingroup spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY target/app.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
