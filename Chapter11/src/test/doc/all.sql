use course;
create table user (
                      id int(30),
                      name varchar(32),
                      age int(30),
                      sex varchar(30)
);
show tables;
select * from user;
insert into user values(1,"zhangsan",18,"男");
insert into user values(2,"lisi",20,"女");
insert into user values(3,"王五",25,"男");