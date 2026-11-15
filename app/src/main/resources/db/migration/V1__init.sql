-- users
CREATE TABLE users
(
    id            UUID PRIMARY KEY,
    login         VARCHAR(100) NOT NULL,
    password_hash VARCHAR(60)  NOT NULL,
    created_at    TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);
CREATE UNIQUE INDEX uq_users_login_lower ON users (LOWER(login));

-- accounts
CREATE TABLE accounts
(
    id         UUID PRIMARY KEY,
    user_id    UUID           NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    name       VARCHAR(100)   NOT NULL,
    currency   VARCHAR(3)     NOT NULL DEFAULT 'RUB' CHECK (currency ~ '^[A-Z]{3}$'),
    balance    NUMERIC(19, 4) NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ    NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_accounts_user_id ON accounts (user_id);

-- categories: user_id NULL = системная, NOT NULL = пользовательская
CREATE TABLE categories
(
    id      UUID         NOT NULL PRIMARY KEY,
    user_id UUID REFERENCES users (id) ON DELETE CASCADE,
    name    VARCHAR(100) NOT NULL,
    type    VARCHAR(20)  NOT NULL CHECK (type IN ('INCOME', 'EXPENSE'))
);

-- NULLS NOT DISTINCT: (NULL, 'food') = (NULL, 'Food') — конфликт
--                      (userA, 'food') ≠ (NULL, 'Food')  — разные
CREATE UNIQUE INDEX uq_categories_user_name_lower
    ON categories (user_id, LOWER(name)) NULLS NOT DISTINCT;

CREATE INDEX idx_categories_user_id ON categories (user_id);

-- системные категории (user_id = NULL)
INSERT INTO categories (id, user_id, name, type)
VALUES (gen_random_uuid(), NULL, 'Salary', 'INCOME'),
       (gen_random_uuid(), NULL, 'Freelance', 'INCOME'),
       (gen_random_uuid(), NULL, 'Investments', 'INCOME'),
       (gen_random_uuid(), NULL, 'Gifts', 'INCOME'),
       (gen_random_uuid(), NULL, 'Other Income', 'INCOME'),
       (gen_random_uuid(), NULL, 'Food', 'EXPENSE'),
       (gen_random_uuid(), NULL, 'Transport', 'EXPENSE'),
       (gen_random_uuid(), NULL, 'Housing', 'EXPENSE'),
       (gen_random_uuid(), NULL, 'Utilities', 'EXPENSE'),
       (gen_random_uuid(), NULL, 'Healthcare', 'EXPENSE'),
       (gen_random_uuid(), NULL, 'Entertainment', 'EXPENSE'),
       (gen_random_uuid(), NULL, 'Shopping', 'EXPENSE'),
       (gen_random_uuid(), NULL, 'Education', 'EXPENSE'),
       (gen_random_uuid(), NULL, 'Travel', 'EXPENSE'),
       (gen_random_uuid(), NULL, 'Other Expense', 'EXPENSE')
ON CONFLICT DO NOTHING;

-- transactions
CREATE TABLE transactions
(
    id          UUID           NOT NULL PRIMARY KEY,
    account_id  UUID           NOT NULL REFERENCES accounts (id) ON DELETE CASCADE,
    category_id UUID           NOT NULL REFERENCES categories (id) ON DELETE RESTRICT,
    amount      NUMERIC(19, 4) NOT NULL,
    description VARCHAR(500),
    occurred_at TIMESTAMPTZ    NOT NULL,
    created_at  TIMESTAMPTZ    NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_transactions_account_occurred
    ON transactions (account_id, occurred_at DESC);
CREATE INDEX idx_transactions_category_id ON transactions (category_id);