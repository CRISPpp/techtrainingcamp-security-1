CREATE
DATABASE account_system;
USE
account_system;

DROP TABLE IF EXISTS user;
CREATE TABLE user
(
    id           INT PRIMARY KEY AUTO_INCREMENT,
    username     TEXT NOT NULL,
    password     TEXT NOT NULL,
    phone_number TEXT NOT NULL
)