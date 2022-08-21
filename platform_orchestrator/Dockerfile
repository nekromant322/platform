FROM maven:3.8-amazoncorretto-11
COPY src /platform_orchestrator/src
COPY target/*.jar /platform_orchestrator/app.jar
EXPOSE 8000
ENTRYPOINT ["java","-jar", "/platform_orchestrator/app.jar"]
#ENTRYPOINT ["sh","-c", "java -DSMS_API_ID=9848A658-DBC1-F953-7D76-05DC739EE738 -jar /platform_eureka/app.jar"]


#SMS_API_ID=9848A658-DBC1-F953-7D76-05DC739EE738