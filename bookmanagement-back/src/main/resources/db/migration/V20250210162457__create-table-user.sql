CREATE TABLE user_account (
    id BIGSERIAL NOT NULL PRIMARY KEY ,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(150) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
