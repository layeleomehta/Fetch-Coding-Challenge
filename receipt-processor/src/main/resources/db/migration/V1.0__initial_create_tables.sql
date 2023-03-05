CREATE TABLE chat_user
(
	id              SERIAL PRIMARY KEY,
	external_id     VARCHAR(128) UNIQUE NOT NULL,
	username        VARCHAR(128) NOT NULL
);
