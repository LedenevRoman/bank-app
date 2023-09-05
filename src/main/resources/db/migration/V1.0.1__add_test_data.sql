INSERT INTO managers (first_name, last_name, status, created_at, updated_at)
VALUES
    ('Alexander', 'Johnson', 'ACTIVE', '2023-09-02 13:57:40', '2023-09-02 13:57:40'),
    ('Emily', 'Anderson', 'ACTIVE', '2023-09-02 13:57:40', '2023-09-02 13:57:40'),
    ('Daniel', 'Thompson', 'ACTIVE', '2023-09-02 13:57:40', '2023-09-02 13:57:40'),
    ('Olivia', 'Davis', 'ACTIVE', '2023-09-02 13:57:40', '2023-09-02 13:57:40'),
    ('Benjamin', 'Wilson', 'ACTIVE', '2023-09-02 13:57:40', '2023-09-02 13:57:40'),
    ('Sophia', 'Martinez', 'ACTIVE', '2023-09-02 13:57:40', '2023-09-02 13:57:40'),
    ('Samuel', 'Taylor', 'ACTIVE', '2023-09-02 13:57:40', '2023-09-02 13:57:40'),
    ('Ava', 'Rodriguez', 'ACTIVE', '2023-09-02 13:57:40', '2023-09-02 13:57:40'),
    ('Matthew', 'Thomas', 'ACTIVE', '2023-09-02 13:57:40', '2023-09-02 13:57:40');

INSERT INTO clients (manager_id, status, tax_code, first_name, last_name, email, address, phone, created_at, updated_at)
VALUES
    (1, 'ACTIVE', 'E-PL', 'Isabella', 'White', 'isabella.white@yopmail.com ', '789 Elm St', '777-8888', '2023-09-01 13:57:40', '2023-09-01 13:57:40'),
    (2, 'ACTIVE', 'ER-PL', 'James', 'Harris', 'james.harris@yopmail.com ', '987 Main St', '333-1111', '2023-08-01 13:57:40', '2023-08-01 13:57:40'),
    (3, 'ACTIVE', 'ES-PL', 'Mia', 'Clark', 'mia.clark@yopmail.com ', '654 Elm St', '999-0000', '2023-08-02 13:57:40', '2023-09-01 13:57:40'),
    (4, 'ACTIVE', 'IS-PL', 'Joseph', 'Lewis', 'joseph.lewis@yopmail.com ', '123 Main St', '246-1357', '2023-08-09 13:57:40', '2023-09-01 13:57:40'),
    (5, 'ACTIVE', 'ES-PL', 'Charlotte', 'Lee', 'charlotte.lee@yopmail.com ', '321 Pine St', '864-2093', '2023-08-07 13:57:40', '2023-09-01 13:57:40'),
    (1, 'ACTIVE', 'IS-PL', 'David', 'Walker', 'david.walker@yopmail.com ', '987 Maple St', '502-8174', '2023-08-06 13:57:40', '2023-09-01 13:57:40'),
    (2, 'ACTIVE', 'ER-PL', 'Abigail', 'Hall', 'abigail.hall@yopmail.com ', '654 Oak St', '619-3847', '2023-08-05 13:57:40', '2023-08-01 13:57:40'),
    (3, 'ACTIVE', 'ESSP-PL', 'Christopher', 'Wright', 'christopher.wright@yopmail.com ', '789 Elm St', '205-6789', '2023-08-04 13:57:40', '2023-09-01 13:57:40'),
    (1, 'ACTIVE', 'E-PL', 'Harper', 'Young', 'harper.young@yopmail.com ', '321 Oak St', '714-2938', '2023-08-03 13:57:40', '2023-09-01 13:57:40'),
    (1, 'ACTIVE', 'ESSP-PL', 'Andrew', 'Allen', 'andrew.allen@yopmail.com ', '456 Maple St', '408-5726', '2023-08-10 13:57:40', '2023-08-10 13:57:40');

INSERT INTO products (manager_id, type, status, currency_code, interest_rate, created_at, updated_at)
VALUES
    (8, 'LOAN','ACTIVE', 'USD', 8.8888, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    (7, 'MORTGAGE', 'ACTIVE', 'USD', 7.7777, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    (6, 'DEPOSIT', 'ACTIVE', 'USD', 6.6666, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    (5, 'CAR_LOAN', 'ACTIVE', 'USD', 5.5555, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    (4, 'DEBIT_ACCOUNT', 'ACTIVE', 'USD', 4.4444, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    (3, 'CREDIT_CARD', 'ACTIVE', 'USD', 3.3333, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    (2, 'LOAN', 'ACTIVE', 'USD', 2.2222, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    (1, 'CREDIT_CARD', 'ACTIVE', 'USD', 1.1111, '2023-09-03 13:00:00', '2023-09-03 13:00:00');

INSERT INTO accounts (client_id, name, type, status, balance, currency_code, created_at, updated_at)
VALUES
    (1, 'IsabellaWDebit', 'DEBIT', 'ACTIVE', 10321.41, 'USD', '2023-09-04 13:57:40', '2023-09-04 13:57:40'),
    (1, 'IsabellaWCredit', 'CREDIT', 'ACTIVE', 5031.75, 'USD', '2023-09-04 13:57:40', '2023-09-04 13:57:40'),
    (2, 'JamesHCredit', 'CREDIT', 'ACTIVE', 8121.42, 'EUR', '2023-09-03 13:57:40', '2023-09-03 13:57:40'),
    (3, 'MiaCCredit', 'CREDIT', 'ACTIVE', 3002.11, 'USD', '2023-09-03 13:57:40', '2023-09-03 13:57:40'),
    (4, 'JosephLCredit', 'CREDIT', 'ACTIVE', 2453.24, 'EUR', '2023-09-03 13:57:40', '2023-09-03 13:57:40'),
    (5, 'CharlotteLCredit', 'CREDIT', 'ACTIVE', 7610.31, 'USD', '2023-09-03 13:57:40', '2023-09-03 13:57:40'),
    (6, 'DavidWCredit', 'CREDIT', 'ACTIVE', 9543.17, 'EUR', '2023-09-04 13:57:40', '2023-09-04 13:57:40'),
    (7, 'AbigailHCredit', 'CREDIT', 'ACTIVE', 4214.86, 'EUR', '2023-09-04 13:57:40', '2023-09-04 13:57:40'),
    (8, 'ChristopherWCredit', 'CREDIT', 'ACTIVE', 6520.96, 'USD', '2023-09-04 13:57:40', '2023-09-04 13:57:40'),
    (9, 'HarperYCredit', 'CREDIT', 'ACTIVE', 7120.88, 'EUR', '2023-09-03 13:57:40', '2023-09-03 13:57:40'),
    (10, 'AndrewACredit', 'CREDIT', 'ACTIVE', 3512.73, 'USD', '2023-09-03 13:57:40', '2023-09-03 13:57:40');

INSERT INTO agreements (account_id, product_id, status, sum, created_at, updated_at)
VALUES
    (1, 2, 'ACTIVE', 10000, '2023-09-03 12:00:00', '2023-09-03 12:00:00'),
    (2, 1, 'ACTIVE', 90000, '2023-09-03 12:00:00', '2023-09-03 12:00:00'),
    (3, 3, 'ACTIVE', 11000, '2023-09-03 12:00:00', '2023-09-03 12:00:00'),
    (4, 2, 'ACTIVE', 15000, '2023-09-03 12:00:00', '2023-09-03 12:00:00'),
    (5, 1, 'ACTIVE', 8000, '2023-09-03 12:00:00', '2023-09-03 12:00:00'),
    (6, 3, 'ACTIVE', 13000, '2023-09-03 12:00:00', '2023-09-03 12:00:00'),
    (7, 2, 'ACTIVE', 12000, '2023-09-03 12:00:00', '2023-09-03 12:00:00'),
    (8, 1, 'ACTIVE', 7000, '2023-09-03 12:00:00', '2023-09-03 12:00:00'),
    (9, 3, 'ACTIVE', 14000, '2023-09-03 12:00:00', '2023-09-03 12:00:00'),
    (10, 2, 'ACTIVE', 6000, '2023-09-03 12:00:00', '2023-09-03 12:00:00'),
    (11, 1, 'ACTIVE', 5000, '2023-09-03 12:00:00', '2023-09-03 12:00:00');

INSERT INTO transactions (debit_account_id, credit_account_id, type, amount, description, created_at)
VALUES
    (1, 2, 'CASH', 1037.58, 'for ice cream', '2023-09-03 12:00:00'),
    (2, 3, 'CASH', 845.67, 'for ice cream', '2023-09-03 12:00:00'),
    (3, 4, 'CASH', 541.63, 'for ice cream', '2023-09-03 12:00:00'),
    (4, 5, 'CASH', 375.64, 'for ice cream', '2023-09-03 12:00:00'),
    (5, 6, 'CASH', 258.81, 'for ice cream', '2023-09-03 12:00:00'),
    (6, 7, 'CASH', 761.45, 'for ice cream', '2023-09-03 12:00:00'),
    (7, 8, 'CASH', 135.77, 'for ice cream', '2023-09-03 12:00:00'),
    (8, 9, 'CASH', 428.66, 'for ice cream', '2023-09-03 12:00:00'),
    (9, 10, 'CASH', 744.63, 'for ice cream', '2023-09-03 12:00:00'),
    (10, 11, 'CASH', 269.78, 'for ice cream', '2023-09-03 12:00:00'),
    (11, 1, 'CASH', 899.99, 'for ice cream', '2023-09-03 12:00:00');