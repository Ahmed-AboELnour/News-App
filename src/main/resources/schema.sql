CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       full_name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       date_of_birth DATE NOT NULL,
                       role VARCHAR(50) NOT NULL
);

CREATE TABLE news (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      title VARCHAR(255) NOT NULL,
                      title_arabic VARCHAR(255),
                      description TEXT NOT NULL,
                      description_arabic TEXT,
                      publish_date TIMESTAMP,
                      image_url VARCHAR(255),
                      status VARCHAR(50) DEFAULT 'PENDING',
                      softDeleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE BlacklistedToken (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  token VARCHAR(255) NOT NULL UNIQUE,
                                  expiry_date TIMESTAMP NOT NULL
);