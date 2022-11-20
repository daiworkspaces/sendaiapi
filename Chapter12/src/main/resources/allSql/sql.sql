use course;

create table user (id int(100) ,userName varchar(100),password varchar(200),sex varchar(10),age int(10),permission int(10),isDelete int(10));

create table addUserTest(id int(100),userName varchar(100),password varchar(200),sex varchar(10),age int(10),permission int(10),isDelete int(10),expected varchar(200));

create table getUserInfoCase(id int(100),userId int(100),expected varchar(200));

create table getUserListCase(id int(100), userName varchar(100),age int(10),sex varchar(10),expected varchar(200));

create table loginCase(id int(100),userName varchar(100),password varchar(200),expected varchar(200));

create table updateUserInfoCase(id int(100),userId int(100),userName varchar(100),sex varchar(10),age int(10),permission int(10),isDelete int(10),expected varchar(200));