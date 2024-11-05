FROM eclipse-temurin:17-jdk-focal as builder
WORKDIR /opt/app
COPY guru/.mvn/ .mvn
COPY guru/mvnw .
COPY guru/pom.xml .
RUN chmod +x ./mvnw
RUN ./mvnw dependency:go-offline
COPY guru/src ./src
RUN ./mvnw clean install -DskipTests
RUN find ./target -type f -name '*.jar' -exec cp {} /opt/app/app.jar \; -quit

FROM eclipse-temurin:17-jre-alpine
COPY --from=builder /opt/app/app.jar /opt/app/
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]
