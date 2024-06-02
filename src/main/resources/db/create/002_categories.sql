create table categories
(
    cat_id     bigserial primary key,
    cat_name       varchar(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
    created_by bigint       null,
    updated_at timestamp(6),
    updated_by bigint,
    deleted    boolean      default false
);

INSERT INTO categories(
    cat_name,
    created_at,
    updated_at,
    created_by,
    updated_by,
    deleted
)
VALUES ('Programming',CURRENT_TIMESTAMP, null, 1, null, false),
       ('Framework',CURRENT_TIMESTAMP, null, 1, null, false),
       ('Database',CURRENT_TIMESTAMP, null, 1, null, false),
       ('Algorithm',CURRENT_TIMESTAMP, null, 1, null, false);
