create table launch (
      id serial not null,
      description varchar(100) not null,
      due_date date not null,
      payment_date date,
      value decimal not null,
      note varchar(100),
      type varchar(30) not null,
      category_id bigint not null,
      person_id bigint not null,
    
      foreign key (category_id) references category(id),
      foreign key (person_id) references person(id),
    
      primary key (id)
);

insert into launch (description, due_date, payment_date, value, note, type, category_id, person_id) values ('Salário mensal', '2019-06-10', null, 6500.00, 'Distribuição de lucros', 'RECEITA', 1, 1);
insert into launch (description, due_date, payment_date, value, note, type, category_id, person_id) values ('Bahamas', '2019-02-10', '2019-02-10', 100.32, null, 'DESPESA', 2, 2);
insert into launch (description, due_date, payment_date, value, note, type, category_id, person_id) values ('Top Club', '2019-06-10', null, 120, null, 'RECEITA', 3, 3);
insert into launch (description, due_date, payment_date, value, note, type, category_id, person_id) values ('CEMIG', '2019-02-10', '2019-02-10', 110.44, 'Geração', 'RECEITA', 3, 4);
insert into launch (description, due_date, payment_date, value, note, type, category_id, person_id) values ('DMAE', '2019-06-10', null, 200.30, null, 'DESPESA', 3, 5);
insert into launch (description, due_date, payment_date, value, note, type, category_id, person_id) values ('Extra', '2019-03-10', '2019-03-10', 1010.32, null, 'RECEITA', 4, 6);
insert into launch (description, due_date, payment_date, value, note, type, category_id, person_id) values ('Bahamas', '2019-06-10', null, 500, null, 'RECEITA', 1, 3);
insert into launch (description, due_date, payment_date, value, note, type, category_id, person_id) values ('Top Club', '2019-03-10', '2019-03-10', 400.32, null, 'DESPESA', 4, 1);
insert into launch (description, due_date, payment_date, value, note, type, category_id, person_id) values ('Despachante', '2019-06-10', null, 123.64, 'Multas', 'DESPESA', 3, 2);
insert into launch (description, due_date, payment_date, value, note, type, category_id, person_id) values ('Pneus', '2019-04-10', '2019-04-10', 665.33, null, 'RECEITA', 5, 3);
insert into launch (description, due_date, payment_date, value, note, type, category_id, person_id) values ('Café', '2019-06-10', null, 8.32, null, 'DESPESA', 1, 4);
insert into launch (description, due_date, payment_date, value, note, type, category_id, person_id) values ('Eletrônicos', '2019-04-10', '2019-04-10', 2100.32, null, 'DESPESA', 5, 5);
insert into launch (description, due_date, payment_date, value, note, type, category_id, person_id) values ('Instrumentos', '2019-06-10', null, 1040.32, null, 'DESPESA', 4, 6);
insert into launch (description, due_date, payment_date, value, note, type, category_id, person_id) values ('Café', '2019-04-10', '2019-04-10', 4.32, null, 'DESPESA', 4, 2);
insert into launch (description, due_date, payment_date, value, note, type, category_id, person_id) values ('Lanche', '2019-06-10', null, 10.20, null, 'DESPESA', 4, 1);