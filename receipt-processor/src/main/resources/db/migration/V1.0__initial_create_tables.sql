CREATE TABLE receipt
(
	id              SERIAL PRIMARY KEY,
	external_id     VARCHAR(128) UNIQUE NOT NULL,
	retailer        VARCHAR(256) NOT NULL,
	purchase_date   VARCHAR(256) NOT NULL,
	purchase_time   VARCHAR(256) NOT NULL,
	total           VARCHAR(256) NOT NULL
);

CREATE TABLE item
(
	id                         SERIAL PRIMARY KEY,
	external_id                VARCHAR(128) UNIQUE NOT NULL,
	receipt_id                 INTEGER NOT NULL,
	short_description          TEXT NOT NULL,
	price                      FLOAT NOT NULL,
	FOREIGN KEY (receipt_id)   REFERENCES receipt(id)
);

CREATE TABLE points
(
	id                         SERIAL PRIMARY KEY,
	external_id                VARCHAR(128) UNIQUE NOT NULL,
	receipt_id                 INTEGER NOT NULL,
	points                     INTEGER NOT NULL,
	FOREIGN KEY (receipt_id)   REFERENCES receipt(id)
);
