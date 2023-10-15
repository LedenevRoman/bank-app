INSERT INTO users (role, status, password, first_name, last_name, email, address, phone, created_at, updated_at)
VALUES
    ('CLIENT', 'ACTIVE', '$2a$04$pqlgizIi1uE4Tb9BxRT3D.qonNfBIXxCDiq.r2tL5vqQKNGQ25IE6', 'Isabella', 'White', 'isabella.white@yopmail.com', '789 Elm St', '777-8888', '2023-09-01 13:57:40', '2023-09-01 13:57:40'),
    ('CLIENT', 'ACTIVE', '$2a$04$pqlgizIi1uE4Tb9BxRT3D.qonNfBIXxCDiq.r2tL5vqQKNGQ25IE6', 'James', 'Harris', 'james.harris@yopmail.com', '987 Main St', '333-1111', '2023-08-01 13:57:40', '2023-08-01 13:57:40'),
    ('MANAGER', 'ACTIVE', '$2a$04$pqlgizIi1uE4Tb9BxRT3D.qonNfBIXxCDiq.r2tL5vqQKNGQ25IE6', 'Mia', 'Clark', 'mia.clark@yopmail.com', '654 Elm St', '999-0000', '2023-08-02 13:57:40', '2023-09-01 13:57:40'),
    ('CLIENT', 'ACTIVE', '$2a$04$pqlgizIi1uE4Tb9BxRT3D.qonNfBIXxCDiq.r2tL5vqQKNGQ25IE6', 'Joseph', 'Lewis', 'joseph.lewis@yopmail.com', '123 Main St', '246-1357', '2023-08-09 13:57:40', '2023-09-01 13:57:40'),
    ('CLIENT', 'ACTIVE', '$2a$04$pqlgizIi1uE4Tb9BxRT3D.qonNfBIXxCDiq.r2tL5vqQKNGQ25IE6', 'Charlotte', 'Lee', 'charlotte.lee@yopmail.com', '321 Pine St', '864-2093', '2023-08-07 13:57:40', '2023-09-01 13:57:40'),
    ('MANAGER', 'ACTIVE', '$2a$04$pqlgizIi1uE4Tb9BxRT3D.qonNfBIXxCDiq.r2tL5vqQKNGQ25IE6', 'David', 'Walker', 'david.walker@yopmail.com', '987 Maple St', '502-8174', '2023-08-06 13:57:40', '2023-09-01 13:57:40'),
    ('CLIENT', 'ACTIVE', '$2a$04$pqlgizIi1uE4Tb9BxRT3D.qonNfBIXxCDiq.r2tL5vqQKNGQ25IE6', 'Abigail', 'Hall', 'abigail.hall@yopmail.com', '654 Oak St', '619-3847', '2023-08-05 13:57:40', '2023-08-01 13:57:40'),
    ('CLIENT', 'ACTIVE', '$2a$04$pqlgizIi1uE4Tb9BxRT3D.qonNfBIXxCDiq.r2tL5vqQKNGQ25IE6', 'Christopher', 'Wright', 'christopher.wright@yopmail.com', '789 Elm St', '205-6789', '2023-08-04 13:57:40', '2023-09-01 13:57:40'),
    ('CLIENT', 'ACTIVE', '$2a$04$pqlgizIi1uE4Tb9BxRT3D.qonNfBIXxCDiq.r2tL5vqQKNGQ25IE6', 'Harper', 'Young', 'harper.young@yopmail.com', '321 Oak St', '714-2938', '2023-08-03 13:57:40', '2023-09-01 13:57:40'),
    ('MANAGER', 'ACTIVE', '$2a$04$pqlgizIi1uE4Tb9BxRT3D.qonNfBIXxCDiq.r2tL5vqQKNGQ25IE6', 'Andrew', 'Allen', 'andrew.allen@yopmail.com', '456 Maple St', '408-5726', '2023-08-10 13:57:40', '2023-08-10 13:57:40');

INSERT INTO products (name, type, status, interest_rate, min_limit, period_months, created_at, updated_at)
VALUES
    ('Auto Loan' ,'LOAN','ACTIVE', 4.5, 60000, 60, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('Mortgage Loan' ,'LOAN','ACTIVE', 3.2, 250000, 240, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('Personal Loan' ,'LOAN','ACTIVE', 7, 15000, 36, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('Educational Loan', 'LOAN','ACTIVE', 5.8, 20000, 72, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('Small Business Loan', 'LOAN', 'ACTIVE', 6.5, 100000, 84, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('Home Improvement Loan', 'LOAN', 'ACTIVE', 4.8, 10000, 24, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('Travel Loan', 'LOAN', 'ACTIVE', 8.2, 8000, 12, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('Equipment Purchase Loan', 'LOAN', 'ACTIVE', 5, 30000, 48, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('Student Loan', 'LOAN', 'ACTIVE', 3.5, 12000, 60, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('Term Deposit', 'DEPOSIT', 'ACTIVE', 3.5, 10000, 12, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('Long-Term Deposit', 'DEPOSIT', 'ACTIVE', 4.0, 20000, 24, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('Pension Savings Deposit', 'DEPOSIT', 'ACTIVE', 3.8, 30000, 120, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('Children''s Savings Deposit', 'DEPOSIT', 'ACTIVE', 4.5, 5000, 60, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('Monthly Interest Payment Deposit', 'DEPOSIT', 'ACTIVE', 3.0, 25000, 36, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('VIP Deposit', 'DEPOSIT', 'ACTIVE', 4.8, 100000, 24, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('Credit card', 'CREDIT_CARD', 'ACTIVE', 18, 10000, 60, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('Debit card', 'DEBIT_CARD', 'ACTIVE', 0, 0, 60, '2023-09-03 13:00:00', '2023-09-03 13:00:00');

INSERT INTO accounts (client_id, number, status, balance, currency_code, created_at, updated_at)
VALUES
    (null, '1111111111111111', 'ACTIVE', 9999999999999.99, 'PLN', '2023-09-01 00:00:00', '2023-09-01 00:00:00'),
    (1, '1234567890123456', 'ACTIVE', 10321.41, 'USD', '2023-09-04 13:57:40', '2023-09-04 13:57:40'),
    (1, '6123456789012345', 'ACTIVE', 5031.75, 'USD', '2023-09-04 13:57:40', '2023-09-04 13:57:40'),
    (2, '5612345678901234', 'ACTIVE', 8121.42, 'EUR', '2023-09-03 13:57:40', '2023-09-03 13:57:40'),
    (3, '4561234567890123', 'ACTIVE', 3002.11, 'USD', '2023-09-03 13:57:40', '2023-09-03 13:57:40'),
    (4, '3456123456789012', 'ACTIVE', 2453.24, 'EUR', '2023-09-03 13:57:40', '2023-09-03 13:57:40'),
    (5, '2345612345678901', 'ACTIVE', 7610.31, 'USD', '2023-09-03 13:57:40', '2023-09-03 13:57:40'),
    (6, '1234561234567890', 'ACTIVE', 9543.17, 'EUR', '2023-09-04 13:57:40', '2023-09-04 13:57:40'),
    (7, '0123456123456789', 'ACTIVE', 4214.86, 'EUR', '2023-09-04 13:57:40', '2023-09-04 13:57:40'),
    (8, '9012345612345678', 'ACTIVE', 6520.96, 'USD', '2023-09-04 13:57:40', '2023-09-04 13:57:40'),
    (9, '8901234561234567', 'ACTIVE', 7120.88, 'EUR', '2023-09-03 13:57:40', '2023-09-03 13:57:40'),
    (10, '7890123456123456', 'ACTIVE', 3512.73, 'USD', '2023-09-03 13:57:40', '2023-09-03 13:57:40');

INSERT INTO agreements (account_id, product_id, manager_id, status, sum, start_date, created_at, updated_at)
VALUES
    (2, 2, 3, 'ACTIVE', 10000, '2023-09-03', '2023-09-03 12:00:00', '2023-09-03 12:00:00'),
    (3, 1, 6,'ACTIVE', 90000, '2023-09-03', '2023-09-03 12:00:00', '2023-09-03 12:00:00'),
    (4, 3, 10, 'ACTIVE', 11000, '2023-09-03', '2023-09-03 12:00:00', '2023-09-03 12:00:00'),
    (5, 2, 3, 'ACTIVE', 15000, '2023-09-03', '2023-09-03 12:00:00', '2023-09-03 12:00:00'),
    (6, 1, 6, 'ACTIVE', 8000, '2023-09-03', '2023-09-03 12:00:00', '2023-09-03 12:00:00'),
    (7, 3, 10, 'ACTIVE', 13000, '2023-09-03', '2023-09-03 12:00:00', '2023-09-03 12:00:00'),
    (8, 2, 3, 'ACTIVE', 12000, '2023-09-03', '2023-09-03 12:00:00', '2023-09-03 12:00:00'),
    (9, 1, 6,  'ACTIVE', 7000, '2023-09-03', '2023-09-03 12:00:00', '2023-09-03 12:00:00'),
    (10, 3, 10,  'ACTIVE', 14000, '2023-09-03', '2023-09-03 12:00:00', '2023-09-03 12:00:00'),
    (11, 1, 6,  'ACTIVE', 5000, '2023-09-03', '2023-09-03 12:00:00', '2023-09-03 12:00:00');

INSERT INTO transactions (debit_account_id, credit_account_id, type, amount, currency_code, debit_balance_difference, credit_balance_difference, description, created_at)
VALUES
    (1, 2, 'CASH', 1037.58, 'USD', 1037, 1037, 'for ice cream', '2023-09-03 12:00:00'),
    (2, 3, 'CASH', 845.67, 'USD', 845, 845, 'for ice cream', '2023-09-03 12:00:00'),
    (3, 4, 'CASH', 541.63, 'USD', 541, 541, 'for ice cream', '2023-09-03 12:00:00'),
    (4, 5, 'CASH', 375.64, 'USD', 375, 375, 'for ice cream', '2023-09-03 12:00:00'),
    (5, 6, 'CASH', 258.81, 'USD', 258, 258, 'for ice cream', '2023-09-03 12:00:00'),
    (6, 7, 'CASH', 761.45, 'USD', 761, 761, 'for ice cream', '2023-09-03 12:00:00'),
    (7, 8, 'CASH', 135.77, 'USD', 135, 135, 'for ice cream', '2023-09-03 12:00:00'),
    (8, 9, 'CASH', 428.66, 'USD', 428, 428, 'for ice cream', '2023-09-03 12:00:00'),
    (9, 10, 'CASH', 744.63, 'USD', 744, 744, 'for ice cream', '2023-09-03 12:00:00'),
    (10, 11, 'CASH', 269.78, 'USD', 269, 269, 'for ice cream', '2023-09-03 12:00:00'),
    (11, 1, 'CASH', 899.99, 'USD', 899, 899, 'for ice cream', '2023-09-03 12:00:00');