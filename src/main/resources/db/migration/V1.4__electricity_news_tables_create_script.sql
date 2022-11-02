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
    id       bigserial not null,
    reason  varchar(255) not null,
    day date not null,
    start_time time not null,
    end_time time,
    division_id int not null,
    district_id int not null,
    upazila_id int,
    union_parishad_id int,
    PRIMARY KEY (id)
) PARTITION BY RANGE (division_id);

CREATE INDEX ON broadcasting_parent (division_id);

CREATE TABLE broadcasting_child_01 (
    CHECK ( division_id == 1 )
) INHERITS (broadcasting_parent);

CREATE TABLE broadcasting_child_02 (
    CHECK ( division_id == 2 )
) INHERITS (broadcasting_parent);

CREATE TABLE broadcasting_child_03 (
    CHECK ( division_id == 3 )
) INHERITS (broadcasting_parent);

CREATE TABLE broadcasting_child_04 (
    CHECK ( division_id == 4 )
) INHERITS (broadcasting_parent);

CREATE TABLE broadcasting_child_05 (
    CHECK ( division_id == 5 )
) INHERITS (broadcasting_parent);


CREATE TABLE broadcasting_child_06 (
    CHECK ( division_id == 6 )
) INHERITS (broadcasting_parent);

CREATE TABLE broadcasting_child_07 (
    CHECK ( division_id == 7 )
) INHERITS (broadcasting_parent);

CREATE TABLE broadcasting_child_08 (
    CHECK ( division_id == 8 )
) INHERITS (broadcasting_parent);