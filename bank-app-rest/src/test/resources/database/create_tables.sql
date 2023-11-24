CREATE TABLE IF NOT EXISTS users
(
    id         int PRIMARY KEY AUTO_INCREMENT,
    role       varchar(20),
    status     varchar(20),
    password   varchar(60) NOT NULL,
    first_name varchar(50),
    last_name  varchar(50),
    email      varchar(60) UNIQUE,
    address    varchar(80),
    phone      varchar(20),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS products
(
    id            INT PRIMARY KEY AUTO_INCREMENT,
    name          varchar(70),
    type          varchar(20),
    status        varchar(20),
    interest_rate decimal(6, 4),
    min_limit     int,
    period_months int,
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP
);

CREATE TABLE IF NOT EXISTS accounts
(
    id            INT PRIMARY KEY AUTO_INCREMENT,
    client_id     int,
    number        varchar(28) UNIQUE,
    status        varchar(20),
    balance       decimal(15, 2),
    currency_code varchar(3),
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP,
    FOREIGN KEY (client_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS agreements
(
    id            INT PRIMARY KEY AUTO_INCREMENT,
    account_id    int,
    product_id    int,
    manager_id    int,
    status        varchar(20),
    sum           decimal(15, 2),
    start_date    DATE,
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES accounts (id),
    FOREIGN KEY (product_id) REFERENCES products (id),
    FOREIGN KEY (manager_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS transactions
(
    id                        INT PRIMARY KEY AUTO_INCREMENT,
    debit_account_id          int,
    credit_account_id         int,
    type                      varchar(20),
    amount                    decimal(12, 4),
    currency_code             varchar(3),
    debit_balance_difference  decimal(12, 4),
    credit_balance_difference decimal(12, 4),
    description               varchar(255),
    created_at                TIMESTAMP,
    FOREIGN KEY (debit_account_id) REFERENCES accounts (id),
    FOREIGN KEY (credit_account_id) REFERENCES accounts (id)
);
