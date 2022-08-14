FROM maven:3.8-amazoncorretto-11
COPY src eureka/src
COPY pom.xml eureka/pom.xml
RUN mvn -f eureka/pom.xml clean install -X
#COPY target /eureka/target
EXPOSE 8761
ENTRYPOINT ["java","-jar", "/eureka/target/platform-eureka-0.0.1.jar"]
