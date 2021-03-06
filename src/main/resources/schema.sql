create table account (id bigint generated by default as identity, amount integer check (amount>=0), user_name varchar(255), primary key (id))
create table movement (id bigint generated by default as identity, concept varchar(255) not null, date_field date, quantity integer check (quantity>=1), type varchar(255), account_id bigint, primary key (id))
create table user (username varchar(255) not null, password varchar(255), primary key (username))
alter table account add constraint fk_user_user_name foreign key (user_name) references user
alter table movement add constraint fk_account_account_id foreign key (account_id) references account


