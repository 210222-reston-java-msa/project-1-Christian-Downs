create table user_roles (
	user_role varchar(50) not null,
	userid serial  primary key,
	FOREIGN key (userid) references users(id) 
);


insert into user_roles (user_role, userid) values ('Manager',1);