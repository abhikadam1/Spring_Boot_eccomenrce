-- src/main/resources/data.sql

-- -----------------------------------------------------
-- USERS (Passwords are BCrypt encoded)
-- -----------------------------------------------------

-- User: user@test.com (Password: 'password123')
INSERT INTO users (email, password, first_name, role) VALUES
('user@test.com', '$2a$10$tUj3YgXfN.12ZcT/mJ2iY.gA.H.P7uQ4d2E.2W.mI.0S.1G.7mB/m', 'Standard', 'ROLE_USER');

-- Admin: admin@test.com (Password: 'admin123')
INSERT INTO users (email, password, first_name, role) VALUES
('admin@test.com', '$2a$10$oB8h0Q/D.R/oXmE.2Y.P7uQ4d2E.2W.mI.0S.1G.7mB/mI.0S.1G.7mB/m', 'Administrator', 'ROLE_ADMIN');


-- -----------------------------------------------------
-- PRODUCTS
-- -----------------------------------------------------
INSERT INTO products (name, price_usd, description) VALUES
('Quantum Processor', 999.99, 'High-speed computational unit for next-gen systems.'),
('Gravity Boots', 129.50, 'Enhance your fitness with anti-gravity training.'),
('Neural Interface Cable', 49.99, 'Connect your thoughts directly to the web.'),
('Eco-Fibre Jacket', 249.00, 'Sustainable and temperature-regulating outerwear.');