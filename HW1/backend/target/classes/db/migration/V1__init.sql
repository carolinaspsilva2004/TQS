-- Table: restaurant
CREATE TABLE restaurant (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    external_menu_url VARCHAR(500)
);

-- Table: meal
CREATE TABLE meal (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(500) NOT NULL,
    date DATE NOT NULL,
    restaurant_id BIGINT NOT NULL,
    CONSTRAINT fk_meal_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurant(id) ON DELETE CASCADE
);

-- Table: reservation
CREATE TABLE reservation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(255) NOT NULL UNIQUE,
    meal_id BIGINT NOT NULL,
    used BOOLEAN NOT NULL DEFAULT FALSE,
    reservation_date TIMESTAMP NOT NULL,
    CONSTRAINT fk_reservation_meal FOREIGN KEY (meal_id) REFERENCES meal(id) ON DELETE CASCADE
);
