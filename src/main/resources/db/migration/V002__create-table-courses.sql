create table courses(
  id uuid default uuid_generate_v4(),
  name varchar(150) not null,
  description varchar(250) not null,
  image_url varchar(255),
  creation_date timestamp without time zone,
  last_update_date timestamp without time zone,
  status varchar(32) not null,
  level varchar(32) not null,
  user_instructor uuid not null,

  primary key (id)
);