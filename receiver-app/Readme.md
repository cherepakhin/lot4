
### Переменные окружения
| Название|Значение по умолчанию|Описание|
| --- |------- | ------ |
| PG_URL |jdbc:postgresql://localhost:5432/db| Хост PostgreSQL|
| PG_USER |postgres| Имя пользователя PostgreSQL |
| PG_PASSWORD |admin| Пароль PostgreSQL |
| REDIS_HOST |127.0.0.1| Адрес сервиса кэширования Redis |
| REDIS_PORT |6379| Порт сервиса кэширования Redis |

### Метрики

Метрики доступны на GET /actuator и для Prometheus на GET /actuator/prometheus

#### Logger

Logger реализован на GrayLog. Протокол обмена GELF UDP. Должны быть установлены переменные окружения GRAYLOG_HOST и GRAYLOG_PORT
