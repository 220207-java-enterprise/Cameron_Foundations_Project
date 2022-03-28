create table User_roles(
role_id VARCHAR primary key,
role Varchar unique
);


create table Users(
user_id  varchar primary key,
username varchar unique not null,
email varchar unique not null,
password varchar not null, 
given_name varchar not null,
surname varchar not null, 
is_active boolean,
role_id varchar,
foreign key (role_id) references User_roles (role_id)
);

create table Reimbursement(
reim_id varchar primary key,
amount numeric(6,2) not null, 
submitted timestamp not null,
resolved timestamp, 
description varchar not null, 
receipt bytea,
payment_id varchar, 
author_id varchar not null, 
resolver_id varchar,
status_id varchar not null, 
type_id varchar not null, 

foreign key (author_id) references Users (user_id),
foreign key (resolver_id) references Users (user_id),
foreign key (status_id) references Reimbursement_status (status_id),
foreign key (type_id) references Reimbursement_type (type_id)
);

create table Reimbursement_type(
type_id varchar primary key,
type varchar unique
); 

create table Reimbursement_status(
status_id varchar primary key, 
status varchar unique
);
