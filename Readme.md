### Тестовое задание МТС

[Само задание](doc/Lot4Java.docx)

Система состоит из модулей:

- [Регистрация устройств](device-app/Readme.md)
- [Приемник событий](receiver-app/Readme.md)
- [Отправка в FCM](sender-app/Readme.md)
- [Получение статистики](statistic-app/Readme.md)

### Сборка

````
mvn clean package
````

### Тестовое развертывание

````
docker-compose up
````

### Логирование

В контейнере развернут Graylog на [http://127.0.0.1:9999](http://127.0.0.1:9999). Имя/пароль: admin/admin. Для приема логов от программ нужно добавить Input Stream __GELF UDP__ на порту 12201