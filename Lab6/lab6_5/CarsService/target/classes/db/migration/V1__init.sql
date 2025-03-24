CREATE TABLE cars (
    car_id SERIAL PRIMARY KEY,
    maker VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    segment VARCHAR(50),
    engine_type VARCHAR(50)
);
