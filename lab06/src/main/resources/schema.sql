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

create table persistent_logins (
  username varchar(64) not null,
  series varchar(64) primary key,
  token varchar(64) not null,
  last_used timestamp not null
);
