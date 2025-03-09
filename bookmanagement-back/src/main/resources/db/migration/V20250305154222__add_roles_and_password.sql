-- Adicionar campo password à tabela user_account
ALTER TABLE user_account
    ADD COLUMN password VARCHAR(255);

-- Remover campo role (se existia antes)
ALTER TABLE user_account
    DROP COLUMN IF EXISTS role;

-- Criar tabela roles
CREATE TABLE roles (
    role_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

-- Criar tabela de junção tb_users_roles
CREATE TABLE tb_users_roles (
    id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (id, role_id),
    FOREIGN KEY (id) REFERENCES user_account(id),
    FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

-- Inserir roles
INSERT INTO roles (role_id, name) VALUES
    (1, 'admin'),
    (2, 'basic');