ALTER TABLE oblik_user ADD COLUMN percentage float8;
UPDATE oblik_user SET percentage = 0.5;
ALTER TABLE oblik_user ALTER COLUMN percentage SET NOT NULL;