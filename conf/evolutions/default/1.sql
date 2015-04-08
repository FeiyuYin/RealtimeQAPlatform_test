# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table answer (
  a_id                      bigint not null,
  content                   varchar(255),
  is_best                   boolean,
  likes                     integer,
  views                     integer,
  constraint pk_answer primary key (a_id))
;

create table person (
  p_id                      bigint not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  constraint pk_person primary key (p_id))
;

create table question (
  q_id                      bigint not null,
  title                     varchar(255),
  content                   varchar(255),
  constraint pk_question primary key (q_id))
;

create table user (
  p_id                      bigint not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  constraint pk_user primary key (p_id))
;

create sequence answer_seq;

create sequence person_seq;

create sequence question_seq;

create sequence user_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists answer;

drop table if exists person;

drop table if exists question;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists answer_seq;

drop sequence if exists person_seq;

drop sequence if exists question_seq;

drop sequence if exists user_seq;

