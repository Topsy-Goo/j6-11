CREATE TABLE ourusers
(
	id			bigserial PRIMARY KEY,
	login		VARCHAR(32) NOT NULL UNIQUE,
	password	VARCHAR(64) NOT NULL,
	email		VARCHAR(64) NOT NULL UNIQUE
);
INSERT INTO ourusers (login, password, email) VALUES
	('super',	'$2a$12$c4HYjryn7vo1bYQfSzkUDe8jPhYIpInbUKZmv5lGnmcyrQPLIWnVu',	'super@post.ru'),	-- пароль 100
	('admin',	'$2a$12$c4HYjryn7vo1bYQfSzkUDe8jPhYIpInbUKZmv5lGnmcyrQPLIWnVu',	'admin@post.ru'),	-- пароль 100
	('user1',	'$2a$12$c4HYjryn7vo1bYQfSzkUDe8jPhYIpInbUKZmv5lGnmcyrQPLIWnVu',	'user1@post.ru'),	-- пароль 100
	('user2',	'$2a$12$c4HYjryn7vo1bYQfSzkUDe8jPhYIpInbUKZmv5lGnmcyrQPLIWnVu',	'user2@post.ru');	-- пароль 100

CREATE TABLE roles
(
	id		serial PRIMARY KEY,
	name	VARCHAR(64) NOT NULL UNIQUE
);
INSERT INTO roles (name) VALUES
	('ROLE_SUPERADMIN'), -- 1 только для суперадминов
	('ROLE_ADMIN'),		 -- 2 только для админов и суперадминов
	('ROLE_USER');		 -- 4 для НЕавторизованных юзеров

CREATE TABLE actions
(
	id		serial PRIMARY KEY,
	name	VARCHAR(64) NOT NULL UNIQUE
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

--CREATE TABLE rules
--(
--	id				serial PRIMARY KEY,
--	role_id			int NOT NULL,
--	action_id	int NOT NULL,
--	FOREIGN KEY (role_id)		REFERENCES roles (id),
--	FOREIGN KEY (action_id)	REFERENCES actions (id)
--);
--INSERT INTO rules (role_id, action_id) VALUES
--	(1, 1),	--  1  ROLE_GUEST	WALK
--	(2, 1),	--  2  ROLE_USER	WALK
--	(2, 2),	--  3  ROLE_USER	TALK
--	(3, 1),	--  4  ROLE_ADMIN	WALK
--	(3, 2),	--  5  ROLE_ADMIN	TALK
--	(3, 3),	--  6  ROLE_ADMIN	EAT
--	(4, 1),	--  7  ROLE_SUPERADMIN	WALK
--	(4, 2),	--  8  ROLE_SUPERADMIN	TALK
--	(4, 3),	--  9  ROLE_SUPERADMIN	EAT
--	(4, 4);	-- 10  ROLE_SUPERADMIN	SLEEP

--CREATE TABLE ourusers_rules
--(
--	user_id		bigint NOT NULL,
--	rule_id	int NOT NULL,
--	PRIMARY KEY (user_id, rule_id),
--	FOREIGN KEY (user_id)	REFERENCES ourusers (id),
--	FOREIGN KEY (rule_id)	REFERENCES rules (id)
--);
--INSERT INTO ourusers_rules (user_id, rule_id) VALUES
--	(1,  2), -- user1	rule2	:	ROLE_USER	WALK
--	(2,  3), -- user2	rule3	:	ROLE_USER	TALK
--
--	(3,  4), -- admin	rule4	:	ROLE_ADMIN	WALK
--	(3,  6), -- admin	rule6	:	ROLE_ADMIN	EAT
--
--	(4,  7), -- super	rule7	:	ROLE_SUPERADMIN	WALK
--	(4,  8), -- super	rule8	:	ROLE_SUPERADMIN	TALK
--	(4,  9), -- super	rule9	:	ROLE_SUPERADMIN	EAT
--	(4, 10); -- super	rule10	:	ROLE_SUPERADMIN	SLEEP
