create table IF NOT EXISTS customer (
    id         binary(16)   not null primary key,
    birthdate  date         not null,
    first_name varchar(255) not null,
    last_name  varchar(255) not null
);

create table IF NOT EXISTS vehicle (
    id                            binary(16)     not null primary key,
    brand                         varchar(255)   not null,
    model                         varchar(255)   not null,
    model_year                    varchar(255)   not null,
    price                         decimal(19, 2) not null,
    vehicle_identification_number varchar(255)   not null
);

create table IF NOT EXISTS leasing_contract (
    id              binary(16)     not null primary key,
    contract_number int            not null,
    monthly_rate    decimal(19, 2) not null,
    customer_id     binary(16)     not null,
    vehicle_id      binary(16)     null,
    constraint FKpxeeq63s4vh9272vr2tipsh2s foreign key (customer_id) references customer (id),
    constraint FKqpkf9hkk4ps9mg91ifuc16qni foreign key (vehicle_id) references vehicle (id)
);
