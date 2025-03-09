-- Remover a constraint existente, se houver
ALTER TABLE tb_users_roles DROP CONSTRAINT IF EXISTS tb_users_roles_user_id_fkey;

-- Adicionar a foreign key com opção de deleção em cascata
ALTER TABLE tb_users_roles
ADD CONSTRAINT fk_tb_users_roles_user FOREIGN KEY (id) REFERENCES user_account(id) ON DELETE CASCADE;
