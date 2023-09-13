INSERT INTO users (role, status, password, first_name, last_name, email, address, phone, created_at, updated_at)
VALUES
    ('CLIENT', 'ACTIVE', '$2a$04$pqlgizIi1uE4Tb9BxRT3D.qonNfBIXxCDiq.r2tL5vqQKNGQ25IE6', 'Isabella', 'White', 'isabella.white@yopmail.com ', '789 Elm St', '777-8888', '2023-09-01 13:57:40', '2023-09-01 13:57:40'),
    ('CLIENT', 'ACTIVE', '$2a$04$pqlgizIi1uE4Tb9BxRT3D.qonNfBIXxCDiq.r2tL5vqQKNGQ25IE6', 'James', 'Harris', 'james.harris@yopmail.com ', '987 Main St', '333-1111', '2023-08-01 13:57:40', '2023-08-01 13:57:40'),
    ('MANAGER', 'ACTIVE', '$2a$04$pqlgizIi1uE4Tb9BxRT3D.qonNfBIXxCDiq.r2tL5vqQKNGQ25IE6', 'Mia', 'Clark', 'mia.clark@yopmail.com ', '654 Elm St', '999-0000', '2023-08-02 13:57:40', '2023-09-01 13:57:40'),
    ('CLIENT', 'ACTIVE', '$2a$04$pqlgizIi1uE4Tb9BxRT3D.qonNfBIXxCDiq.r2tL5vqQKNGQ25IE6', 'Joseph', 'Lewis', 'joseph.lewis@yopmail.com ', '123 Main St', '246-1357', '2023-08-09 13:57:40', '2023-09-01 13:57:40'),
    ('CLIENT', 'ACTIVE', '$2a$04$pqlgizIi1uE4Tb9BxRT3D.qonNfBIXxCDiq.r2tL5vqQKNGQ25IE6', 'Charlotte', 'Lee', 'charlotte.lee@yopmail.com ', '321 Pine St', '864-2093', '2023-08-07 13:57:40', '2023-09-01 13:57:40'),
    ('MANAGER', 'ACTIVE', '$2a$04$pqlgizIi1uE4Tb9BxRT3D.qonNfBIXxCDiq.r2tL5vqQKNGQ25IE6', 'David', 'Walker', 'david.walker@yopmail.com ', '987 Maple St', '502-8174', '2023-08-06 13:57:40', '2023-09-01 13:57:40'),
    ('CLIENT', 'ACTIVE', '$2a$04$pqlgizIi1uE4Tb9BxRT3D.qonNfBIXxCDiq.r2tL5vqQKNGQ25IE6', 'Abigail', 'Hall', 'abigail.hall@yopmail.com ', '654 Oak St', '619-3847', '2023-08-05 13:57:40', '2023-08-01 13:57:40'),
    ('CLIENT', 'ACTIVE', '$2a$04$pqlgizIi1uE4Tb9BxRT3D.qonNfBIXxCDiq.r2tL5vqQKNGQ25IE6', 'Christopher', 'Wright', 'christopher.wright@yopmail.com ', '789 Elm St', '205-6789', '2023-08-04 13:57:40', '2023-09-01 13:57:40'),
    ('CLIENT', 'ACTIVE', '$2a$04$pqlgizIi1uE4Tb9BxRT3D.qonNfBIXxCDiq.r2tL5vqQKNGQ25IE6', 'Harper', 'Young', 'harper.young@yopmail.com ', '321 Oak St', '714-2938', '2023-08-03 13:57:40', '2023-09-01 13:57:40'),
    ('MANAGER', 'ACTIVE', '$2a$04$pqlgizIi1uE4Tb9BxRT3D.qonNfBIXxCDiq.r2tL5vqQKNGQ25IE6', 'Andrew', 'Allen', 'andrew.allen@yopmail.com ', '456 Maple St', '408-5726', '2023-08-10 13:57:40', '2023-08-10 13:57:40');

INSERT INTO products (type, status, interest_rate, max_limit, period_months, created_at, updated_at)
VALUES
    ('LOAN','ACTIVE', 5, 100000, 24, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('LOAN','ACTIVE', 10, 50000, 24, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('LOAN','ACTIVE', 7, 100000, 36, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('LOAN','ACTIVE', 20, 10000, 12, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('LOAN','ACTIVE', 15, 1000, 24, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('MORTGAGE', 'ACTIVE', 10, 100000, 24, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('DEPOSIT', 'ACTIVE', 10, 100000, 24, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('CAR_LOAN', 'ACTIVE', 7, 200000, 24, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('DEBIT_ACCOUNT', 'ACTIVE', 5, 100000, 24, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('CREDIT_CARD', 'ACTIVE', 5, 100000, 24, '2023-09-03 13:00:00', '2023-09-03 13:00:00');

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

INSERT INTO agreements (account_id, product_id, manager_id, status, sum, created_at, updated_at)
VALUES
    (1, 2, 3, 'ACTIVE', 10000, '2023-09-03 12:00:00', '2023-09-03 12:00:00'),
    (2, 1, 6,'ACTIVE', 90000, '2023-09-03 12:00:00', '2023-09-03 12:00:00'),
    (3, 3, 10, 'ACTIVE', 11000, '2023-09-03 12:00:00', '2023-09-03 12:00:00'),
    (4, 2, 3, 'ACTIVE', 15000, '2023-09-03 12:00:00', '2023-09-03 12:00:00'),
    (5, 1, 6, 'ACTIVE', 8000, '2023-09-03 12:00:00', '2023-09-03 12:00:00'),
    (6, 3, 10, 'ACTIVE', 13000, '2023-09-03 12:00:00', '2023-09-03 12:00:00'),
    (7, 2, 3, 'ACTIVE', 12000, '2023-09-03 12:00:00', '2023-09-03 12:00:00'),
    (8, 1, 6,  'ACTIVE', 7000, '2023-09-03 12:00:00', '2023-09-03 12:00:00'),
    (9, 3, 10,  'ACTIVE', 14000, '2023-09-03 12:00:00', '2023-09-03 12:00:00'),
    (10, 2, 3,  'ACTIVE', 6000, '2023-09-03 12:00:00', '2023-09-03 12:00:00'),
    (11, 1, 6,  'ACTIVE', 5000, '2023-09-03 12:00:00', '2023-09-03 12:00:00');

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