---------------------------------------------
-- CURRENCY
---------------------------------------------
drop table if exists currency;
create table currency (
	id BIGINT PRIMARY KEY auto_increment,
	code VARCHAR(3),
	description VARCHAR(50),
	fx_rate FLOAT,
	modification_timestamp timestamp,
	creation_timestamp timestamp
);
CREATE UNIQUE INDEX IF NOT EXISTS currency_code_unique_index ON CURRENCY (code);

---------------------------------------------
-- SECURITY
---------------------------------------------
DROP TABLE IF EXISTS `security`;
CREATE TABLE `security` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(5) NOT NULL,
  `country` varchar(3) NOT NULL,
  `description` varchar(50) default NULL,
  `portfolio_fk` int(11),
  `watchlist_fk` int(11),
  PRIMARY KEY  (`id`)
);
CREATE UNIQUE INDEX IF NOT EXISTS security_code_unique_index ON SECURITY (code);

---------------------------------------------
-- PORTFOLIO
---------------------------------------------
DROP TABLE IF EXISTS `portfolio`;
CREATE TABLE `portfolio` (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  description varchar(250),
  currency_id int(11) NOT NULL,
  PRIMARY KEY (`id`)
);
CREATE UNIQUE INDEX IF NOT EXISTS portfolio_name_unique_index ON PORTFOLIO (name);

---------------------------------------------
-- TECHNICAL ANALYSIS
---------------------------------------------
DROP TABLE IF EXISTS `technicalanalysis`;
CREATE TABLE `technicalanalysis` (
  `id` int(11) NOT NULL auto_increment,
  `lower_channel` decimal(19,2) default NULL,
  `stop_loss` decimal(19,2) NOT NULL,
  `upper_channel` decimal(19,2) default NULL,
  PRIMARY KEY  (`id`)
);

---------------------------------------------
-- TRANSACTION
---------------------------------------------
DROP TABLE IF EXISTS `transaction`;
CREATE TABLE `transaction` (
  `id` int(11) NOT NULL PRIMARY KEY auto_increment,
  `type` varchar(31) NOT NULL,
  `brokerage` decimal(19,2) default NULL,
  `description` varchar(50) default NULL,
  `price` decimal(19,2) NOT NULL,
  `quantity` bigint(20) NOT NULL,
  `reference_number` varchar(255) default NULL,
  `timestamp` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `technical_analysis_id` int(11) default NULL,
  `currency_id` int(11) NOT NULL,
  `security_id` int(11) NOT NULL,
  `shareholding_fk` int(11)
);

---------------------------------------------
-- BUY SELL TRANSACTION
---------------------------------------------
DROP TABLE IF EXISTS `buyselltransaction`;
CREATE TABLE `buyselltransaction` (
  `id` int(11) NOT NULL auto_increment,
  `sell_transaction_fk` int(11) default NULL,
  `buy_transaction_fk` int(11) default NULL,
  PRIMARY KEY  (`id`)
);

---------------------------------------------
-- WATCHLIST
---------------------------------------------
DROP TABLE IF EXISTS `WatchList`;
CREATE TABLE `WatchList` (
  `id` int(11) NOT NULL PRIMARY KEY auto_increment,
  `name` varchar(50) NOT NULL,
  `description` varchar(250)
);

---------------------------------------------
-- DIVIDEND
---------------------------------------------
DROP TABLE IF EXISTS `Dividend`;
CREATE TABLE `Dividend` (
  `id` int(11) NOT NULL PRIMARY KEY auto_increment,
  `franked_percentage` int(5) NOT NULL,
  `number_of_shares` int(11) NOT NULL,
  `payment_amount_per_share` decimal(15,4) NOT NULL,
  `payment_date` timestamp NOT NULL,
  `shareholding_fk` int(11) NOT NULL,
  `type` varchar(15) NOT NULL,
  `alloted_shares` int(5),
  `allotment_share_price` decimal(15,4),
  `shareholding_fk` int(11)
);

---------------------------------------------
-- SHARE HOLDING
---------------------------------------------
DROP TABLE IF EXISTS `ShareHolding`;
CREATE TABLE `ShareHolding` (
  `id` int(11) NOT NULL PRIMARY KEY auto_increment,
  `amount_of_shares_held` int(11) NOT NULL,
  `security_fk` int(11) NOT NULL,
  `portfolio_fk` int(11),
  `has_investment_plan_dividends` int(1) NOT NULL
);
CREATE UNIQUE INDEX IF NOT EXISTS shareholding_security_fk_unique_index ON ShareHolding (security_fk);