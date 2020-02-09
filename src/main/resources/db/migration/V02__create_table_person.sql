create table person (
	id serial not null,
	name varchar(200) not null,
  	active boolean not null,
  	street varchar(200),
	number varchar(30),
  	complement varchar(200),
  	district varchar(100),
  	zipcode varchar(50),
  	city varchar(100),
  	state varchar(100),

	primary key (id)
);

insert into person (name, active, street, number, district, zipcode, city, state) values('Helena Feitosa', true, 'Rua Um', '100', 'Bairro Um', '01000-000', 'São Paulo', 'São Paulo');
insert into person (name, active, street, number, district, zipcode, city, state) values('Suzana Feitosa', true, 'Rua Um', '100', 'Bairro Um', '01000-000', 'São Paulo', 'São Paulo');
insert into person (name, active, street, number, district, zipcode, city, state) values('Maria Feitosa', true, 'Rua Um', '100', 'Bairro Um', '01000-000', 'São Paulo', 'São Paulo');
insert into person (name, active) values('Ronaldo Feitosa', true);
insert into person (name, active) values('Joice Feitosa', true);
insert into person (name, active) values('Rebecca Feitosa', false);