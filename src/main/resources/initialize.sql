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
                                        type VARCHAR(50)
);

-- Create TicketProducts junction table to link Tickets and Products with quantity
CREATE TABLE IF NOT EXISTS TicketProducts (
                                              ticketId VARCHAR(255),
                                              productId VARCHAR(255),
                                              quantity INT,
                                              FOREIGN KEY (ticketId) REFERENCES Tickets(id),
                                              FOREIGN KEY (productId) REFERENCES Products(id),
                                              PRIMARY KEY (ticketId, productId)
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

-- Insert example data into Products table
INSERT INTO Products (id, name, color, height, material, price, stock, type) VALUES
                                                                                 ('F1', 'Rose', 'Red', NULL, NULL, 15.0, 100, 'Flower'),
                                                                                 ('F2', 'Tulip', 'Yellow', NULL, NULL, 12.0, 80, 'Flower'),
                                                                                 ('D1', 'Vase', NULL, NULL, 'PLASTIC', 25.0, 30, 'Decoration'),
                                                                                 ('D2', 'Picture Frame', NULL, NULL, 'WOOD', 45.0, 20, 'Decoration'),
                                                                                 ('T1', 'Oak', NULL, 5.0, NULL, 50.0, 20, 'Tree'),
                                                                                 ('T2', 'Pine', NULL, 6.5, NULL, 60.0, 15, 'Tree'),
                                                                                 ('F3', 'Lily', 'White', NULL, NULL, 20.0, 60, 'Flower'),
                                                                                 ('D3', 'Lamp', NULL, NULL, 'PLASTIC', 35.0, 40, 'Decoration'),
                                                                                 ('T3', 'Maple', NULL, 7.0, NULL, 70.0, 10, 'Tree'),
                                                                                 ('T4', 'Birch', NULL, 4.0, NULL, 40.0, 25, 'Tree');

-- Insert example data into TicketProducts junction table
INSERT INTO TicketProducts (ticketId, productId, quantity) VALUES
                                                               ('T123', 'F1', 10),
                                                               ('T124', 'F2', 5),
                                                               ('T125', 'D1', 2),
                                                               ('T126', 'D2', 1),
                                                               ('T127', 'T1', 3),
                                                               ('T128', 'T2', 4),
                                                               ('T129', 'F3', 6),
                                                               ('T130', 'D3', 7),
                                                               ('T131', 'T3', 2),
                                                               ('T132', 'T4', 8);
