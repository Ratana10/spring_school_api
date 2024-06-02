create table study_types
(
    id         bigserial primary KEY,
    name       varchar(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
    created_by bigint       null,
    updated_at timestamp(6),
    updated_by bigint,
    deleted    boolean      default false
);

INSERT INTO study_types(
    name,
    created_at,
    updated_at,
    created_by,
    updated_by,
    deleted
)
VALUES ('online',CURRENT_TIMESTAMP, null, 1, null, false),
       ('youtube',CURRENT_TIMESTAMP, null, 1, null, false),
       ('offline',CURRENT_TIMESTAMP, null, 1, null, false);
