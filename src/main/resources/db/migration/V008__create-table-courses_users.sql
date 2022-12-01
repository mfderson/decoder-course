create table courses_users(
  user_id uuid not null,
  course_id uuid not null,
  constraint fk_courses_users_user_id FOREIGN KEY (user_id) references users(id),
  constraint fk_courses_users_course_id FOREIGN KEY (course_id) references courses(id),
  constraint courses_users_pkey primary key (user_id, course_id)
);