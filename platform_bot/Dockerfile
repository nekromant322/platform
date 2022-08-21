FROM maven:3.8-amazoncorretto-11
COPY src /platform_bot/src
COPY target/*.jar /platform_bot/app.jar
EXPOSE 8003
ENTRYPOINT ["java","-jar", "/platform_bor/app.jar"]
#ENTRYPOINT ["sh","-c", "java -DTELEGRAM_BOT_NAME=MentoringPlatformBot;TELEGRAM_BOT_TOKEN=5129084912:AAHWNkwwPiPiuEmjJrBccpgwA2lFxYqXbFs  -jar /platform_eureka/app.jar"]



#TELEGRAM_BOT_NAME=MentoringPlatformBot;TELEGRAM_BOT_TOKEN=5129084912:AAHWNkwwPiPiuEmjJrBccpgwA2lFxYqXbFs