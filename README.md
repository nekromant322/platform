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