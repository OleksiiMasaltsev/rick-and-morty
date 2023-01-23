-- liquibase formatted sql
-- changeset <user>:<add-external-id-to-movie-character_table>

ALTER TABLE public.movie_character ADD external_id bigint;

-- rollback ALTER TABLE DROP COLUMN external_id;