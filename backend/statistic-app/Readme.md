#Web-API для получения статистики приложения
1) Отображает список всех зарегистрированных в системе версий мобильного приложения, 
с указанием количества регистраций и количества уникальных номеров телефонов для каждой версии.
2) Отображает список всех сообщений, ранее отправленных на указанный номер телефона.

### Переменные окружения
| Название|Значение по умолчанию|Описание|
| --- |------- | ------ |
| PG_URL |jdbc:postgresql://localhost:5432/db| Хост PostgreSQL|
| PG_USER |postgres| Имя пользователя PostgreSQL |
| PG_PASSWORD |admin| Пароль PostgreSQL |
| REDIS_HOST |localhost| Хост Redis |
| REDIS_PORT |6379| Порт Redis |
| REDIS_TTL |-1| время жизни кэша |
| EUREKA_HOST |http://localhost:8761/eureka| Хост register-app |

### Метрики

Метрики доступны на GET /actuator и для Prometheus на GET /actuator/prometheus

Swagger достуен на GET /v2/api-docs

#### Logger

Logger реализован на GrayLog. Протокол обмена GELF UDP. Должны быть установлены переменные окружения GRAYLOG_HOST и GRAYLOG_PORT
