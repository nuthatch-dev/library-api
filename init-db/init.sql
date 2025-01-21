-- Создание расширения dblink
CREATE EXTENSION IF NOT EXISTS dblink;

-- Создание пользователя для library database
DO
$$
    DECLARE
        user_name     TEXT := 'library_user';
        user_password TEXT := 'secret_password';
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_catalog.pg_user WHERE pg_user.usename = user_name)
        THEN
            EXECUTE 'CREATE USER ' || quote_ident(user_name) || ' WITH PASSWORD ' || quote_literal(user_password);
        END IF;
    END
$$;

-- Создание library database
-- Предоставление прав для пользователя library_db_user
DO
$$
    DECLARE
        database_name TEXT := 'library_db';
        user_name     TEXT := 'library_user';
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = database_name)
        THEN
            PERFORM dblink_exec('dbname=postgres', 'CREATE DATABASE ' || quote_ident(database_name));
            PERFORM dblink_exec('dbname=postgres',
                                'GRANT SELECT, INSERT, UPDATE, DELETE ON DATABASE ' || quote_ident(database_name) ||
                                ' TO ' || quote_ident(user_name));
        END IF;
    END
$$;
