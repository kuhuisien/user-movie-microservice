DROP TABLE IF EXISTS movie;

CREATE TABLE movie(
  id int NOT NULL,
  title VARCHAR(120) NOT NULL,
  year int,
  rating double,
  genre VARCHAR(50)
) AS SELECT * FROM CSVREAD('classpath:movie.csv');