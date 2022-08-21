FROM maven:3.8-amazoncorretto-11
COPY src /platform_code_executor/src
COPY target/*.jar /platform_code_executor/app.jar
EXPOSE 8002
ENTRYPOINT ["java","-jar", "/platform_code_executor/app.jar"]
#ENTRYPOINT ["sh","-c", "java -DTELEGRAM_BOT_NAME=MentoringPlatformBot;TELEGRAM_BOT_TOKEN=5129084912:AAHWNkwwPiPiuEmjJrBccpgwA2lFxYqXbFs  -jar /platform_eureka/app.jar"]



#TELEGRAM_BOT_NAME=MentoringPlatformBot;TELEGRAM_BOT_TOKEN=5129084912:AAHWNkwwPiPiuEmjJrBccpgwA2lFxYqXbFs