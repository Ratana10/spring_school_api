create table courses
(
    cau_id          bigserial primary key,
    cou_name        varchar(255),
    cou_description varchar(255),
    cou_image       varchar(255),
    normal_price    numeric(38, 2),
    stu_price       numeric(38, 2),
    cat_id          bigint
        constraint fk_courses_categories
        references categories,
    study_type_id   bigint
        constraint fka0j8msv14tegggxlbt88sjsdl
        references study_types,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
    created_by bigint       null,
    updated_at timestamp(6),
    updated_by bigint,
    deleted    boolean      default false
);

INSERT INTO courses(
    cou_name,
    cou_description,
    cou_image,
    normal_price,
    stu_price,
    cat_id,
    study_type_id,
    created_at,
    updated_at,
    created_by,
    updated_by,
    deleted
)
VALUES ('Java Basic', 'Java Basic description', null,70, 50, 1, 2, CURRENT_TIMESTAMP, null, 1, null, false),
        ('Java Advance', 'Java Advance description', null,70, 50, 1, 2, CURRENT_TIMESTAMP, null, 1, null, false),
        ('Spring Core', 'Spring Core description', null,120, 100, 1, 2, CURRENT_TIMESTAMP, null, 1, null, false),
        ('Spring Boot', 'Spring Boot description', null,200 , 220, 1, 2, CURRENT_TIMESTAMP, null, 1, null, false),
        ('Microservice', 'Microservice description', null,220 , 200, 1, 1, CURRENT_TIMESTAMP, null, 1, null, false)

