FROM maven:3.8-amazoncorretto-11
COPY platform_eureka_server platform/platform_eureka_server
COPY platform_bot platform/platform_bot
COPY platform_code_executor platform/platform_code_executor
COPY platform_orchestrator platform/platform_orchestrator
COPY platform_notification platform/platform_notification
COPY platform_dto platform/platform_dto
COPY pom.xml platform/pom.xml
#RUN mvn -f platform/pom.xml clean package -X
#COPY target /eureka/target
EXPOSE 8000
ENTRYPOINT ["java","-jar", "platform/platform_eureka_server/target/platform-eureka-0.0.1.jar"]
ENTRYPOINT ["java","-jar", "platform/platform_orchestrator/target/platform-eureka-0.0.1.jar"]
