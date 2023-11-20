
create table application_log (
    id bigint not null auto_increment primary key,
    log_time timestamp not null,
    request_id varchar(36),
    log_level varchar(10) not null,
    logger varchar(256) not null,
    message text,
    stacktrace text
);
create index application_log_time_idx on application_log (log_time);
