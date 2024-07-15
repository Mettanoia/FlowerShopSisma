-- Create tables if they do not exist
CREATE TABLE IF NOT EXISTS Tickets (
                                       id VARCHAR(255) PRIMARY KEY,
    dateTime TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS Products (
                                        id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    color VARCHAR(255),
    height DOUBLE,
    material VARCHAR(255),
    price DOUBLE,
    stock INT,
    ticketId VARCHAR(255),
    type VARCHAR(50),
    FOREIGN KEY (ticketId) REFERENCES Tickets(id)
    );

-- Insert example data into Tickets and Products tables
INSERT INTO Tickets (id, dateTime) VALUES ('T123', CURRENT_TIMESTAMP);

INSERT INTO Products (id, name, color, height, material, price, stock, ticketId, type)
VALUES
    ('F1', 'Rose', 'Red', NULL, 'Silk', 15.0, 100, 'T123', 'Flower'),
    ('T1', 'Oak', 'Brown', 5.0, 'Wood', 50.0, 20, 'T123', 'Tree'),
    ('D1', 'Vase', NULL, NULL, 'Glass', 25.0, 30, 'T123', 'Decoration');
