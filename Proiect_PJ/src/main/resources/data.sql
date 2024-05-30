-- create table utilizatori
-- (
--     id_utilizator int primary key auto_increment,
--     nume          varchar(255),
--     client    varchar(255),
--     parola        varchar(255),
--     rol           varchar(255)
-- );
-- create table masini
-- (
--     nr_inmatriculare      varchar(255) primary key,
--     id_utilizator         int,
--     marca                 varchar(255),
--     model                 varchar(255),
--     culoare               varchar(255),
--     an_fabricatie         int,
--     capacitate_cilindrica int,
--     tip_combustibil       varchar(255),
--     putere                int,
--     cuplu                 int,
--     volum_portbagaj       int,
--     pret double
-- );
insert into utilizatori(nume, client, parola, rol)
values ('Estera Rechesan', 'esty', '$2a$12$CrmGshrCssdSNEI835e4CeqpgiGCdrmEEyEiYRiP/uoWxVSFiV1Ga', 'ROLE_EDITOR');
insert into utilizatori(nume, client, parola, rol)
values ('Crina Onea', 'crina', '$2a$12$cEHy.ZUx4U1Dg1NsvxFTcOgk4TpULnXb9n5ElXW9pr.lhU4FOOTXu', 'ROLE_USER');
insert into masini(nr_inmatriculare, id_utilizator, marca, model, culoare, an_fabricatie, capacitate_cilindrica,
                   tip_combustibil, putere, cuplu, volum_portbagaj, pret)
values ('CJ01ABC', 1, 'Audi', 'A4', 'negru', 2015, 2000, 'benzina', 150, 200, 500, 15000);
insert into masini(nr_inmatriculare, id_utilizator, marca, model, culoare, an_fabricatie, capacitate_cilindrica,
                   tip_combustibil, putere, cuplu, volum_portbagaj, pret)
values ('TM01XYZ', 1, 'BMW', 'X5', 'alb', 2018, 3000, 'motorina', 250, 300, 700, 25000);