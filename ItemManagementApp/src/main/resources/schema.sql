DROP TABLE IF EXISTS accounts_items;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS accounts;

CREATE TABLE items (
    item_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    quantity INT DEFAULT 0
);

CREATE TABLE accounts (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    type VARCHAR(100) DEFAULT 'user' NOT NULL
);

CREATE TABLE accounts_items (
    "user_id" INT REFERENCES accounts(user_id) NOT NULL,
    "item_id" INT REFERENCES items(item_id) NOT NULL,
    UNIQUE ("user_id", "item_id")

);