CREATE TABLE IF NOT EXISTS MANAGERS(
	id INT PRIMARY KEY AUTO_INCREMENT,
	first_name varchar(50),
	last_name varchar(50),
    status varchar(20),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS CLIENTS(
	id int PRIMARY KEY AUTO_INCREMENT,
    manager_id int,
    status varchar(20),
    tax_code varchar(20),
    first_name varchar(50),
    last_name varchar(50),
    email varchar(60),
    address varchar(80),
    phone varchar(20),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (manager_id) REFERENCES managers(id)
);

CREATE TABLE IF NOT EXISTS PRODUCTS(
	id INT PRIMARY KEY AUTO_INCREMENT,
    manager_id int,
    status varchar(20),
    currency_code varchar(20),
    interest_rate decimal(6,4),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (manager_id) REFERENCES managers(id)
);

CREATE TABLE IF NOT EXISTS ACCOUNTS(
	id INT PRIMARY KEY AUTO_INCREMENT,
	client_id int,
	name varchar(100),
    type varchar(20),
	status varchar(20),
    balance decimal(15,2),
    currency_code varchar(3),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (client_id) REFERENCES clients(id)
);

CREATE TABLE IF NOT EXISTS AGREEMENTS(
	id INT PRIMARY KEY AUTO_INCREMENT,
	account_id int,
	product_id int,
	interest_rate decimal(6,4),
	status varchar(20),
    sum decimal(15,2),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES accounts(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE IF NOT EXISTS TRANSACTIONS(
	id INT PRIMARY KEY AUTO_INCREMENT,
	debit_account_id int,
	credit_account_id int,
    type varchar(20),
	amount decimal(12,4),
    description varchar(255),
    created_at TIMESTAMP,
    FOREIGN KEY (debit_account_id) REFERENCES accounts(id),
    FOREIGN KEY (credit_account_id) REFERENCES accounts(id)
);