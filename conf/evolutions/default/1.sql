# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table answer (
  a_id                      bigint auto_increment not null,
  content                   varchar(255),
  is_best                   tinyint(1) default 0,
  likes                     integer,
  views                     integer,
  q_q_id                    bigint,
  u_u_id                    bigint,
  constraint pk_answer primary key (a_id))
;

create table category (
  c_id                      bigint auto_increment not null,
  name                      varchar(255),
  follower_number           integer,
  create_time               varchar(255),
  create_date               varchar(255),
  constraint pk_category primary key (c_id))
;

create table notification (
  n_id                      bigint auto_increment not null,
  q_q_id                    bigint,
  u_u_id                    bigint,
  status                    varchar(255),
  create_time               varchar(255),
  create_date               varchar(255),
  type                      integer,
  constraint ck_notification_type check (type in (0,1,2)),
  constraint pk_notification primary key (n_id))
;

create table person (
  p_id                      bigint auto_increment not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  address                   varchar(255),
  constraint pk_person primary key (p_id))
;

create table question (
  q_id                      bigint auto_increment not null,
  title                     varchar(255),
  content                   varchar(255),
  create_time               varchar(255),
  create_date               varchar(255),
  close_time                varchar(255),
  close_date                varchar(255),
  is_open                   tinyint(1) default 0,
  image_urls_string         varchar(255),
  u_u_id                    bigint,
  best_answer_a_id          bigint,
  credit                    integer,
  uuid                      varchar(255),
  constraint pk_question primary key (q_id))
;

create table user (
  u_id                      bigint auto_increment not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  credit                    integer,
  exp                       integer,
  u_mid                     varchar(255),
  constraint pk_user primary key (u_id))
;


create table question_category (
  question_q_id                  bigint not null,
  category_c_id                  bigint not null,
  constraint pk_question_category primary key (question_q_id, category_c_id))
;

create table user_category (
  user_u_id                      bigint not null,
  category_c_id                  bigint not null,
  constraint pk_user_category primary key (user_u_id, category_c_id))
;
alter table answer add constraint fk_answer_q_1 foreign key (q_q_id) references question (q_id) on delete restrict on update restrict;
create index ix_answer_q_1 on answer (q_q_id);
alter table answer add constraint fk_answer_u_2 foreign key (u_u_id) references user (u_id) on delete restrict on update restrict;
create index ix_answer_u_2 on answer (u_u_id);
alter table notification add constraint fk_notification_q_3 foreign key (q_q_id) references question (q_id) on delete restrict on update restrict;
create index ix_notification_q_3 on notification (q_q_id);
alter table notification add constraint fk_notification_u_4 foreign key (u_u_id) references user (u_id) on delete restrict on update restrict;
create index ix_notification_u_4 on notification (u_u_id);
alter table question add constraint fk_question_u_5 foreign key (u_u_id) references user (u_id) on delete restrict on update restrict;
create index ix_question_u_5 on question (u_u_id);
alter table question add constraint fk_question_bestAnswer_6 foreign key (best_answer_a_id) references answer (a_id) on delete restrict on update restrict;
create index ix_question_bestAnswer_6 on question (best_answer_a_id);



alter table question_category add constraint fk_question_category_question_01 foreign key (question_q_id) references question (q_id) on delete restrict on update restrict;

alter table question_category add constraint fk_question_category_category_02 foreign key (category_c_id) references category (c_id) on delete restrict on update restrict;

alter table user_category add constraint fk_user_category_user_01 foreign key (user_u_id) references user (u_id) on delete restrict on update restrict;

alter table user_category add constraint fk_user_category_category_02 foreign key (category_c_id) references category (c_id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table answer;

drop table category;

drop table user_category;

drop table question_category;

drop table notification;

drop table person;

drop table question;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

