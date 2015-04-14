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
  u_u_id                    bigint,
  answer_u_id               bigint,
  constraint pk_question primary key (q_id))
;

create table user (
  u_id                      bigint auto_increment not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  address                   varchar(255),
  constraint pk_user primary key (u_id))
;

alter table answer add constraint fk_answer_q_1 foreign key (q_q_id) references question (q_id) on delete restrict on update restrict;
create index ix_answer_q_1 on answer (q_q_id);
alter table answer add constraint fk_answer_u_2 foreign key (u_u_id) references user (u_id) on delete restrict on update restrict;
create index ix_answer_u_2 on answer (u_u_id);
alter table question add constraint fk_question_u_3 foreign key (u_u_id) references user (u_id) on delete restrict on update restrict;
create index ix_question_u_3 on question (u_u_id);
alter table question add constraint fk_question_answer_4 foreign key (answer_u_id) references user (u_id) on delete restrict on update restrict;
create index ix_question_answer_4 on question (answer_u_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table answer;

drop table person;

drop table question;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

