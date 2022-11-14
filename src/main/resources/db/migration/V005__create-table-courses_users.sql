create table courses_users(
    id uuid default uuid_generate_v4(),
    user_id uuid not null,
    course_id uuid not null,
    primary key (id),
    constraint fk_course_id_course_id
        foreign key (course_id)
            references courses(id)
);