INSERT INTO items (name, quantity)
    VALUES ('T-Shirt', 50), ('Dress', 40), ('Jacket', 20), ('Jean', 100), ('Hat', 50);

INSERT INTO accounts (username, password)
    VALUES ('bo', '123'), ('nick', '123'), ('will', 123), ('david', 123);

INSERT INTO accounts_items ("user_id", "item_id")
    VALUES (1, 1), (1, 4), (1, 5), (2, 1), (2, 5), (3, 2);