CREATE TABLE testing (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                         updated_at TIMESTAMP,
                         created_by BIGINT NOT NULL,
                         updated_by BIGINT,
                         deleted BOOLEAN DEFAULT FALSE
);