CREATE table if not exists mobile_application (
	"id"            serial      not null UNIQUE,
	"name"          varchar(255)    not null,
	"version"       varchar(20)     not null
);

CREATE TABLE if not exists push_notification (
	"id"            serial          not null UNIQUE,
	"title"         varchar(255)    not null,
    "body"          text            not null,
    "date"          Date            not null,
    "status"        int             not null,
    "phone_id"      serial          not null
);

CREATE TABLE if not exists phone (
	"id"            serial      not null UNIQUE,
	"token"         text        not null,
	"number"        varchar(20) not null,
    "app_id"        serial      not null
);

ALTER TABLE phone ADD FOREIGN KEY (app_id) REFERENCES mobile_application (id);

ALTER TABLE push_notification ADD FOREIGN KEY (phone_id) REFERENCES public.Phone (id);