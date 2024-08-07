CREATE TABLE qms_site
(
    id             integer primary key,
    name           text not null,
    modality_types text
);

CREATE TABLE qms_sound
(
    id            serial primary key,
    ticket_id     integer not null,
    modality_id   integer not null,
    modality_room text,
    speaker_id    integer not null,
    ticket_number varchar(8),
    patient_name  text,
    audio         bytea
);

CREATE TABLE qms_speaker
(
    id   integer primary key,
    name text not null
);

CREATE TABLE qms_ticket_counter
(
    id                varchar(10) primary key,
    current_number    integer NOT NULL default 0,
    last_updated_date timestamp with time zone
);

CREATE TABLE qms_modality
(
    id                   integer primary key,
    site_id              integer     NOT NULL references qms_site (id),
    speaker_id           integer,              -- references qms_speaker (id)
    name                 text        not null,
    code                 varchar(32) not null, -- HIS code
    room_name            text,
    room_name_audio      bytea,
    modality_type        varchar(8),
    other_modality_types text,
    enabled              boolean default true
);

create table qms_modality_type
(
    id varchar(10) primary key
);

CREATE TABLE qms_modality_counter
(
    id                integer primary key, /
    /
    reference
    to
    qms_modality
    current_number
    integer
    NOT
    NULL
    default
    0,
    last_updated_date timestamp with time zone
);

CREATE TABLE qms_procedure
(
    id            serial primary key,
    name          text        not null,
    code          varchar(32) not null,
    modality_type varchar(8)
);

CREATE TABLE qms_modality_procedure
(
    id           serial primary key,
    modality_id  integer NOT NULL references qms_modality (id),
    procedure_id integer NOT NULL references qms_procedure (id)
);

CREATE TABLE qms_ticket
(
    id                  serial primary key,
    modality_id         integer                  NOT NULL references qms_modality (id),
    patient_pid         text,
    patient_name        text,
    patient_name_search text,
    gender              varchar(8),
    birth_date          varchar(10),
    address             text,
    ticket_number       varchar(8),
    status              integer                  NOT NULL default 0,
    created_time        timestamp with time zone not null,
    creator_id          varchar(36)              not null,
    last_updated_time   timestamp with time zone,
    last_updated_id     varchar(36)
);

CREATE TABLE qms_ticket_procedure
(
    id                   serial primary key,
    ticket_id            integer NOT NULL references qms_ticket (id),
    ris_order_id         integer NOT NULL,
    ris_service_id       integer NOT NULL,
    procedure_id         integer,
    procedure_code       varchar(20),
    procedure_name       text,
    ris_accession_number text
);

CREATE UNIQUE INDEX uniq_qms_ticket_procedure_ris_service on qms_ticket_procedure(ris_service_id);


ALTER TABLE qms_modality
ADD COLUMN created_time TIMESTAMP WITH TIME ZONE,
ADD COLUMN creator_name VARCHAR(255),
ADD COLUMN last_updated_time TIMESTAMP WITH TIME ZONE,
ADD COLUMN last_updated_name VARCHAR(255);

