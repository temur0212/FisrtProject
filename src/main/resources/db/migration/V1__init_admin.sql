INSERT INTO profile (name, username, password, visible, status, created_date, updated_date)
VALUES ('Admin', 'erkinovtemur351@gmail.com',
        '$2a$10$uCMi4mIwRJ8ELyA5xL/2ne0Zk.FExTLYDN99GhCyRB90ewEFOtNuW',
        true, 'ACTIVE', now(), now());

INSERT INTO profile_role (profile_id, roles, created_date, updated_date)
VALUES ((SELECT MAX(id) FROM profile), 'ROLE_ADMIN', now(), now()),
       ((SELECT MAX(id) FROM profile), 'ROLE_USER', now(), now())
