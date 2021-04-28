CREATE table if not exists mobile_application (
	"id"            serial          not null UNIQUE,
	"version"       varchar(20)     not null
);

CREATE TABLE if not exists push_notification (
	"id"            serial          not null UNIQUE,
	"title"         varchar(255)    not null,
    "body"          text            not null,
    "date"          timestamp       not null,
    "status"        int                       DEFAULT 0
);

CREATE TABLE if not exists phone (
	"id"            serial      not null UNIQUE,
	"token"         text        not null,
	"number"        varchar(20) not null,
    "app_id"        serial      not null,
    "is_active"     boolean     not null DEFAULT true
);

CREATE TABLE if not exists push_notification_phone (
    "push_notification_id"  serial  not null,
    "phone_id"  serial  not null
);

ALTER TABLE phone ADD FOREIGN KEY (app_id) REFERENCES mobile_application (id);

ALTER TABLE push_notification_phone ADD FOREIGN KEY (push_notification_id) REFERENCES push_notification (id);

ALTER TABLE push_notification_phone ADD FOREIGN KEY (phone_id) REFERENCES phone (id);