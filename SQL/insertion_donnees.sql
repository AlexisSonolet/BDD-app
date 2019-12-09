-- Table des artistes
INSERT INTO artiste VALUES (0, 'Dupont', 'Jacques', TO_DATE('1997-10-24', 'YYYY-MM-DD'), 'La fête a la maison', '06-18-18-18-18');
INSERT INTO artiste VALUES (1, 'Jibougne', 'Leo', TO_DATE('1994-04-14', 'YYYY-MM-DD'), 'La fête a la maison', '06-28-18-18-18');
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
INSERT INTO specialite_artiste VALUES (14, 'Magie');

-- Table des numeros
INSERT INTO numero VALUES (0, 'La machine a sous', 'Magie', 'Ceci est un resume du numero', 10, 30, 8);
INSERT INTO numero VALUES (1, 'TOP 10 des elephants', 'Zoo', 'Ceci est un resume du numero', 0, 30, 3);
INSERT INTO numero VALUES (2, 'Une corde cest bien, deux cest mieux', 'Voltige', 'Ceci est un resume du numero', 2, 30, 5);
INSERT INTO numero VALUES (3, 'Jai un grand sourire', 'Clown', 'Ceci est un resume du numero', 1, 30, 8);
INSERT INTO numero VALUES (4, 'Imaginez la licorne', 'Zoo', 'Ceci est un resume du numero', 14, 30, 10);

-- Table des experts
INSERT INTO expert VALUES (7);
INSERT INTO expert VALUES (4);
INSERT INTO expert VALUES (11);
INSERT INTO expert VALUES (6);
INSERT INTO expert VALUES (3);
INSERT INTO expert VALUES (8);

-- Table des pseudos
INSERT INTO pseudo_artiste VALUES (14, 'Fornite');
INSERT INTO pseudo_artiste VALUES (8, 'Mistral');
INSERT INTO pseudo_artiste VALUES (9, 'Essence');
INSERT INTO pseudo_artiste VALUES (9, 'Chemise');
INSERT INTO pseudo_artiste VALUES (2, 'Langlaise');
