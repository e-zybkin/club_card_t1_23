CREATE DATABASE club_card_db;
\c club_card_db;

CREATE TABLE person (
                        id INTEGER GENERATED BY DEFAULT AS IDENTITY,
                        username VARCHAR(255) UNIQUE NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        date_of_birth DATE NOT NULL,
                        role VARCHAR(255) NOT NULL,
                        privilege VARCHAR(255) NOT NULL,
                        card_id INTEGER
--                         FOREIGN KEY (card_id) REFERENCES card(id)
);