-- 11. Hollywood is remaking the classic movie "The Blob" and doesn't have
-- a director yet. Add yourself to the person table, and assign yourself as
-- the director of "The Blob" (the movie is already in the movie table).
-- (1 record each)

INSERT into person
(person_name)
VALUES
('Ersin Deger');

UPDATE movie
SET director_id = (SELECT person_id FROM person where person_name = 'Ersin Deger')
WHERE title = 'The Blob';
