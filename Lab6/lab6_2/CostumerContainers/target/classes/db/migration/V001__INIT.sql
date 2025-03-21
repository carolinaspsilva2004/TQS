-- V001__INIT.sql

-- Criação da tabela de clientes
CREATE TABLE IF NOT EXISTS customer (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

-- Inserção de alguns registros de exemplo
INSERT INTO customer (name, email) VALUES ('John Doe', 'john.doe@example.com');
INSERT INTO customer (name, email) VALUES ('Jane Smith', 'jane.smith@example.com');
INSERT INTO customer (name, email) VALUES ('Robert Brown', 'robert.brown@example.com');
