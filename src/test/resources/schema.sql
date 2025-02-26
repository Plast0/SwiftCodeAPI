CREATE TABLE BRANCH (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        address VARCHAR(255),
                        bank_name VARCHAR(255),
                        countryiso2 VARCHAR(225),
                        country_name VARCHAR(255),
                        is_headquarter BOOLEAN,
                        swift_code VARCHAR(225),
                        headquarter_id BIGINT
);

CREATE TABLE HEADQUARTER (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        address VARCHAR(255),
                        bank_name VARCHAR(255),
                        countryiso2 VARCHAR(225),
                        country_name VARCHAR(255),
                        is_headquarter BOOLEAN,
                        swift_code VARCHAR(225)
);

