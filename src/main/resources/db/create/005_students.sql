create table students
(
    stu_id     bigserial primary key,
    stu_name   varchar(255),
    email      varchar(255)
        constraint uk_e2rndfrsx22acpq2ty1caeuyw
            unique,
    password   varchar(255),
    gender     varchar(255),
    phone      varchar(255)
        constraint uk_4j48kma5fa3dcya13gd0l3gi
            unique,
    stu_type   varchar(255)
        constraint students_stu_type_check
            check ((stu_type)::text = ANY ((ARRAY ['STUDY'::character varying, 'WORK'::character varying])::text[])),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
    created_by bigint       null,
    updated_at timestamp(6),
    updated_by bigint,
    deleted    boolean      default false
);

INSERT INTO students(
    stu_name,
    email,
    password,
    gender,
    phone,
    stu_type,

    created_at,
    updated_at,
    created_by,
    updated_by,
    deleted
)
