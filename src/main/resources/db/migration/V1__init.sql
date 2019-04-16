drop sequence hibernate_sequence if exists;
create sequence hibernate_sequence start with 1 increment by 1;
commit;

create table accounts (
                          id bigint generated by default as identity (start with 1),
                          is_ai boolean,
                          email varchar(100) not null,
                          first_name varchar(100) not null,
                          user_id bigint,
                          primary key (id)
);


create table authorities (
                             id bigint generated by default as identity (start with 1),
                             authority varchar(255),
                             username varchar(255),
                             primary key (id)
);


create table card_type_reference (
                                     id bigint not null,
                                     clazz varchar(255),
                                     name varchar(255),
                                     score bigint,
                                     account_id bigint,
                                     scores_id bigint,
                                     game_cards_id bigint,
                                     primary key (id)
);


create table match (
                       id bigint not null,
                       created_at timestamp,
                       player_count integer,
                       seed bigint,
                       state varchar(255),
                       turn_order varchar(255),
                       winner_id bigint,
                       primary key (id)
);


create table match_players (
                               match_entity_id bigint not null,
                               players_id bigint not null
);


create table users (
                       id bigint generated by default as identity (start with 1),
                       enabled boolean,
                       password varchar(60) not null,
                       username varchar(100) not null,
                       primary key (id)
);

commit;

alter table accounts
    add constraint UK_n7ihswpy07ci568w34q0oi8he unique (email);


alter table accounts
    add constraint UK_e6g3mck07aipja6lq3gliqw9v unique (first_name);


alter table users
    add constraint UK_r43af9ap4edm43mmtq01oddj6 unique (username);


alter table accounts
    add constraint FKnjuop33mo69pd79ctplkck40n
        foreign key (user_id)
            references users;


alter table card_type_reference
    add constraint FKg2yomha8g05636ebycrh04og5
        foreign key (account_id)
            references accounts;


alter table card_type_reference
    add constraint FK5ggw652a3bblo5oa1oxi3t2r
        foreign key (scores_id)
            references match;


alter table card_type_reference
    add constraint FKoib3ji9l5cpjsprgbcc1ahqcd
        foreign key (game_cards_id)
            references match;


alter table match
    add constraint FK4jufv82diyba54pqtt90n2d4e
        foreign key (winner_id)
            references accounts;


alter table match_players
    add constraint FKmqdsdj1y8pvetreudysqgvj2g
        foreign key (players_id)
            references accounts;

alter table match_players
    add constraint FKqyk3xeihsu0a5rhy75q3rpwnh
        foreign key (match_entity_id)
            references match;

Commit;

insert
       into
         users
       (id, enabled, password, username)
       values
       (
         default,
         true,
         '$2a$10$UCrtNnihvVBF63xf.Y3rXODwEtrO4W/Pj6UyohY.23HdBfRmc58eK',
         'bob');
Commit;

insert
    into
        accounts
        (id, email, is_ai, first_name, user_id)
    values
        (default, 'Bob@example.com', false, 'Bob', IDENTITY());
Commit;


insert
       into
         users
       (id, enabled, password, username)
       values
       (
         default,
         true,
         '$2a$10$UCrtNnihvVBF63xf.Y3rXODwEtrO4W/Pj6UyohY.23HdBfRmc58eK',
         'shafeen');
Commit;

insert
    into
        accounts
        (id, email, is_ai, first_name, user_id)
    values
        (default, 'shafeen@example.com', false, 'Shafeen', IDENTITY());
Commit;

insert
       into
         users
       (id, enabled, password, username)
       values
       (
         default,
         true,
         '$2a$10$UCrtNnihvVBF63xf.Y3rXODwEtrO4W/Pj6UyohY.23HdBfRmc58eK',
         'derek');
Commit;

insert
    into
        accounts
        (id, email, is_ai, first_name, user_id)
    values
        (default, 'derek@example.com', false, 'Derek', IDENTITY());
Commit;

insert
       into
         users
       (id, enabled, password, username)
       values
       (
         default,
         true,
         '$2a$10$UCrtNnihvVBF63xf.Y3rXODwEtrO4W/Pj6UyohY.23HdBfRmc58eK',
         'Monique');

       Commit;

insert
into
  accounts
(id, email, is_ai, first_name, user_id)
values
(default, 'Monique@example.com', true, 'Monique', IDENTITY());

insert
       into
         users
       (id, enabled, password, username)
       values
       (
         default,
         true,
         '$2a$10$UCrtNnihvVBF63xf.Y3rXODwEtrO4W/Pj6UyohY.23HdBfRmc58eK',
         'Jamal');

Commit;

insert
    into
        accounts
        (id, email, is_ai, first_name, user_id)
    values
        (default, 'Jamal@example.com', true, 'Jamal', IDENTITY());

insert
       into
         users
       (id, enabled, password, username)
       values
       (
         default,
         true,
         '$2a$10$UCrtNnihvVBF63xf.Y3rXODwEtrO4W/Pj6UyohY.23HdBfRmc58eK',
         'Susan');

  Commit;

insert
into
  accounts
(id, email, is_ai, first_name, user_id)
values
(default, 'Susan@example.com', true, 'Susan', IDENTITY());

insert
       into
         users
       (id, enabled, password, username)
       values
       (
         default,
         true,
         '$2a$10$UCrtNnihvVBF63xf.Y3rXODwEtrO4W/Pj6UyohY.23HdBfRmc58eK',
         'Ross');

Commit;

insert
    into
        accounts
        (id, email, is_ai, first_name, user_id)
    values
        (default, 'Ross@example.com', true, 'Ross', IDENTITY());

insert
       into
         users
       (id, enabled, password, username)
       values
       (
         default,
         true,
         '$2a$10$UCrtNnihvVBF63xf.Y3rXODwEtrO4W/Pj6UyohY.23HdBfRmc58eK',
         'James');

  Commit;

insert
into
  accounts
(id, email, is_ai, first_name, user_id)
values
(default, 'James@example.com', true, 'James', IDENTITY());

insert
       into
         users
       (id, enabled, password, username)
       values
       (
         default,
         true,
         '$2a$10$UCrtNnihvVBF63xf.Y3rXODwEtrO4W/Pj6UyohY.23HdBfRmc58eK',
         'Tanisha');

Commit;

insert
    into
        accounts
        (id, email, is_ai, first_name, user_id)
    values
        (default, 'Tanisha@example.com', true, 'Tanisha', IDENTITY());

insert
       into
         users
       (id, enabled, password, username)
       values
       (
         default,
         true,
         '$2a$10$UCrtNnihvVBF63xf.Y3rXODwEtrO4W/Pj6UyohY.23HdBfRmc58eK',
         'Cane');

  Commit;

insert
into
  accounts
(id, email, is_ai, first_name, user_id)
values
(default, 'Cane@example.com', true, 'Cane', IDENTITY());

Commit;

insert
       into
         users
       (id, enabled, password, username)
       values
       (
         default,
         true,
         '$2a$10$UCrtNnihvVBF63xf.Y3rXODwEtrO4W/Pj6UyohY.23HdBfRmc58eK',
         'Abel');

Commit;

insert
    into
        accounts
        (id, email, is_ai, first_name, user_id)
    values
        (default, 'Abel@example.com', true, 'Abel', IDENTITY());

Commit;

insert
       into
         users
       (id, enabled, password, username)
       values
       (
         default,
         true,
         '$2a$10$UCrtNnihvVBF63xf.Y3rXODwEtrO4W/Pj6UyohY.23HdBfRmc58eK',
         'Krishna');

Commit;

insert
    into
        accounts
        (id, email, is_ai, first_name, user_id)
    values
        (default, 'Krishna@example.com', true, 'Krishna', IDENTITY());

insert
       into
         users
       (id, enabled, password, username)
       values
       (
         default,
         true,
         '$2a$10$UCrtNnihvVBF63xf.Y3rXODwEtrO4W/Pj6UyohY.23HdBfRmc58eK',
         'Saanvi');

Commit;

insert
  into
  accounts
  (id, email, is_ai, first_name, user_id)
  values
  (default, 'Saanvi@example.com', true, 'Saanvi', IDENTITY());

insert
       into
         users
       (id, enabled, password, username)
       values
       (
         default,
         true,
         '$2a$10$UCrtNnihvVBF63xf.Y3rXODwEtrO4W/Pj6UyohY.23HdBfRmc58eK',
         'Prishna');

Commit;

insert
  into
  accounts
  (id, email, is_ai, first_name, user_id)
  values
  (default, 'Prishna@example.com', true, 'Prishna', IDENTITY());

Commit;