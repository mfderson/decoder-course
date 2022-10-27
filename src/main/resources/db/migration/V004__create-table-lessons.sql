create table lessons(
  id uuid default uuid_generate_v4(),
  title varchar(150) not null,
  description varchar(250) not null,
  video_url varchar,
  creation_date timestamp without time zone,
  module_id uuid not null,

  primary key (id),
  constraint fk_lessons_module_id
      foreign key (module_id)
          references modules(id)
);