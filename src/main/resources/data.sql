INSERT INTO book(id, code, code_number, format, title, authors, edition, rating, copies, marc) VALUES (1, 'ISBN', '9788420471839', 'Book', 'Cien años de soledad', '{"name":"Gabriel García Márquez"}', 2017, 5, 2, '');
INSERT INTO book(id, code, code_number, format, title, authors, edition, rating, copies, marc) VALUES (2, 'ISBN', '9788467036589', 'Book', 'La República', '{"name":"Platón"}', 2011, 4, 1, '');
INSERT INTO book(id, code, code_number, format, title, authors, edition, rating, copies, marc) VALUES (3, 'ISBN', '9789589016824', 'Book', 'El Maestro y Margarita', '{"name":"Mijail Bulgakov"}', 2003, 4, 3, '');

INSERT INTO users(nid, type_nid, user_alias, email, name, last_name, active) VALUES (1, 'CC', 'ElLector', 'ellector@correo.co', 'Jhon', 'Doe', 1);
INSERT INTO users(nid, type_nid, user_alias, email, name, last_name, active) VALUES (2, 'CC', 'Anonymous', 'usuario@correo.co', 'Cosme', 'Fulanito', 1);
INSERT INTO users(nid, type_nid, user_alias, email, name, last_name, active) VALUES (3, 'CE', 'Diogenes', 'dio@correo.co', 'Diogenes', 'Sinope', 0);

INSERT INTO book_review(id, book_id, user_nid, comment, review) VALUES (1, 1, 1, 'Todos tienen cola de marrano', 5);
INSERT INTO book_review(id, book_id, user_nid, comment, review) VALUES (2, 1, 2, 'Muy buen libro', 5);
INSERT INTO book_review(id, book_id, user_nid, comment, review) VALUES (3, 1, 3, 'Extraño, pero recomendado', 4);
INSERT INTO book_review(id, book_id, user_nid, comment, review) VALUES (4, 2, 1, 'Interesante literatura', 4);
INSERT INTO book_review(id, book_id, user_nid, comment, review) VALUES (5, 2, 2, 'Sin comentarios', 4);
INSERT INTO book_review(id, book_id, user_nid, comment, review) VALUES (6, 2, 3, '¡Te traje una gallina!', 2);
INSERT INTO book_review(id, book_id, user_nid, comment, review) VALUES (7, 3, 1, 'Excelente obra', 5);