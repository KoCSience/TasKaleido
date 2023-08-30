create table if not exists accounts
(
    uuid      nchar(36),
    username  nchar(36),
    password  nchar(128),
    name      nchar(36),
    email     nchar(128),
    "PROFILE" nchar(128)
);

-- insert into accounts
-- values ('user', 'user', 'password', 'user', 'user@user', 'user')