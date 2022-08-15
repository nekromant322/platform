#FROM adoptopenjdk/openjdk14:alpine-jre
#COPY target/*.jar /platform_eureka/app.jar
#ENV JAVA_OPTS=""
#EXPOSE 8761
#ENTRYPOINT ["sh","-c", "java $JAVA_OPTS -Djava.security.edg=file:/dev/./urandom -jar /platform_eureka/app.jar"]

FROM maven:3.8-amazoncorretto-11
COPY src /platform_eureka/src
COPY target/*.jar /platform_eureka/app.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar", "/platform_eureka/app.jar"]
