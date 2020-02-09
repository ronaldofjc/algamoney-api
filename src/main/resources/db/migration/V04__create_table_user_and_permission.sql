CREATE TABLE users (
	id BIGINT NOT NULL,
	name VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	password VARCHAR(150) NOT NULL,

  	PRIMARY KEY (id)
);

CREATE TABLE permission (
	id BIGINT NOT NULL,
	description VARCHAR(50) NOT NULL,

  	PRIMARY KEY (id)
);

CREATE TABLE user_permission (
	user_id BIGINT NOT NULL,
	permission_id BIGINT NOT NULL,
	PRIMARY KEY (user_id, permission_id),
	FOREIGN KEY (user_id) REFERENCES users(id),
	FOREIGN KEY (permission_id) REFERENCES permission(id)
);

INSERT INTO users (id, name, email, password) values (1, 'Administrador', 'admin@admin.com', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.');
INSERT INTO users (id, name, email, password) values (2, 'Convidado', 'convidado@convidado.com', '$2a$10$B7/aoC88qfbsEDkFcnj79uMt78t5qVSvMI3.IgdXcnkzR00JNdkDK');

INSERT INTO permission (id, description) values (1, 'ROLE_CADASTRAR_CATEGORIA');
INSERT INTO permission (id, description) values (2, 'ROLE_PESQUISAR_CATEGORIA');
INSERT INTO permission (id, description) values (3, 'ROLE_REMOVER_CATEGORIA');
INSERT INTO permission (id, description) values (4, 'ROLE_ATUALIZAR_CATEGORIA');

INSERT INTO permission (id, description) values (5, 'ROLE_CADASTRAR_PESSOA');
INSERT INTO permission (id, description) values (6, 'ROLE_REMOVER_PESSOA');
INSERT INTO permission (id, description) values (7, 'ROLE_PESQUISAR_PESSOA');
INSERT INTO permission (id, description) values (8, 'ROLE_ATUALIZAR_PESSOA');

INSERT INTO permission (id, description) values (9, 'ROLE_CADASTRAR_LANCAMENTO');
INSERT INTO permission (id, description) values (10, 'ROLE_REMOVER_LANCAMENTO');
INSERT INTO permission (id, description) values (11, 'ROLE_PESQUISAR_LANCAMENTO');
INSERT INTO permission (id, description) values (12, 'ROLE_ATUALIZAR_LANCAMENTO');

INSERT INTO permission (id, description) values (13, 'ROLE_CADASTRAR_USUARIO');
INSERT INTO permission (id, description) values (14, 'ROLE_REMOVER_USUARIO');
INSERT INTO permission (id, description) values (15, 'ROLE_PESQUISAR_USUARIO');
INSERT INTO permission (id, description) values (16, 'ROLE_ATUALIZAR_USUARIO');

-- admin
INSERT INTO user_permission (user_id, permission_id) values (1, 1);
INSERT INTO user_permission (user_id, permission_id) values (1, 2);
INSERT INTO user_permission (user_id, permission_id) values (1, 3);
INSERT INTO user_permission (user_id, permission_id) values (1, 4);
INSERT INTO user_permission (user_id, permission_id) values (1, 5);
INSERT INTO user_permission (user_id, permission_id) values (1, 6);
INSERT INTO user_permission (user_id, permission_id) values (1, 7);
INSERT INTO user_permission (user_id, permission_id) values (1, 8);
INSERT INTO user_permission (user_id, permission_id) values (1, 9);
INSERT INTO user_permission (user_id, permission_id) values (1, 10);
INSERT INTO user_permission (user_id, permission_id) values (1, 11);
INSERT INTO user_permission (user_id, permission_id) values (1, 12);
INSERT INTO user_permission (user_id, permission_id) values (1, 13);
INSERT INTO user_permission (user_id, permission_id) values (1, 14);
INSERT INTO user_permission (user_id, permission_id) values (1, 15);
INSERT INTO user_permission (user_id, permission_id) values (1, 16);

-- convidado
INSERT INTO user_permission (user_id, permission_id) values (2, 2);
INSERT INTO user_permission (user_id, permission_id) values (2, 7);
INSERT INTO user_permission (user_id, permission_id) values (2, 11);
