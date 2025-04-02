-- Table: restaurant
CREATE TABLE restaurant (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Table: meal
CREATE TABLE meal (
    id BIGSERIAL PRIMARY KEY,
    description VARCHAR(500) NOT NULL,
    date DATE NOT NULL,
    restaurant_id BIGINT NOT NULL,
    CONSTRAINT fk_meal_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurant(id) ON DELETE CASCADE
);

-- Table: reservation
CREATE TABLE reservation (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(255) NOT NULL UNIQUE,
    reservation_date TIMESTAMP NOT NULL,
    used BOOLEAN NOT NULL DEFAULT FALSE,
    meal_id BIGINT NOT NULL,
    CONSTRAINT fk_reservation_meal FOREIGN KEY (meal_id) REFERENCES meal(id) ON DELETE CASCADE
);
