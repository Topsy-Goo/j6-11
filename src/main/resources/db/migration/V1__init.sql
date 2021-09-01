CREATE TABLE ourusers
(
	id			bigserial,
	login		VARCHAR(32) NOT NULL UNIQUE,
	password	VARCHAR(64) NOT NULL,
	email		VARCHAR(64) NOT NULL UNIQUE,
	PRIMARY KEY (id)
);
INSERT INTO ourusers (login, password, email) VALUES
	('super',	'$2a$12$c4HYjryn7vo1bYQfSzkUDe8jPhYIpInbUKZmv5lGnmcyrQPLIWnVu',	'super@post.ru'),	-- пароль 100
	('admin',	'$2a$12$c4HYjryn7vo1bYQfSzkUDe8jPhYIpInbUKZmv5lGnmcyrQPLIWnVu',	'admin@post.ru'),	-- пароль 100
	('user1',	'$2a$12$c4HYjryn7vo1bYQfSzkUDe8jPhYIpInbUKZmv5lGnmcyrQPLIWnVu',	'user1@post.ru'),	-- пароль 100
	('user2',	'$2a$12$c4HYjryn7vo1bYQfSzkUDe8jPhYIpInbUKZmv5lGnmcyrQPLIWnVu',	'user2@post.ru');	-- пароль 100

CREATE TABLE roles
(
	id		serial,
	name	VARCHAR(64) NOT NULL UNIQUE,
	PRIMARY KEY (id)
);
INSERT INTO roles (name) VALUES
	('ROLE_SUPERADMIN'), -- 1
	('ROLE_ADMIN'),		 -- 2
	('ROLE_USER');		 -- 4

CREATE TABLE actions
(
	id		serial,
	name	VARCHAR(64) NOT NULL UNIQUE,
	PRIMARY KEY (id)
);
INSERT INTO actions (name) VALUES
	('WALK'),	-- 1
	('TALK'),	-- 2
	('EAT'),	-- 3
	('SLEEP');	-- 4

CREATE TABLE ourusers_roles
(
	user_id		bigint NOT NULL,
	role_id		int NOT NULL,
	PRIMARY KEY (user_id, role_id),
	FOREIGN KEY (user_id) REFERENCES ourusers (id),
	FOREIGN KEY (role_id) REFERENCES roles (id)
);
INSERT INTO ourusers_roles (user_id, role_id) VALUES
	(1, 1), -- super	ROLE_SUPERADMIN
	(2, 2), -- admin	ROLE_ADMIN
	(3, 3),	-- user1	ROLE_USER
	(4, 3); -- user2	ROLE_USER

CREATE TABLE ourusers_actions
(
	user_id		bigint NOT NULL,
	action_id	int NOT NULL,
	PRIMARY KEY (user_id, action_id),
	FOREIGN KEY (user_id) REFERENCES ourusers (id),
	FOREIGN KEY (action_id) REFERENCES actions (id)
);
INSERT INTO ourusers_actions (user_id, action_id) VALUES
	(1, 1), -- super	WALK
	(1, 2), -- super	TALK
	(1, 3), -- super	EAT
	(1, 4), -- super	SLEEP

	(2, 1), -- admin	WALK
--	(2, 2), -- admin	TALK
	(2, 3), -- admin	EAT

	(3, 1), -- user1	WALK
	(3, 3), -- user1	EAT		- постороннему юзеру выдан пропуск в офисную столовку

	(4, 2); -- user2	TALK
