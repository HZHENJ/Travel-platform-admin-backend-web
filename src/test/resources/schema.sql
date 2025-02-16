CREATE TABLE IF NOT EXISTS admins (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS reviews (
                                       review_id INT AUTO_INCREMENT PRIMARY KEY,
                                       user_id INT NOT NULL,
                                       item_type VARCHAR(20) NOT NULL,
    item_id INT NOT NULL,
    rating DECIMAL(2,1) NOT NULL,
    comment TEXT,
    reply TEXT,
    status VARCHAR(10) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );