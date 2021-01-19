create database airport_db;

create table airplane_model(
    id serial primary key,
    model_name varchar(128) not null,
    model_seats smallint not null,
    model_crew smallint not null
);

insert into airplane_model(model_name, model_seats, model_crew) values 
('Airbus A380', 486, 2),
('Embraer E2', 86, 2);

create table airplanes(
    id serial primary key,
    reg_number varchar(128) unique not null,
    model_id int references airplane_model(id)
);

insert into airplanes(reg_number, model_id) values 
/* Airbus */
('DAAAA-33-01-43459', 1),
('DAAAA-33-53-11300', 1),

/* Embraer */
('DAAAA-77-21-88001', 2),
('DAAAA-77-23-00193', 2),
('DAAAA-77-62-06619', 2);

create table pilot(
    id serial primary key,
    name varchar(32) not null,
    age smallint not null,
    driving smallint not null
);

insert into pilot(name, age, driving) values
('Lucas', 38, 1),
('Mark', 40, 1),
('Stefan', 34, 2),
('Ronald', 28, 1),
('Brian', 33, 2),
('Steven', 28, 2),
('Mike', 39, 1),
('John', 40, 1);

create table driving(
    id serial primary key,
    pilot_id int references pilot(id),
    model_id int references airplane_model(id)
);

insert into driving(pilot_id, model_id) values 
(1,2),
(2,2),
(3,1),
(3,2),
(4,2),
(5,1),
(5,2),
(6,1),
(6,2),
(7,1),
(8,2);