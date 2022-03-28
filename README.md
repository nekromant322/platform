Перед запуском:
- Установи JDK 11
- Idea-> Plugins -> Lombok -> Install
- Idea -> Settings -> Build, Execution, Deployment -> Compiler -> Annotation Proccesor -> Enable annotation Proccesing

Git Branching:
- Новые ветки с изменениями должны называться feature/task-name! (пример: feature/telegram-bot)
- Изменения в master только через pull request, в тайтле указать суть изменений, либо название задачи из trello
- Прежде чем начать работу над задачей :
1) git checkout main
2) git pull origin main
3) git checkout -b feature/task-name
По завершении создать пул реквест и прикрепить в trello в комментарий к задаче

Мы инжектим бины через поля (не через сеттер/конструктор)!

Для разворачивания Postgres:
1) Установи Docker Desktop.
2) Перезагрузи компьютер. Запусти Docker Desktop и удали предыдущие версии контейнеров. 
Выполни в терминале IDEA `mvn clean install`. Это очень важный пункт, если у тебя что-то не получается и в интернете 
решения нет, то можешь повторить любые из этих трех магических действий или пункт целиком.
3) В IDEA открой docker-compose-db.yml. Это compose-файл, в котором указаны конфиги для контейнеров.
Для каждого микросервиса разворачивается свой контейнер с Postgres внутри, и для каждого конфиги свои. 
Внимательно смотри на конфиги!
4) Жмем на кнопку docker-compose up рядом с services или открываем вкладку Services 
жмем Docker -> Deploy -> docker-compose-db.yml: Compose Deployment. В Deploy Log должны появиться
надписи наподобие:
    `Starting platform_notification-db_1 ... done`
А в Docker Desktop должна появится группа контейнеров platform. Если нет, то попробуй повторить п.2.
5) Запускай сервисы, которым нужен Postgres.
Для удобства работы с БД в IDEA есть вкладка Database, можешь использовать её, но однажды она чуть не свела меня с ума, 
поэтому предлагаю иногда проверять работоспособность Postgres'ов вручную. Как это делается:
6) Открой из Docker Desktop терминал (CLI) контейнера с нужной тебе БД.
7) Посмотри п.3 и пойми, какие значения ты будешь подставлять вместо POSTGRES_USER, POSTGRES_PASSWORD, POSTGRES_DB в следующих командах. 
В CLI введи:
    psql -U POSTGRES_USER 
Если приглашение к вводу сменилось на `postgres=#` поздравляю, вы зашли в Postgres. 
8) Выполни `\l` для получения списка доступных БД в контейнере, либо же сразу подключись к нужной:
    \c POSTGRES_DB
9) Если приглашение к вводу сменилось на `POSTGRES_DB=#`, то можешь выполнить `\dt` для получения списка таблиц или любой SQL-запрос
И помни, если у тебя что-то не получается - никогда не поздно попробовать повторить п.2!


Для тестирования через Postman:
    
1) Получаем token, для этого делаем запрос на /login с логином и паролем в формате json
    
![alt](https://i.ibb.co/c3cXS8C/img.png)
    
2) Копируем токен, который пришел в ответе
   
![alt](https://i.ibb.co/qyPJyTg/img-1.png)

3) Открываем вкладку Cookies и нажимаем на кнопку Add cookie
   
![alt](https://i.ibb.co/WsZGvmy/img-2.png) ![alt](https://i.ibb.co/09ms0yd/img-3.png)

4) Вместо Cookie_N=value ставим token=*наш токен*; *эту часть оставляем*
    
![alt](https://i.ibb.co/j8S68TD/img-6.png) ![alt](https://i.ibb.co/7vx42gV/img-5.png)

Полезные ссылки:
https://trello.com/b/zN2JnsWE/%D0%BF%D0%BB%D0%B0%D1%82%D1%84%D0%BE%D1%80%D0%BC%D0%B0