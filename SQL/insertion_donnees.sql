BEGIN;

-- Table des artistes
INSERT INTO artiste VALUES (0, 'Dupont', 'Jacques', TO_DATE('1997-10-24', 'YYYY-MM-DD'), 'La fete a la maison', '06-18-18-18-18');
INSERT INTO artiste VALUES (1, 'Jibougne', 'Leo', TO_DATE('1994-04-14', 'YYYY-MM-DD'), 'La fete a la maison', '06-28-18-18-18');
INSERT INTO artiste VALUES (2, 'Latisane', 'Nicolette', TO_DATE('1947-10-24', 'YYYY-MM-DD'), 'Ici cest parti', '06-18-28-18-18');
INSERT INTO artiste VALUES (3, 'Natoo', 'Hugues', TO_DATE('1992-10-24', 'YYYY-MM-DD'), 'Ici cest parti', '07-38-18-18-18');
INSERT INTO artiste VALUES (4, 'Haroun', 'Lea', TO_DATE('1897-10-24', 'YYYY-MM-DD'), 'Ici cest parti', '07-18-18-18-18');
INSERT INTO artiste VALUES (5, 'Latite', 'Teillere', TO_DATE('2007-10-24', 'YYYY-MM-DD'), 'Les fous furieux', '08-18-18-18-18');
INSERT INTO artiste VALUES (6, 'Tiboulette', 'Celine', TO_DATE('1997-10-24', 'YYYY-MM-DD'), 'Les fous furieux', '05-18-18-18-18');
INSERT INTO artiste VALUES (7, 'Celdo', 'Renand', TO_DATE('1992-11-20', 'YYYY-MM-DD'), 'Le cirketanou', '04-18-18-18-18');
INSERT INTO artiste VALUES (8, 'Luce', 'Renaud', TO_DATE('1997-01-01', 'YYYY-MM-DD'), 'Le cirketanou', '04-18-18-18-18');
INSERT INTO artiste VALUES (9, 'Tryo', 'Ordinaire', TO_DATE('1990-10-24', 'YYYY-MM-DD'), 'Le cirketanou', '04-18-18-18-18');
INSERT INTO artiste VALUES (10, 'Namene', 'Lolita', TO_DATE('1980-10-24', 'YYYY-MM-DD'), 'Les magiciens du spectacle', '07-10-08-18-18');
INSERT INTO artiste VALUES (11, 'Linote', 'Kiki', TO_DATE('1990-10-10', 'YYYY-MM-DD'), 'Les magiciens du spectacle', '06-18-11-08-18');
INSERT INTO artiste VALUES (12, 'Hanounah', 'Cyril', TO_DATE('1991-02-25', 'YYYY-MM-DD'), 'Le grand plateau', '03-18-18-18-18');
INSERT INTO artiste VALUES (13, 'Lustucru', 'Lapate', TO_DATE('1991-02-25', 'YYYY-MM-DD'), 'Le grand plateau', '03-18-18-18-18');
INSERT INTO artiste VALUES (14, 'Pinata', 'Lalicorne', TO_DATE('1991-02-25', 'YYYY-MM-DD'), 'Le grand plateau', '03-18-18-18-18');
INSERT INTO artiste VALUES (15, 'Bonobo', 'Kong', TO_DATE('1901-05-30', 'YYYY-MM-DD'), 'Moderat', '04-28-38-18-18');
INSERT INTO artiste VALUES (16, '1995', 'Smog', TO_DATE('1971-12-24', 'YYYY-MM-DD'), 'Moderat', '03-18-58-98-18');
INSERT INTO artiste VALUES (17, 'Tastycool', 'Amsterdam', TO_DATE('1991-02-25', 'YYYY-MM-DD'), 'Im on it', '05-28-88-99-18');
INSERT INTO artiste VALUES (18, 'Prince', 'Karma', TO_DATE('2001-02-25', 'YYYY-MM-DD'), 'Im on it', '07-28-88-99-18');
INSERT INTO artiste VALUES (19, 'Indochine', 'Lalune', TO_DATE('1991-03-22', 'YYYY-MM-DD'), 'Im on it', '01-22-88-99-18');
INSERT INTO artiste VALUES (20, 'Skippy', 'Creamy', TO_DATE('1991-02-25', 'YYYY-MM-DD'), 'Im on it', '07-18-28-99-18');
INSERT INTO artiste VALUES (21, 'D.A.N.C.E.', 'Juliette', TO_DATE('1990-12-25', 'YYYY-MM-DD'), 'Im on it', '07-18-28-99-18');
INSERT INTO artiste VALUES (22, 'Tasted', 'Maxence', TO_DATE('1991-02-25', 'YYYY-MM-DD'), 'Piano Delta Piano', '08-28-28-99-18');
INSERT INTO artiste VALUES (23, 'Cyrin', 'Novo', TO_DATE('1991-02-25', 'YYYY-MM-DD'), 'Piano Delta Piano', '05-18-28-99-18');

-- Table des specialites
INSERT INTO specialite_artiste VALUES (0, 'Magie');
INSERT INTO specialite_artiste VALUES (0, 'Voltige');
INSERT INTO specialite_artiste VALUES (1, 'Clown');
INSERT INTO specialite_artiste VALUES (2, 'Voltige');
INSERT INTO specialite_artiste VALUES (3, 'Zoo');
INSERT INTO specialite_artiste VALUES (4, 'Clown');
INSERT INTO specialite_artiste VALUES (5, 'Voltige');
INSERT INTO specialite_artiste VALUES (6, 'Magie');
INSERT INTO specialite_artiste VALUES (7, 'Zoo');
INSERT INTO specialite_artiste VALUES (8, 'Clown');
INSERT INTO specialite_artiste VALUES (8, 'Magie');
INSERT INTO specialite_artiste VALUES (9, 'Zoo');
INSERT INTO specialite_artiste VALUES (10, 'Zoo');
INSERT INTO specialite_artiste VALUES (11, 'Voltige');
INSERT INTO specialite_artiste VALUES (12, 'Zoo');
INSERT INTO specialite_artiste VALUES (13, 'Clown');
INSERT INTO specialite_artiste VALUES (14, 'Zoo');
INSERT INTO specialite_artiste VALUES (15, 'Zoo');
INSERT INTO specialite_artiste VALUES (15, 'Magie');
INSERT INTO specialite_artiste VALUES (16, 'Voltige');
INSERT INTO specialite_artiste VALUES (17, 'Voltige');
INSERT INTO specialite_artiste VALUES (17, 'Clown');
INSERT INTO specialite_artiste VALUES (18, 'Voltige');
INSERT INTO specialite_artiste VALUES (19, 'Zoo');
INSERT INTO specialite_artiste VALUES (20, 'Zoo');
INSERT INTO specialite_artiste VALUES (21, 'Voltige');
INSERT INTO specialite_artiste VALUES (21, 'Magie');
INSERT INTO specialite_artiste VALUES (22, 'Clown');
INSERT INTO specialite_artiste VALUES (22, 'Voltige');
INSERT INTO specialite_artiste VALUES (23, 'Zoo');
INSERT INTO specialite_artiste VALUES (23, 'Clown');

-- Table des numeros
INSERT INTO numero VALUES (0, 'La machine a sous', 'Magie', 'Ceci est un resume du numero', 10, 30, 1, 5, 'Ceci est une evaluation');
INSERT INTO numero VALUES (1, 'TOP 10 des elephants', 'Zoo', 'Ceci est un resume du numero', 0, 30, 3, 3, 'Ceci est une evaluation');
INSERT INTO numero VALUES (2, 'Une corde cest bien, deux cest mieux', 'Voltige', 'Ceci est un resume du numero', 2, 30, 3, 7, 'Ceci est une evaluation');
INSERT INTO numero VALUES (3, 'Jai un grand sourire', 'Clown', 'Ceci est un resume du numero', 1, 30, 1, 8, 'Ceci est une evaluation');
INSERT INTO numero VALUES (4, 'Imaginez la licorne', 'Zoo', 'Ceci est un resume du numero', 14, 10, 5, 3, 'We want your soul');
INSERT INTO numero VALUES (5, 'Quelle vie', 'Clown', 'Ceci est un resume du numero', 17, 20, 3, 7, 'Ceci est une evaluation');

-- Planning des artistes
INSERT INTO planning_artiste VALUES (10, 0);
INSERT INTO planning_artiste VALUES (0, 1);
INSERT INTO planning_artiste VALUES (2, 2);
INSERT INTO planning_artiste VALUES (1, 3);
INSERT INTO planning_artiste VALUES (14, 4);
INSERT INTO planning_artiste VALUES (17, 5);

-- Table des experts
INSERT INTO expert VALUES (7);
INSERT INTO expert VALUES (4);
INSERT INTO expert VALUES (11);
INSERT INTO expert VALUES (6);
INSERT INTO expert VALUES (3);
INSERT INTO expert VALUES (8);
INSERT INTO expert VALUES (21);
INSERT INTO expert VALUES (22);
INSERT INTO expert VALUES (17);
INSERT INTO expert VALUES (15);

-- Table des pseudos
INSERT INTO pseudo_artiste VALUES (14, 'Fornite');
INSERT INTO pseudo_artiste VALUES (8, 'Mistral');
INSERT INTO pseudo_artiste VALUES (9, 'Essence');
INSERT INTO pseudo_artiste VALUES (9, 'Chemise');
INSERT INTO pseudo_artiste VALUES (2, 'Langlaise');

-- Table des spectacles
INSERT INTO spectacle VALUES (TO_DATE('1111-11-11', 'YYYY-MM-DD'), 9, 'Zoo', 1, 7);

-- Planning numero
INSERT INTO planning_numero VALUES(TO_DATE('1111-11-11', 'YYYY-MM-DD'), 9, 4);


COMMIT; -- On sauvegarde
