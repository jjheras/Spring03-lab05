CREATE TABLE IF NOT EXISTS brand (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    warranty INT,
    country VARCHAR(250)
);


CREATE TABLE IF NOT EXISTS car (
    id INT AUTO_INCREMENT PRIMARY KEY,
    brandid INT,
    model VARCHAR(250),
    year INT,
    numberofdoors INT,
    isconvertible BOOLEAN,
    mileage INT,
    price DOUBLE,
    description TEXT,
    colour VARCHAR(250),
    fueltype VARCHAR(250),
    FOREIGN KEY (brandid) REFERENCES brand(id)
);


INSERT INTO brand (name, warranty, country) VALUES
('Toyota', 5, 'Japan'),
('Ford', 3, 'USA'),
('BMW', 4, 'Alemania');


INSERT INTO car (brandid, model, year, numberofdoors, isconvertible, mileage, price, description, colour, fueltype) VALUES
(1, 'Corolla', 2020, 4, FALSE, 15000, 20000.0, 'Tiene poco usp', 'Rojo', 'Gasolina'),
(2, 'Mustang', 2021, 2, TRUE, 5000, 35000.0, 'Es de alta gama', 'Azul', 'Gasolina'),
(3, 'X5', 2019, 4, FALSE, 20000, 50000.0, 'Es el mejor coche que tenemos', 'Negro', 'Diesel');