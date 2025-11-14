CREATE TABLE utilisateur (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              nom VARCHAR(100) NOT NULL,
                              email VARCHAR(150) NOT NULL UNIQUE,
                              mot_de_passe VARCHAR(255) NOT NULL,
                              type_compte ENUM('CLIENT', 'ORGANISATEUR') NOT NULL,
                              date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE evenement (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            nom VARCHAR(100) NOT NULL,
                            date DATETIME NOT NULL,
                            lieu VARCHAR(100) NOT NULL,
                            type_evenement ENUM('CONCERT','SPECTACLE','CONFERENCE') NOT NUL,
                            organisateur_id INTEGER NOT NULL,
                            FOREIGN KEY (organisateur_id) REFERENCES utilisateur(id)
);

CREATE TABLE category_place (
                                   id INT AUTO_INCREMENT PRIMARY KEY,
                                   evenement_id INT,
                                   categorie VARCHAR(50),
                                   nb_places INT,
                                   prix DOUBLE,
                                   FOREIGN KEY (evenement_id) REFERENCES evenements(id)
);



CREATE TABLE reservation (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              client_id INT,
                              evenement_id INT,
                              category_place_id INT,
                              date_reservation DATETIME,
                              FOREIGN KEY (client_id) REFERENCES utilisateurs(id),
                              FOREIGN KEY (evenement_id) REFERENCES evenements(id),
                              FOREIGN KEY (category_place_id) REFERENCES category_place(id)
);