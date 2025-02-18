CREATE TABLE loan (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    user_id BIGINT  NOT NULL,
    book_id BIGINT NOT NULL,
    loan_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    return_date TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES user_account (id),
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES book (id)
);