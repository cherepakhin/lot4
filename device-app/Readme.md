#Web-API для вызова из мобильных приложений

### Переменные окружения
| Название|Значение по умолчанию|Описание|
| --- |------- | ------ |
| PG_URL |jdbc:postgresql://localhost:5432/db| Хост PostgreSQL|
| PG_USER |postgres| Имя пользователя PostgreSQL |
| PG_PASSWORD |admin| Пароль PostgreSQL |

### Метрики

Метрики доступны на GET **/actuator** и для Prometheus на GET **/actuator/prometheus**
Описание API доступно на GET **/swagger-ui/**