FROM maven:3.8-amazoncorretto-11
COPY src /platform_code_executor/src
COPY target/*.jar /platform_code_executor/app.jar
EXPOSE 8002
ENTRYPOINT ["java","-jar", "/platform_code_executor/app.jar"]