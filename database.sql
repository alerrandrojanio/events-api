CREATE TABLE events (
    event_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    pretty_name VARCHAR(255),
    location VARCHAR(255),
    price DECIMAL(10, 2),
    start_date DATE,
    end_date DATE,
    start_time TIME,
    end_time TIME
);