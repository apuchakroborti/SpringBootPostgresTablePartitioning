drop table if exists country;
drop table if exists division;
drop table if exists district;
drop table if exists upazila;
drop table if exists broadcasting_parent;
drop table if exists broadcasting_child_01;
drop table if exists broadcasting_child_02;
drop table if exists broadcasting_child_03;
drop table if exists broadcasting_child_04;
drop table if exists broadcasting_child_05;
drop table if exists broadcasting_child_06;
drop table if exists broadcasting_child_07;
drop table if exists broadcasting_child_08;

create table country
(
    id bigserial not null,
    name_en varchar(64) not null,
    name_bn varchar(64) not null,
    country_code varchar(16) not null,
    iso_codes varchar(16) not null,
    area_skm int not null,
    PRIMARY KEY (id)
);

create table division
(
    id                          bigserial not null,
    country_id                  int not null,
    name_en                     varchar(64) not null,
    name_bn                     varchar(64) not null,
    number_of_district          int,
    number_of_upazila           int,
    number_of_union_parishad    int,
    area_skm                    int not null,
    PRIMARY KEY (id)
);

create table district
(
    id                          bigserial not null,
    name_en                     varchar(64) not null,
    name_bn                     varchar(64) not null,
    division_id                 int not null,
    PRIMARY KEY (id)
);


create table upazila
(
    id                          bigserial not null,
    name_en                     varchar(64),
    name_bn                     varchar(64),
    district_id                 int not null,
    PRIMARY KEY (id)
);

create table broadcasting_parent
(
    reason  varchar(255) not null,
    day date not null,
    start_time time not null,
    end_time time,
    division_id int not null,
    district_id int not null,
    upazila_id int,
    union_parishad_id int
) PARTITION BY RANGE (day);

CREATE INDEX ON broadcasting_parent (day);


CREATE TABLE broadcasting_child_01 PARTITION OF broadcasting_parent
    FOR VALUES FROM ('2022-01-01') TO ('2022-01-31');

CREATE TABLE broadcasting_child_02 PARTITION OF broadcasting_parent
    FOR VALUES FROM ('2022-02-01') TO ('2022-03-31');

CREATE TABLE broadcasting_child_03 PARTITION OF broadcasting_parent
    FOR VALUES FROM ('2022-04-01') TO ('2022-04-30');

CREATE TABLE broadcasting_child_04 PARTITION OF broadcasting_parent
    FOR VALUES FROM ('2022-05-01') TO ('2022-05-31');

CREATE TABLE broadcasting_child_05 PARTITION OF broadcasting_parent
    FOR VALUES FROM ('2022-06-01') TO ('2022-06-30');

CREATE TABLE broadcasting_child_06 PARTITION OF broadcasting_parent
    FOR VALUES FROM ('2022-07-01') TO ('2022-07-31');

CREATE TABLE broadcasting_child_07 PARTITION OF broadcasting_parent
    FOR VALUES FROM ('2022-08-01') TO ('2022-10-31');

CREATE TABLE broadcasting_child_08 PARTITION OF broadcasting_parent
    FOR VALUES FROM ('2022-11-01') TO ('2022-12-31');
