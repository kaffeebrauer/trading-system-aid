drop table if exists currency;
create table currency (
	id BIGINT PRIMARY KEY auto_increment,
	code VARCHAR(3),
	description VARCHAR(50),
	fx_rate FLOAT,
	modification_timestamp timestamp,
	creation_timestamp timestamp
) ENGINE=MyISAM;

DROP TABLE IF EXISTS transaction;
CREATE TABLE transaction (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	type VARCHAR(4) NOT NULL,
	brokerage FLOAT,
	currency_id BIGINT NOT NULL,
	description VARCHAR(50),
	price FLOAT NOT NULL,
	quantity INT(11) NOT NULL,
	reference_number
	
	