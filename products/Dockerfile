FROM maven:3.8.3-openjdk-11-slim AS Build
COPY ./ ./
RUN mvn clean package -DskipTests

FROM openjdk:11
COPY --from=Build ./target/*.jar /application.jar
ENTRYPOINT ["java","-jar","/application.jar"]