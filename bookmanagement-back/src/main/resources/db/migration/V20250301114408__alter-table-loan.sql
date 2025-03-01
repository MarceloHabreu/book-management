-- Remove constraint existent
ALTER TABLE loan DROP CONSTRAINT fk_book;

-- Add a constraint with deletion in cascade
ALTER TABLE loan ADD CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES book (id) ON DELETE CASCADE;
