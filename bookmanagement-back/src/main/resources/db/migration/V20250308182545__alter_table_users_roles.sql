-- Remover todas as constraints existentes
ALTER TABLE tb_users_roles DROP CONSTRAINT IF EXISTS fk_tb_users_roles_user;
ALTER TABLE tb_users_roles DROP CONSTRAINT IF EXISTS fk_tb_users_roles_role;

-- Adicionar constraint para id com deleção em cascata
ALTER TABLE tb_users_roles
ADD CONSTRAINT fk_tb_users_roles_user FOREIGN KEY (id) REFERENCES user_account(id) ON DELETE CASCADE;

-- Adicionar constraint para role_id SEM deleção em cascata
ALTER TABLE tb_users_roles
ADD CONSTRAINT fk_tb_users_roles_role FOREIGN KEY (role_id) REFERENCES roles(role_id);