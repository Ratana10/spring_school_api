create table databasechangelog
(
    id            varchar(255) not null,
    author        varchar(255) not null,
    filename      varchar(255) not null,
    dateexecuted  timestamp    not null,
    orderexecuted integer      not null,
    exectype      varchar(10)  not null,
    md5sum        varchar(35),
    description   varchar(255),
    comments      varchar(255),
    tag           varchar(255),
    liquibase     varchar(20),
    contexts      varchar(255),
    labels        varchar(255),
    deployment_id varchar(10)
);

create table databasechangeloglock
(
    id          integer not null
        primary key,
    locked      boolean not null,
    lockgranted timestamp,
    lockedby    varchar(255)
);

create table categories
(
    cat_id     bigserial
        primary key,
    created_at timestamp(6) not null,
    created_by bigint       not null,
    updated_at timestamp(6),
    updated_by bigint,
    cat_name   varchar(255),
    deleted    boolean      not null
);

comment on column categories.deleted is 'Soft-delete indicator';

create table promotions
(
    pro_id          bigserial
        primary key,
    created_at      timestamp(6) not null,
    created_by      bigint       not null,
    updated_at      timestamp(6),
    updated_by      bigint,
    pro_description varchar(255),
    dis_amount      numeric(38, 2),
    disc_percentage numeric(38, 2),
    pro_end_date    timestamp(6),
    pro_name        varchar(255),
    pro_start_date  timestamp(6),
    deleted         boolean      not null
);

comment on column promotions.deleted is 'Soft-delete indicator';

create table students
(
    stu_id     bigserial
        primary key,
    created_at timestamp(6) not null,
    created_by bigint       not null,
    updated_at timestamp(6),
    updated_by bigint,
    email      varchar(255)
        constraint uk_e2rndfrsx22acpq2ty1caeuyw
            unique,
    gender     varchar(255),
    stu_name   varchar(255),
    password   varchar(255),
    phone      varchar(255)
        constraint uk_4j48kma5fa3dcya13gd0l3gi
            unique,
    stu_type   varchar(255)
        constraint students_stu_type_check
            check ((stu_type)::text = ANY ((ARRAY ['STUDY'::character varying, 'WORK'::character varying])::text[])),
    deleted    boolean      not null
);

comment on column students.deleted is 'Soft-delete indicator';

create table enrollments
(
    enr_id             bigserial
        primary key,
    created_at         timestamp(6) not null,
    created_by         bigint       not null,
    updated_at         timestamp(6),
    updated_by         bigint,
    enr_amount         numeric(38, 2),
    enr_date           timestamp(6),
    enr_payment_status varchar(255)
        constraint enrollments_enr_payment_status_check
            check ((enr_payment_status)::text = ANY
        ((ARRAY ['UNPAID'::character varying, 'PAID'::character varying, 'PARTIAL'::character varying])::text[])),
    enr_remain         numeric(38, 2),
    deleted            boolean      not null,
    stu_id             bigint
        constraint fkop3ifxpqtm4mkklmom4s5ymae
            references students
);

comment on column enrollments.deleted is 'Soft-delete indicator';

create table payments
(
    pay_id        bigserial
        primary key,
    created_at    timestamp(6) not null,
    created_by    bigint       not null,
    updated_at    timestamp(6),
    updated_by    bigint,
    pay_amount    numeric(38, 2),
    pay_date      date,
    pay_type      varchar(255)
        constraint payments_pay_type_check
            check ((pay_type)::text = ANY
        ((ARRAY ['ABA'::character varying, 'ACLEDA'::character varying, 'WING'::character varying, 'NONE'::character varying])::text[])),
    deleted       boolean      not null,
    enrollment_id bigint
        constraint fklygmnscikfnsu084rpdat2jo5
            references enrollments
);

comment on column payments.deleted is 'Soft-delete indicator';

create table study_types
(
    id         bigserial
        primary key,
    created_at timestamp(6) not null,
    created_by bigint       not null,
    updated_at timestamp(6),
    updated_by bigint,
    name       varchar(255),
    deleted    boolean      not null
);

comment on column study_types.deleted is 'Soft-delete indicator';

create table courses
(
    cau_id          bigserial
        primary key,
    created_at      timestamp(6) not null,
    created_by      bigint       not null,
    updated_at      timestamp(6),
    updated_by      bigint,
    stu_price       numeric(38, 2),
    cou_description varchar(255),
    cou_image       varchar(255),
    cou_name        varchar(255),
    normal_price    numeric(38, 2),
    deleted         boolean      not null,
    cat_id          bigint constraint fko0xfcxv62yier41v6c554fhu7
            references categories,
    study_type_id   bigint
        constraint fka0j8msv14tegggxlbt88sjsdl
            references study_types
);

comment on column courses.deleted is 'Soft-delete indicator';

create table course_schedule
(
    id         bigserial
        primary key,
    created_at timestamp(6) not null,
    created_by bigint       not null,
    updated_at timestamp(6),
    updated_by bigint,
    day        varchar(255)
        constraint course_schedule_day_check
            check ((day)::text = ANY
        ((ARRAY ['MONDAY'::character varying, 'TUESDAY'::character varying, 'WEDNESDAY'::character varying, 'THURSDAY'::character varying, 'FRIDAY'::character varying, 'SATURDAY'::character varying, 'SUNDAY'::character varying])::text[])),
    end_time   time(6),
    start_time time(6),
    deleted    boolean      not null,
    course_id  bigint
        constraint fkahfhilt2apyqcx619gvvc1pnw
            references courses
);

comment on column course_schedule.deleted is 'Soft-delete indicator';

create table enroll_course
(
    enr_id bigint not null
        constraint fk1kn3tivkgkuaq8hbns0bmt3xp
            references enrollments,
    cou_id bigint not null
        constraint fkd3qc4mtpiaak2x0m7j3gqdghg
            references courses,
    primary key (enr_id, cou_id)
);

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

create table testing
(
    cat_id     bigserial
        primary key,
    created_at timestamp(6) not null,
    created_by bigint       not null,
    updated_at timestamp(6),
    updated_by bigint,
    cat_name   varchar(255),
    deleted    boolean      not null
);

comment on column testing.deleted is 'Soft-delete indicator';

create table users
(
    id         bigserial
        primary key,
    created_at timestamp(6) not null,
    created_by bigint       not null,
    updated_at timestamp(6),
    updated_by bigint,
    email      varchar(255),
    first_name varchar(255),
    last_name  varchar(255),
    password   varchar(255),
    role       varchar(255)
        constraint users_role_check
            check ((role)::text = ANY ((ARRAY ['ADMIN'::character varying, 'STUDENT'::character varying])::text[])),
    deleted    boolean      not null
);

comment on column users.deleted is 'Soft-delete indicator';

