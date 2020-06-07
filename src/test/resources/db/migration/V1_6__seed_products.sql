INSERT INTO product (name, price, category_id) VALUES
    ('pen', 10, (SELECT id FROM category WHERE name = 'test category 1')),
    ('pencil', 7, (SELECT id FROM category WHERE name = 'test category 1'));