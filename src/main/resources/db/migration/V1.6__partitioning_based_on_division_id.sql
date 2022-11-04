
drop table if exists division_wise_broadcasting_child_01;
drop table if exists division_wise_broadcasting_child_02;
drop table if exists division_wise_broadcasting_child_03;
drop table if exists division_wise_broadcasting_child_04;
drop table if exists division_wise_broadcasting_child_05;
drop table if exists division_wise_broadcasting_child_06;
drop table if exists division_wise_broadcasting_child_07;
drop table if exists division_wise_broadcasting_child_08;

drop table if exists division_wise_broadcasting_parent;

create table division_wise_broadcasting_parent
(
    id bigserial not null,
    reason  varchar(255) not null,
    day date not null,
    start_time time not null,
    end_time time,
    division_id int not null,
    district_id int not null,
    upazila_id int,
    union_parishad_id int
) PARTITION BY LIST (division_id);

CREATE INDEX ON division_wise_broadcasting_parent (division_id);


CREATE TABLE division_wise_broadcasting_child_01 PARTITION OF division_wise_broadcasting_parent
    FOR VALUES IN (1);

CREATE TABLE division_wise_broadcasting_child_02 PARTITION OF division_wise_broadcasting_parent
    FOR VALUES IN (2);

CREATE TABLE division_wise_broadcasting_child_03 PARTITION OF division_wise_broadcasting_parent
    FOR VALUES IN (3);

CREATE TABLE division_wise_broadcasting_child_04 PARTITION OF division_wise_broadcasting_parent
    FOR VALUES IN (4);


CREATE TABLE division_wise_broadcasting_child_05 PARTITION OF division_wise_broadcasting_parent
    FOR VALUES IN (5);

CREATE TABLE division_wise_broadcasting_child_06 PARTITION OF division_wise_broadcasting_parent
    FOR VALUES IN (6);


CREATE TABLE division_wise_broadcasting_child_07 PARTITION OF division_wise_broadcasting_parent
    FOR VALUES IN (7);

CREATE TABLE division_wise_broadcasting_child_08 PARTITION OF division_wise_broadcasting_parent
    FOR VALUES IN (8);
