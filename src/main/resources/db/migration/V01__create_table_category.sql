create table category (
	id serial not null,
	name varchar(50) not null,
	
	primary key (id)
);

insert into category (name) values('Lazer');
insert into category (name) values('Alimentação');
insert into category (name) values('Supermercado');
insert into category (name) values('Farmácia');
insert into category (name) values('Combustível');
insert into category (name) values('Outros');