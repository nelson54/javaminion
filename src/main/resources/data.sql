
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