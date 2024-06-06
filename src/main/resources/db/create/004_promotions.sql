create table promotions
(
    pro_id          bigserial primary key,
    pro_name        varchar(255),
    pro_description varchar(255),
    dis_amount      numeric(38, 2),
    disc_percentage numeric(38, 2),
    pro_start_date  timestamp(6),
    pro_end_date    timestamp(6),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
    created_by bigint       null,
    updated_at timestamp(6),
    updated_by bigint,
    deleted    boolean      default false
);


INSERT INTO promotions(
    pro_name,
    pro_description,
    dis_amount,
    disc_percentage,
    pro_start_date,
    pro_end_date,

    created_at,
    updated_at,
    created_by,
    updated_by,
    deleted
)
VALUES
    ('Buy 3 discount 50%', 'Buy 3  courses discount 50%', 0, 0.5, '2024-05-20 00:00:00', '2024-06-20 00:00:00', CURRENT_TIMESTAMP, null, 1, null, false),
    ('Buy 3 discount paid 2', 'Buy 3 courses  paid 2 course', 0, 0, '2024-05-20 00:00:00', '2024-06-20 00:00:00', CURRENT_TIMESTAMP, null, 1, null, false),
    ('Buy 3 only 125$', 'Buy 3 courses paid only 125$', 125, 0, '2024-05-20 00:00:00', '2024-06-20 00:00:00', CURRENT_TIMESTAMP, null, 1, null, false),
    ('Buy 3 free 2 ', 'Buy 3 (java basic, java advance, spring core) courses free 2 courses (springboot and microservice), ',
     0, 0, '2024-05-20 00:00:00', '2024-06-20 00:00:00', CURRENT_TIMESTAMP, null, 1, null, false);



create table promotion_required_courses
(
    pro_id bigint not null
        constraint fkfricvu545ge40qvnod41jfy3c
            references promotions,
    cou_id bigint not null
        constraint fkhg3ovy6l16apoovwokikn0yp0
            references courses,
    primary key (pro_id, cou_id)
);

INSERT INTO promotion_required_courses(
    pro_id, cou_id
)
VALUES (4, 1),
        (4, 2),
       (4, 3);


create table promotion_courses
(
    pro_id bigint not null
        constraint fkfm8hl2h45o8xdk16q0eixno9t
        references promotions,
    cou_id bigint not null
        constraint fkn7y1cmmids2p3r8hsm20sfg0n
        references courses,
    primary key (pro_id, cou_id)
);



INSERT INTO promotion_courses(
    pro_id, cou_id
)
VALUES (4, 4),
       (4, 5);


