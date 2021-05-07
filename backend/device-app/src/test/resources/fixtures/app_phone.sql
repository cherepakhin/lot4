delete from push_notification_phone;
delete from push_notification;
delete from phone;
delete from mobile_application;
insert into mobile_application(id,version) values (1,'v1');
insert into phone(id,number,token,is_active,app_id) values (1,'phone1','token1',true,1);
insert into phone(id,number,token,is_active,app_id) values (2,'phone2','token2',true,1);