FROM maven:3.8-amazoncorretto-11
COPY src /platform_bot/src
COPY target/*.jar /platform_bot/app.jar
EXPOSE 8003
ENTRYPOINT ["java","-jar", "/platform_bor/app.jar"]