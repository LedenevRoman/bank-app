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
