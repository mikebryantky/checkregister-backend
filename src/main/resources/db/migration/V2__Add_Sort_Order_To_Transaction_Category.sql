alter table transaction_category
    add active_ind boolean default true not null;

alter table transaction_category
    add sort_order int default 1 not null;
