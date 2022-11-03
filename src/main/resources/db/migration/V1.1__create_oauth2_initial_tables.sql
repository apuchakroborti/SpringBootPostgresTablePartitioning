drop table if exists oauth_user_authorities;
drop table if exists oauth_access_token;
drop table if exists oauth_authority;
drop table if exists oauth_client_details;
drop table if exists oauth_client_token;
drop table if exists oauth_refresh_token;
drop table if exists oauth_user;

create table oauth_user
(
    id                  bigserial       not null,
    username            varchar(255) not null,
    password            varchar(255) not null,

    account_expired     boolean,
    account_locked      boolean,
    credentials_expired boolean,
    enabled             boolean,

    PRIMARY KEY (id)
);

create table oauth_authority
(
    id                  bigserial       not null,
    name                varchar(255) not null,
    PRIMARY KEY (id)
);

create table oauth_user_authorities
(
    user_id                  bigint       not null,
    authority_id             bigint       not null
);


create table oauth_access_token (
  token_id varchar(255),
  token bytea,
  authentication_id varchar(255) primary key,
  user_name varchar(255),
  client_id varchar(255),
  authentication bytea,
  refresh_token varchar(255)
);

create table oauth_client_token (
  token_id varchar(255),
  token bytea,
  authentication_id varchar(255) primary key,
  user_name varchar(255),
  client_id varchar(255)
);

create table oauth_client_details (
  client_id varchar(255) primary key,
  resource_ids varchar(255),
  client_secret varchar(255),
  scope varchar(255),
  authorized_grant_types varchar(255),
  web_server_redirect_uri varchar(255),
  authorities varchar(255),
  access_token_validity integer,
  refresh_token_validity integer,
  additional_information varchar(4096),
  autoapprove varchar(255)
) ;

create table oauth_refresh_token (
  token_id varchar(255),
  token bytea,
  authentication bytea
);