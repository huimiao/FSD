create table users (
	id int IDENTITY(10001,1) primary key,
	username varchar(20) unique not null,
	password varchar(512) not null,
	first_name varchar(30) not null,
	last_name varchar(30) not null,
	email varchar(30) not null,
	enabled BOOLEAN not null default true
);

create table authorities(
    id int IDENTITY(10001,1) primary key,
    authority varchar(20) unique not null
);

create table roles(
    id int IDENTITY(10001,1) primary key,
    role varchar(20) unique not null
);


create table user_role(
    id int IDENTITY(10001,1) primary key,
    uid int not null,
    rid int not null
);

create table role_authority(
    id int IDENTITY(10001,1) primary key,
    rid int not null,
    aid int not null
);

--raw password 123456
insert into users( username, password, first_name, last_name, email) values('test', '{bcrypt}$2a$10$QH13tIzX5BvpQGLhW8oFpex7UeFCPJQvNLNpID.Qf8zyzF7GdXvq2', 'f', 'l', 'fl@huimiao.com');
insert into users( username, password, first_name, last_name, email) values('admin', '{bcrypt}$2a$10$QH13tIzX5BvpQGLhW8oFpex7UeFCPJQvNLNpID.Qf8zyzF7GdXvq2', 'f', 'l', 'fl@huimiao.com');
insert into roles(role) values('ROLE_USER');
insert into roles(role) values('ROLE_ADMIN');
insert into user_role(uid, rid) values(10001, 10001);
insert into user_role(uid, rid) values(10002, 10002);