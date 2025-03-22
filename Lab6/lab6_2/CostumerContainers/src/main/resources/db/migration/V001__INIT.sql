-- V001__INIT.sql

-- Criação da tabela de clientes
CREATE TABLE customer (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100)
);

INSERT INTO customer (name, email) VALUES ('John Doe', 'john@example.com');
INSERT INTO customer (name, email) VALUES ('Jane Doe', 'jane@example.com');
INSERT INTO customer (name, email) VALUES ('Alice Smith', 'alice@example.com');
