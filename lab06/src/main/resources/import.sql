--raw password 123456
insert into users( username, password, first_name, last_name, email) values('test', '{bcrypt}$2a$10$18VfwnlsZh1wZHy0XCBabOSoOtVuTMHtW.VPb8b12KOK25r5t6LIK', 'f', 'l', 'fl@huimiao.com');
insert into users( username, password, first_name, last_name, email) values('admin', '{bcrypt}$2a$10$18VfwnlsZh1wZHy0XCBabOSoOtVuTMHtW.VPb8b12KOK25r5t6LIK', 'f', 'l', 'fl@huimiao.com');
insert into roles(role) values('ROLE_USER');
insert into roles(role) values('ROLE_ADMIN');
insert into user_role(uid, rid) values(10001, 10001);
insert into user_role(uid, rid) values(10002, 10002);