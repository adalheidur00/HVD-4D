
CREATE TABLE IF NOT EXISTS Users (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    email TEXT NOT NULL,
    phoneNumber TEXT NOT NULL,
);

INSERT INTO Users (name, email, phoneNumber) VALUES ('Alla', 'alla@hi.is', '1234567')
