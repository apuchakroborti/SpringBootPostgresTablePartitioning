
alter table broadcasting_parent add column id bigserial not null;
-- CREATE SEQUENCE broadcasting_parent_id_seq INCREMENT BY 1 START WITH 1 NO CYCLE OWNED BY broadcasting_parent.ID;
