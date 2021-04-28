## Приложение для чтения сообщений из Kafka и отправки их в Firebase Cloud Messaging

### Переменные окружения


| Название|Значение по умолчанию|Описание|
| --- |------- | ------ |
| KAFKA_HOST |127.0.0.1:9092| Хост Kafka |
| KAFKA_TOPIC |lot4-messages| Топик Kafka для входящих сообщений |
| KAFKA_NAME_GROUP |lot4-senders| Имя группы kafka для группировки consumers |
| SERVICE_ACCOUNT_KEY | | Ключ авторизации Firebase Cloud Messaging |
| COUNT_THREAD |100| Кол-во потоков для отправки сообщений в Firebase Cloud Messaging |
| FCM_HEALTH_TIMEOUT_MS |5000 | Период проверки статуса в мс Firebase Cloud Message (сетевые проблемы и т.п.) |
 
Метрики доступны на GET /actuator и для Prometheus на GET /actuator/prometheus