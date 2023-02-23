create table if not exists category
(
    id    bigint auto_increment primary key,
    title varchar(255)
);

create table if not exists product
(
    id            bigint auto_increment primary key,
    name_of_photo varchar(255),
    price         double,
    title         varchar(255),
    category_id   bigint,
    constraint category_id_fk foreign key (category_id) references category (id)
);

create table if not exists user
(
    id       bigint auto_increment primary key,
    login    varchar(255),
    password varchar(255),
    role     varchar(255)
);

create table if not exists bucket
(
    id      bigint auto_increment primary key,
    user_id bigint,
    constraint user_id_fk foreign key (user_id) references user (id)
);

create table if not exists bucket_products
(
    bucket_id  bigint,
    product_id bigint,
    constraint product_id_fk foreign key (product_id) references product (id),
    constraint bucket_id_fk foreign key (bucket_id) references bucket (id)
);

create table if not exists orders
(
    id      bigint auto_increment primary key,
    changed datetime(6),
    created datetime(6),
    sum     decimal(19, 2),
    user_id bigint,
    constraint user_order_id_fk foreign key (user_id) references user (id)
);

create table if not exists orders_details
(
    id         bigint auto_increment primary key,
    amount     bigint,
    price      decimal(19, 2),
    order_id   bigint,
    product_id bigint,
    constraint orders_id_fk foreign key (order_id) references orders (id),
    constraint product_order_id_fk foreign key (product_id) references product (id)
);