FROM maven:3.8-amazoncorretto-11
COPY src /platform_notification/src
COPY target/*.jar /platform_notification/app.jar
EXPOSE 8001
ENTRYPOINT ["java","-jar", "/platform_notification/app.jar"]