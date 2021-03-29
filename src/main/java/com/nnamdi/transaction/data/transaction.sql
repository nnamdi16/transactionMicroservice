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

DROP TABLE IF EXISTS finacialAccount;
CREATE TABLE IF NOT EXISTS financialAccount (
                                                financial_account_id INT PRIMARY KEY AUTO_INCREMENT,
                                                total_balance decimal(10,2) NOT NULL ,
                                                account_id INT NOT NULL

    );

ALTER TABLE transaction
ADD COLUMN date DATETIME;

ALTER TABLE transaction modify transaction_type INT;

INSERT INTO financial_account( total_balance, account_id) VALUES (1000.00,12345);

# DROP TABLE IF EXISTS user_role;
# CREATE TABLE IF NOT EXISTS UserRole (
#                                         user_role_id INT PRIMARY KEY,
#                                         account_id INT NOT NULL ,
#                                         role_id INT(6) NOT NULL,
#     FOREIGN KEY (account_id) REFERENCES transaction(account_id),
#     FOREIGN KEY (role_id) REFERENCES Roles(role_id)
#
#
#     );