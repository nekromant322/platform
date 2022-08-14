FROM maven:3.5-jdk-11 AS build
COPY src /src/app/src
COPY pom.xml /src/app
RUN mvn -f /src/app/pom.xml clean package

FROM gcr.io/distroless/java:11
COPY --from=build /src/app/platform-orchestrator-0.0.1-SNAPSHOT.jar /app/back.jar
EXPOSE 8000
ENTRYPOINT ["java","-jar","app/back.jar"]