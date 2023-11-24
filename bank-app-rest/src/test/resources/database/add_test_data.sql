INSERT INTO users (role, status, password, first_name, last_name, email, address, phone, created_at, updated_at)
VALUES
    ('CLIENT', 'ACTIVE', '$2a$04$pqlgizIi1uE4Tb9BxRT3D.qonNfBIXxCDiq.r2tL5vqQKNGQ25IE6', 'Isabella', 'White', 'isabella.white@yopmail.com', '789 Elm St', '777-8888', '2023-09-01 13:57:40', '2023-09-01 13:57:40'),
    ('CLIENT', 'ACTIVE', '$2a$04$pqlgizIi1uE4Tb9BxRT3D.qonNfBIXxCDiq.r2tL5vqQKNGQ25IE6', 'James', 'Harris', 'james.harris@yopmail.com', '987 Main St', '333-1111', '2023-08-01 13:57:40', '2023-08-01 13:57:40'),
    ('MANAGER', 'ACTIVE', '$2a$04$pqlgizIi1uE4Tb9BxRT3D.qonNfBIXxCDiq.r2tL5vqQKNGQ25IE6', 'Mia', 'Clark', 'mia.clark@yopmail.com', '654 Elm St', '999-0000', '2023-08-02 13:57:40', '2023-09-01 13:57:40');

INSERT INTO products (name, type, status, interest_rate, min_limit, period_months, created_at, updated_at)
VALUES
    ('Auto Loan' ,'LOAN','ACTIVE', 4.5, 60000, 60, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('Mortgage Loan' ,'LOAN','ACTIVE', 3.2, 250000, 240, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('Travel Loan', 'LOAN', 'ACTIVE', 8.2, 8000, 12, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('Pension Savings Deposit', 'DEPOSIT', 'ACTIVE', 3.8, 30000, 120, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('Children''s Savings Deposit', 'DEPOSIT', 'ACTIVE', 4.5, 5000, 60, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('VIP Deposit', 'DEPOSIT', 'ACTIVE', 4.8, 100000, 24, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('Credit card', 'CREDIT_CARD', 'ACTIVE', 18, 10000, 60, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('Debit card', 'DEBIT_CARD', 'ACTIVE', 0, 0, 60, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('Personal Loan' ,'LOAN','BLOCKED', 7, 15000, 36, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('Educational Loan', 'LOAN','BLOCKED', 5.8, 20000, 72, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('Small Business Loan', 'LOAN', 'BLOCKED', 6.5, 100000, 84, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('Home Improvement Loan', 'LOAN', 'BLOCKED', 4.8, 10000, 24, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('Equipment Purchase Loan', 'LOAN', 'BLOCKED', 5, 30000, 48, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('Student Loan', 'LOAN', 'BLOCKED', 3.5, 12000, 60, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('Term Deposit', 'DEPOSIT', 'BLOCKED', 3.5, 10000, 12, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('Long-Term Deposit', 'DEPOSIT', 'BLOCKED', 4.0, 20000, 24, '2023-09-03 13:00:00', '2023-09-03 13:00:00'),
    ('Monthly Interest Payment Deposit', 'DEPOSIT', 'BLOCKED', 3.0, 25000, 36, '2023-09-03 13:00:00', '2023-09-03 13:00:00');

INSERT INTO accounts (client_id, number, status, balance, currency_code, created_at, updated_at)
VALUES
    (null, '1111111111111111', 'ACTIVE', 9999999999999.99, 'PLN', '2023-09-01 00:00:00', '2023-09-01 00:00:00'),
    (1, '1234567890123456', 'ACTIVE', 15000, 'USD', '2023-09-04 13:57:40', '2023-09-04 13:57:40'),
    (1, '6123456789012345', 'ACTIVE', 2000, 'USD', '2023-09-04 13:57:40', '2023-09-04 13:57:40'),
    (2, '4561234567890123', 'NEW', 0, 'EUR', '2023-09-04 13:57:40', '2023-09-04 13:57:40'),
    (2, '5612345678901234', 'NEW', 0, 'EUR', '2023-09-03 13:57:40', '2023-09-03 13:57:40');

INSERT INTO agreements (account_id, product_id, manager_id, status, sum, start_date, created_at, updated_at)
VALUES
    (2, 4, 3, 'ACTIVE', 10000, '2023-09-03', '2023-09-03 12:00:00', '2023-09-03 12:00:00'),
    (3, 7, 3,'ACTIVE', 90000, '2023-09-03', '2023-09-03 12:00:00', '2023-09-03 12:00:00'),
    (4, 1, null, 'NEW', 11000, null, '2023-09-03 12:00:00', '2023-09-03 12:00:00'),
    (5, 8, null, 'NEW', 11000, null, '2023-09-03 12:00:00', '2023-09-03 12:00:00');

INSERT INTO transactions (debit_account_id, credit_account_id, type, amount, currency_code, debit_balance_difference, credit_balance_difference, description, created_at)
VALUES
    (2, 3, 'CASH', 1037.58, 'USD', 1037, 1037, 'for ice cream', '2023-09-03 12:00:00'),
    (3, 4, 'CASH', 845.67, 'USD', 845, 845, 'for ice cream', '2023-09-03 12:00:00'),
    (4, 5, 'CASH', 541.63, 'USD', 541, 541, 'for ice cream', '2023-09-03 12:00:00');