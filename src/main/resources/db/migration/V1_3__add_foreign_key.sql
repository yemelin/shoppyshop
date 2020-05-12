ALTER TABLE category ADD PRIMARY KEY (id);
ALTER TABLE product ADD PRIMARY KEY (id);
alter table product
    ADD category_id integer REFERENCES category (id);