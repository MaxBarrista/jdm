create sequence hibernate_sequence start 1 increment 1;

create table car (
    id int8 not null,
    filename varchar(255),
    original_filename varchar(255),
    manufacturer varchar(255),
    mileage int4,
    model varchar(255),
    model_year int4,
    published timestamp,
    user_id int8,
    primary key (id)
);

create table user_role (
    user_id int8 not null,
    roles varchar(255)
);
    
create table usr (
    id int8 not null,
    activation_code varchar(255),
    active boolean not null,
    email varchar(255),
    password varchar(255) not null,
    username varchar(255) not null,
    primary key (id)
);

alter table if exists car
    add constraint car_user_fk
    foreign key (user_id) references usr;

alter table if exists user_role
    add constraint user_role_user_fk
    foreign key (user_id) references usr
