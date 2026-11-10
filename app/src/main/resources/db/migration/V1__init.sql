CREATE TABLE users
(
    id            UUID PRIMARY KEY,
    login         VARCHAR(100) NOT NULL,
    password_hash VARCHAR(60)  NOT NULL,
    created_at    TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_users_login UNIQUE (login)
);

CREATE TABLE accounts
(
    id         UUID PRIMARY KEY,
    user_id    UUID         NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    name       VARCHAR(100) NOT NULL,
    currency   VARCHAR(3)   NOT NULL DEFAULT 'RUB',
    created_at TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_accounts_user_id ON accounts (user_id);

CREATE TABLE categories
(
    id   UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    CONSTRAINT uq_categories_name UNIQUE (name)
);

CREATE TABLE transactions
(
    id          UUID PRIMARY KEY,
    account_id  UUID           NOT NULL REFERENCES accounts (id) ON DELETE CASCADE,
    category_id UUID           REFERENCES categories (id) ON DELETE SET NULL,
    amount      NUMERIC(19, 2) NOT NULL CHECK (amount > 0),
    currency    VARCHAR(3)     NOT NULL,
    type        VARCHAR(10)    NOT NULL CHECK (type IN ('INCOME', 'EXPENSE')),
    note        VARCHAR(500),
    occurred_at TIMESTAMPTZ    NOT NULL,
    created_at  TIMESTAMPTZ    NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_transactions_account_id ON transactions (account_id);
CREATE INDEX idx_transactions_category_id ON transactions (category_id);
CREATE INDEX idx_transactions_occurred_at ON transactions (occurred_at DESC);