drop table if exists USER;

CREATE TABLE USER(
    ID              bigint not null auto_increment,
    USER_ID         VARCHAR(255) NOT NULL UNIQUE,
    FIRST_NAME	    varchar(255) not null,
    LAST_NAME	    varchar(255),
    EMAIL	        varchar(128) not null,
    PHONE	        varchar(32),
    TIN	            varchar(255),
    NID	            varchar(255),
    PASSPORT	    varchar(64),
    DATE_OF_JOINING datetime not null,
    DESIGNATION_ID	int,
    ADDRESS_ID	    int,

    STATUS	        BOOLEAN DEFAULT TRUE,

    CREATED_BY	    bigint NOT NULL,
    CREATE_TIME	    datetime NOT NULL,
    EDITED_BY	    bigint,
    EDIT_TIME       datetime,
    INTERNAL_VERSION bigint default 1,
    oauth_user_id     bigint,
    primary key (ID)
) engine=InnoDB;

