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

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE subscriptions (
    subscription_id INT AUTO_INCREMENT PRIMARY KEY,
    event_id INT NOT NULL,
    subscribed_user_id INT NOT NULL,
    indication_user_id INT NULL,
    FOREIGN KEY (event_id) REFERENCES events(event_id) ON DELETE CASCADE,
    FOREIGN KEY (subscribed_user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (indication_user_id) REFERENCES users(user_id) ON DELETE SET NULL
);