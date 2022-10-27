create table modules(
  id uuid default uuid_generate_v4(),
  title varchar(150) not null,
  description varchar(250) not null,
  creation_date timestamp without time zone,
  course_id uuid not null,

  primary key (id),
  constraint fk_modules_course_id
      foreign key (course_id)
          references courses(id)
);