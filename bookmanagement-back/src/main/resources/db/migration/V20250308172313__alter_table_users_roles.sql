-- Remover a constraint existente, se houver
ALTER TABLE tb_users_roles DROP CONSTRAINT IF EXISTS tb_users_roles_role_id_fkey;
ALTER TABLE tb_users_roles DROP CONSTRAINT IF EXISTS fk_tb_users_roles_user;

-- Adicionar novamente a constraint com deleção em cascata
ALTER TABLE tb_users_roles
ADD CONSTRAINT fk_tb_users_roles_role FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE;
