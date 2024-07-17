-- Create Tickets table if it does not exist
CREATE TABLE IF NOT EXISTS Tickets (
                                       id VARCHAR(255) PRIMARY KEY,
                                       dateTime TIMESTAMP
);

-- Create Products table if it does not exist
CREATE TABLE IF NOT EXISTS Products (
                                        id VARCHAR(255) PRIMARY KEY,
                                        name VARCHAR(255),
                                        color VARCHAR(255),
                                        height DOUBLE,
                                        material VARCHAR(255) CHECK (material IN ('PLASTIC', 'WOOD') OR material IS NULL),
                                        price DOUBLE,
                                        stock INT,
                                        ticketId VARCHAR(255),
                                        type VARCHAR(50),
                                        FOREIGN KEY (ticketId) REFERENCES Tickets(id)
);

-- Insert example data into Tickets table
INSERT INTO Tickets (id, dateTime) VALUES
                                       ('T123', CURRENT_TIMESTAMP),
                                       ('T124', CURRENT_TIMESTAMP),
                                       ('T125', CURRENT_TIMESTAMP),
                                       ('T126', CURRENT_TIMESTAMP),
                                       ('T127', CURRENT_TIMESTAMP),
                                       ('T128', CURRENT_TIMESTAMP),
                                       ('T129', CURRENT_TIMESTAMP),
                                       ('T130', CURRENT_TIMESTAMP),
                                       ('T131', CURRENT_TIMESTAMP),
                                       ('T132', CURRENT_TIMESTAMP);

-- Insert products for each ticket
INSERT INTO Products (id, name, color, height, material, price, stock, ticketId, type) VALUES
                                                                                           ('F1', 'Rose', 'Red', NULL, NULL, 15.0, 100, 'T123', 'Flower'),
                                                                                           ('F2', 'Tulip', 'Yellow', NULL, NULL, 12.0, 80, 'T124', 'Flower'),
                                                                                           ('D1', 'Vase', NULL, NULL, 'PLASTIC', 25.0, 30, 'T125', 'Decoration'),
                                                                                           ('D2', 'Picture Frame', NULL, NULL, 'WOOD', 45.0, 20, 'T126', 'Decoration'),
                                                                                           ('T1', 'Oak', NULL, 5.0, NULL, 50.0, 20, 'T127', 'Tree'),
                                                                                           ('T2', 'Pine', NULL, 6.5, NULL, 60.0, 15, 'T128', 'Tree'),
                                                                                           ('F3', 'Lily', 'White', NULL, NULL, 20.0, 60, 'T129', 'Flower'),
                                                                                           ('D3', 'Lamp', NULL, NULL, 'PLASTIC', 35.0, 40, 'T130', 'Decoration'),
                                                                                           ('T3', 'Maple', NULL, 7.0, NULL, 70.0, 10, 'T131', 'Tree'),
                                                                                           ('T4', 'Birch', NULL, 4.0, NULL, 40.0, 25, 'T132', 'Tree');
