<!--create table employeereimbusement.users (
	id serial primary key,
	username varchar(50)  not null,
	pass_word varchar(50) not null,
	first_name varchar(50) not null,
	last_name varchar(50) not null,
	email varchar(256) not NULL
);


insert into employeereimbusement.users (username, pass_word, first_name, last_name, email) 
	values ('Test','password','test','person','test.person@example.com')--!>
	
	
ALTER TABLE employeereimbusement.users ADD role_id number FOREIGN KEY REFERENCES user_role(id);