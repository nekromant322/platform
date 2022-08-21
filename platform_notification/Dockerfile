FROM maven:3.8-amazoncorretto-11
COPY src /platform_notification/src
COPY target/*.jar /platform_notification/app.jar
EXPOSE 8001
ENTRYPOINT ["java","-jar", "/platform_notification/app.jar"]
#ENTRYPOINT ["sh","-c", "java -DMAIL_USER_NAME=platform.override@yandex.ru;MAIL_PASSWORD=kmctrmyvlkzbrbil;SMS_API_ID=9848A658-DBC1-F953-7D76-05DC739EE738  -jar /platform_eureka/app.jar"]


#MAIL_USER_NAME=platform.override@yandex.ru;MAIL_PASSWORD=kmctrmyvlkzbrbil;SMS_API_ID=9848A658-DBC1-F953-7D76-05DC739EE738