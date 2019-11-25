--CREATE DATABASE IF NOT EXISTS festival;
--USE festival;

DROP TABLE planning_artiste;
DROP TABLE planning_numero;
DROP TABLE specialite_artiste;
DROP TABLE pseudo_artiste;
DROP TABLE evaluation;
DROP TABLE spectacle;
DROP TABLE numero;
DROP TABLE artiste;

CREATE TABLE artiste
(
    idArtiste INT PRIMARY KEY NOT NULL,
    nomArtiste VARCHAR(100),
    prenomArtiste VARCHAR(100),
    dateNaissanceArtiste DATE,
    cirqueArtiste VARCHAR(100),
    telephoneArtiste VARCHAR(16)
);

CREATE TABLE numero
(
    idNumero INT PRIMARY KEY NOT NULL,
    themeNumero VARCHAR(100),
    resumeNumero VARCHAR(1000),
    artistePrincipalNumero REFERENCES artiste(idArtiste),
    dureeNumero INT CHECK(dureeNumero >= 0),
    noteNumero INT CHECK(noteNumero BETWEEN 0 and 10),
    evaluationNumero VARCHAR(1000)
);

CREATE TABLE spectacle
(
    dateSpectacle DATE NOT NULL,
    heureSpectacle INT NOT NULL CHECK (heureSpectacle in (9, 14)),
    themeSpectacle VARCHAR(100),
    presentateurSpectacle NOT NULL REFERENCES artiste(idArtiste),
    prixSpectacle INT NOT NULL CHECK (prixSpectacle >= 0),
    PRIMARY KEY (dateSpectacle, heureSpectacle)
);

CREATE TABLE planning_artiste
(
    idArtiste INT NOT NULL REFERENCES artiste(idArtiste),
    idNumero INT NOT NULL REFERENCES numero(idNumero),
    PRIMARY KEY (idNumero, idArtiste)
);

CREATE TABLE planning_numero
(
    dateSpectacle DATE NOT NULL,
    heureSpectacle INT NOT NULL,
    idNumero INT NOT NULL REFERENCES numero(idNumero),
    FOREIGN KEY (dateSpectacle, heureSpectacle) REFERENCES spectacle(dateSpectacle, heureSpectacle),
    PRIMARY KEY (dateSpectacle, heureSpectacle, idNumero)
);

CREATE TABLE specialite_artiste
(
    idArtiste INT PRIMARY KEY NOT NULL REFERENCES artiste(idArtiste),
    specialiteArtiste VARCHAR(100)
);

CREATE TABLE pseudo_artiste
(
    idArtiste INT PRIMARY KEY NOT NULL REFERENCES artiste(idArtiste),
    pseudoArtiste VARCHAR(100)
);

CREATE TABLE evaluation
(
    idArtiste INT NOT NULL REFERENCES artiste(idArtiste),
    idNumero INT NOT NULL REFERENCES numero(idNumero),
    noteExpert INT NOT NULL CHECK(noteExpert BETWEEN 0 and 10),
    evaluationExpert VARCHAR(1000),
    PRIMARY KEY (idArtiste, idNumero)
);
