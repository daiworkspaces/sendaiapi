use course;

create table user (id int(100) ,userName varchar(100),password varchar(200),sex varchar(10),age int(10),permission int(10),isDelete int(10));

create table addUserCase(id int(100),userName varchar(100),password varchar(200),sex varchar(10),age int(10),permission int(10),isDelete int(10),expected varchar(200));

create table getUserInfoCase(id int(100),userId int(100),expected varchar(200));

create table getUserListCase(id int(100), userName varchar(100),age int(10),sex varchar(10),expected varchar(200));

create table loginCase(id int(100),userName varchar(100),password varchar(200),expected varchar(200));

create table updateUserInfoCase(id int(100),userId int(100),userName varchar(100),sex varchar(10),age int(10),permission int(10),isDelete int(10),expected varchar(200));

insert into loginCase  value(1,"zhangsan","123456","true");
insert into loginCase  value(2,"zhangsan","123","false");

select * from loginCase;
show tables;
select * from user;
delete from user;
insert into user  value (1,"zhnagshan","123456",0,20,0,0);
insert into user  value (2,"lisi","12345678",0,30,1,0);
insert into user  value (3,"wangwu","123",0,24,0,0);
insert into user  value (4,"zhouli","234324",0,27,0,0);
insert into user  value (5,"zhang1","tttr54",1,30,0,0);
insert into user  value (6,"zhou2","675675",1,50,0,0);
insert into user  value (7,"li3","123232",1,60,0,0);
insert into user  value (8,"zhou8","324234",1,30,0,0);
insert into user  value (9,"zhouliu","4353453",0,24,0,0);

select * from addusercase;

insert into addusercase value (1,"zhang009","1234567",0,21,1,0,true);
insert into addusercase value (2,"zhang209","14234567",1,31,0,0,true);
insert into addusercase value (3,"zhang309","125434567",2,41,0,0,false);

alter table addusercase modify expected tinyint(1) default null comment '期望结果';

delete from addusercase;

select * from getuserinfocase;
insert into getuserinfocase value (1,1,"getUserInfo");
insert into getuserinfocase value (2,2,"getUserInfo");
insert into getuserinfocase value (3,3,"getUserInfo");
insert into getuserinfocase value (4,4,"getUserInfo");
insert into getuserinfocase value (5,5,"getUserInfo");

select * from getuserlistcase;
delete from getuserlistcase;
insert into getuserlistcase value (1,"zhou1",23,1,"getUserList");
insert into getuserlistcase value (2,"zh3ou1",43,0,"getUserList");
insert into getuserlistcase value (3,"zho2u1",53,0,"getUserList");
insert into getuserlistcase value (4,"zho4u1",26,1,"getUserList");

select * from updateuserinfocase;
insert into updateuserinfocase value(1,2,"hahahahah",null,null,null,null,true);
insert into updateuserinfocase value(2,3,null,1,null,null,1,true);
insert into updateuserinfocase value(3,4,null,null,18,null,1,true);
insert into updateuserinfocase value(4,5,null,null,null,1,1,true);

delete from updateuserinfocase;

