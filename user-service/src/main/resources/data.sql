DROP TABLE IF EXISTS user;

CREATE TABLE user(
login_id VARCHAR(50),
name VARCHAR(50) NOT NULL,
genre VARCHAR(50) NOT NULL
) AS SELECT * FROM CSVREAD('classpath:user.csv');
