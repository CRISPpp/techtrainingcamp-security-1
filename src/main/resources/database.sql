CREATE DATABASE account_system;
USE account_system;

DROP TABLE IF EXISTS my_test;
CREATE TABLE my_test(
    id INT PRIMARY KEY AUTO_INCREMENT,
    msg TEXT
);

INSERT INTO my_test VALUES (1,'Hello, World');