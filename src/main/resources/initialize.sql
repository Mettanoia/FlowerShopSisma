-- Create FlowerShop table if it does not exist
CREATE TABLE IF NOT EXISTS FlowerShop (
                                          id VARCHAR(255) PRIMARY KEY,
                                          name VARCHAR(255) NOT NULL
);

-- Create Tickets table if it does not exist
CREATE TABLE IF NOT EXISTS Tickets (
                                       id VARCHAR(255) PRIMARY KEY,
                                       dateTime TIMESTAMP NOT NULL
);

-- Create Products table if it does not exist
CREATE TABLE IF NOT EXISTS Products (
                                        id VARCHAR(255) PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL,
                                        color VARCHAR(255),
                                        height DOUBLE,
                                        material ENUM('PLASTIC', 'WOOD') DEFAULT NULL,
                                        price DOUBLE NOT NULL,
                                        stock INT NOT NULL,
                                        type ENUM('Flower', 'Decoration', 'Tree') NOT NULL
);

-- Create TicketProducts junction table to link Tickets and Products with quantity
CREATE TABLE IF NOT EXISTS TicketProducts (
                                              ticketId VARCHAR(255),
                                              productId VARCHAR(255),
                                              quantity INT NOT NULL,
                                              FOREIGN KEY (ticketId) REFERENCES Tickets(id),
                                              FOREIGN KEY (productId) REFERENCES Products(id) ON DELETE SET NULL,
                                              PRIMARY KEY (ticketId, productId)
);

-- Create ShopTickets junction table to link FlowerShop and Tickets
CREATE TABLE IF NOT EXISTS ShopTickets (
                                           shopId VARCHAR(255),
                                           ticketId VARCHAR(255),
                                           FOREIGN KEY (shopId) REFERENCES FlowerShop(id) ON DELETE CASCADE,
                                           FOREIGN KEY (ticketId) REFERENCES Tickets(id),
                                           PRIMARY KEY (shopId, ticketId)
);

-- Create ShopProducts junction table to link FlowerShop and Products
CREATE TABLE IF NOT EXISTS ShopProducts (
                                            shopId VARCHAR(255),
                                            productId VARCHAR(255),
                                            FOREIGN KEY (shopId) REFERENCES FlowerShop(id) ON DELETE CASCADE,
                                            FOREIGN KEY (productId) REFERENCES Products(id),
                                            PRIMARY KEY (shopId, productId)
);

-- Insert example data into FlowerShop table
INSERT INTO FlowerShop (id, name) VALUES
                                      ('S1', 'Blooming Blossoms'),
                                      ('S2', 'Petals & Pots');

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

-- Insert example data into ShopTickets junction table
INSERT INTO ShopTickets (shopId, ticketId) VALUES
                                               ('S1', 'T123'),
                                               ('S1', 'T124'),
                                               ('S1', 'T125'),
                                               ('S2', 'T126'),
                                               ('S2', 'T127'),
                                               ('S2', 'T128'),
                                               ('S1', 'T129'),
                                               ('S2', 'T130'),
                                               ('S1', 'T131'),
                                               ('S2', 'T132');

-- Insert example data into ShopProducts junction table
INSERT INTO ShopProducts (shopId, productId) VALUES
                                                 ('S1', 'F1'),
                                                 ('S1', 'F2'),
                                                 ('S1', 'D1'),
                                                 ('S2', 'D2'),
                                                 ('S2', 'T1'),
                                                 ('S2', 'T2'),
                                                 ('S1', 'F3'),
                                                 ('S2', 'D3'),
                                                 ('S1', 'T3'),
                                                 ('S2', 'T4');
