## Приложение для получения сообщений от пользователей и отправка их для обработки в Kafka

### Переменные окружения


| Название|Значение по умолчанию|Описание|
| --- |------- | ------ |
| KAFKA_HOST |127.0.0.1:9092| Хост Kafka |
| KAFKA_TOPIC |lot4-notify| Топик Kafka для входящих сообщений |
| EUREKA_HOST |http://localhost:8761/eureka| Хост register-app |
 
Метрики доступны на GET /actuator и для Prometheus на GET /actuator/prometheus

#### Logger

Logger реализован на GrayLog. Протокол обмена GELF UDP. Должны быть установлены переменные окружения GRAYLOG_HOST и GRAYLOG_PORT
