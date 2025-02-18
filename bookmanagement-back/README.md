# Projeto de Gerenciamento de Livros

## Diagrama ERD INICIAL

```mermaid
erDiagram
    USER_ACCOUNT {
        BIGINT id PK "Primary Key"
        VARCHAR name "Nome do Usuário"
        VARCHAR email "Email do Usuário"
        TIMESTAMP created_at "Data de Criação"
    }
    BOOK {
        BIGINT id PK "Primary Key"
        VARCHAR title "Título do Livro"
        VARCHAR author "Autor do Livro"
        BOOLEAN is_borrowed "Status de Empréstimo"
    }
    LOAN {
        BIGINT id PK "Primary Key"
        BIGINT user_id FK "Foreign Key para USER_ACCOUNT"
        BIGINT book_id FK "Foreign Key para BOOK"
        TIMESTAMP loan_date "Data de Empréstimo"
        TIMESTAMP return_date "Data de Devolução"
    }
    USER_ACCOUNT ||--o{ LOAN : "empresta"
    BOOK ||--o{ LOAN : "é emprestado"
