CREATE TABLE utilisateur (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    mot_de_passe VARCHAR(255) NOT NULL,
    type_compte ENUM('CLIENT', 'ORGANISATEUR') NOT NULL,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE evenement (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    titre VARCHAR(200) NOT NULL,
    date_evenement DATETIME NOT NULL,
    lieu VARCHAR(200) NOT NULL,
    prix DECIMAL(10, 2) NOT NULL,
    organisateur_id INTEGER NOT NULL,
    FOREIGN KEY (organisateur_id) REFERENCES utilisateur(id)
);

CREATE TABLE ticket (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    evenement_id INTEGER NOT NULL,
    utilisateur_id INTEGER NOT NULL,
    date_achat DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (evenement_id) REFERENCES evenement(id),
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateur(id)
);
