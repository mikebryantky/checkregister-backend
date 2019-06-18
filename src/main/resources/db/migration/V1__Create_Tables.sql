/*
 * transaction_method
 */
create table transaction_method
(
    uuid        char(36)     not null primary key,
    description varchar(150) not null,

    constraint transaction_method_description_uindex
        unique (description)
);

INSERT INTO transaction_method (uuid, description)
VALUES ('b71b33bf-6326-4ad5-88d9-e72a86f34ef9', 'Cash');
INSERT INTO transaction_method (uuid, description)
VALUES ('2ad14e85-e69d-4530-bccf-53b784012b95', 'Check');
INSERT INTO transaction_method (uuid, description)
VALUES ('0b7ca8ec-5162-4ba9-8e7d-0078148d4048', 'Direct Deposit');
INSERT INTO transaction_method (uuid, description)
VALUES ('7867f332-c140-48cb-9d56-808de12f20e8', 'EFT');
INSERT INTO transaction_method (uuid, description)
VALUES ('af90b46d-7efb-4326-9f76-56c2efbf030f', 'Transfer');


/*
 * transaction_type
 */
create table transaction_type
(
    uuid        char(36)     not null primary key,
    description varchar(150) not null,
    color       char(7)      null,

    constraint transaction_type_description_uindex
        unique (description)
);

INSERT INTO transaction_type (uuid, description, color)
VALUES ('32b1e209-ee18-4340-b00c-b6fa64a80e33', 'Withdrawal', '#ff5733');
INSERT INTO transaction_type (uuid, description, color)
VALUES ('8c8fa709-d826-4613-9fbd-2d9fd6554c1b', 'Deposit', '#33ff80');


/*
 * transaction_category
 */
create table transaction_category
(
    uuid        char(36)     not null primary key,
    description varchar(150) not null,

    constraint category_description_uindex
        unique (description)
);

INSERT INTO transaction_category (uuid, description)
VALUES ('12c91278-92b0-48db-8168-9134a7c8caa6', 'Auto');
INSERT INTO transaction_category (uuid, description)
VALUES ('2d5a62d2-6782-46bc-831a-bf11dd0a5858', 'Misc Expense');
INSERT INTO transaction_category (uuid, description)
VALUES ('61dfa74a-cb41-4971-98da-f9d39946f9e5', 'Credit Card');
INSERT INTO transaction_category (uuid, description)
VALUES ('7c19a7c4-86a7-4e09-9d0f-bd3e2fcda7cd', 'Misc Income');
INSERT INTO transaction_category (uuid, description)
VALUES ('97902475-1f0b-4efe-aed0-e061795f5304', 'Payroll');
INSERT INTO transaction_category (uuid, description)
VALUES ('c252c5f6-d6af-4fdb-859d-391b5bc771dd', 'Utilities');


/*
 * transaction
 */
create table transaction
(
    uuid                      char(36)     not null primary key,
    tx_date                   date         not null,
    description               varchar(150) not null,
    transaction_type_uuid     char(36)     not null,
    check_number              varchar(5)   null,
    transaction_method_uuid   char(36)     not null,
    transaction_category_uuid char(36)     not null,
    amount                    float(7, 2)  null,
    reconciled_date           date         null,
    notes                     text         null,

    constraint transaction__transaction_type_fk
        foreign key (transaction_type_uuid) references transaction_type (uuid),
    constraint transaction_category__fk
        foreign key (transaction_category_uuid) references transaction_category (uuid),
    constraint transaction_transaction_method_uuid_fk
        foreign key (transaction_method_uuid) references transaction_method (uuid)
);

create index transaction__transaction_typeindex
    on transaction (transaction_type_uuid);

create index transaction_category_index
    on transaction (transaction_category_uuid);

create index transaction_txdate_index
    on transaction (tx_date);

create index transaction_amount_index
    on transaction (amount);


