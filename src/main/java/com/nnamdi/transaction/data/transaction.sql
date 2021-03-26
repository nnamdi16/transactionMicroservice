CREATE DATABASE transaction_microservice;
USE AccountMicroservice;
DROP TABLE IF EXISTS transaction;
CREATE TABLE IF NOT EXISTS transaction  (
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    transaction_type VARCHAR(500) NOT NULL,
    amount decimal(10,2) ,
    transaction_description nvarchar(512) NOT NULL,
    account_id INT NOT NULL


    );

DROP TABLE IF EXISTS Roles;
CREATE TABLE IF NOT EXISTS Roles (
                                     role_id INT  PRIMARY KEY AUTO_INCREMENT,
                                     name VARCHAR(500) NOT NULL

    );

DROP TABLE IF EXISTS user_role;
CREATE TABLE IF NOT EXISTS UserRole (
                                        user_role_id INT PRIMARY KEY,
                                        account_id INT NOT NULL ,
                                        role_id INT(6) NOT NULL,
    FOREIGN KEY (account_id) REFERENCES transaction(account_id),
    FOREIGN KEY (role_id) REFERENCES Roles(role_id)


    );