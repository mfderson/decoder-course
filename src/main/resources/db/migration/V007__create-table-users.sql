create table users(
  id uuid,
  email varchar(50) unique not null,
  full_name varchar(150) not null,
  status varchar(32) not null,
  type varchar(32) not null,
  cpf varchar(20),
  image_url varchar(255),
  primary key (id)
);