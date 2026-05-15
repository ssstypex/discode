DELETE FROM discount_code_usages;
DELETE FROM discount_codes;

INSERT INTO discount_codes (id, code, country_code, max_usages, current_usages, last_usage, created_at)
VALUES ('edd643e7-b9f9-4cd1-8ff0-2d5c9acd7c19', 'MAJ30', 'PL', 10, 9, now(), now()),
 ('edd643e7-b9f9-4cd1-8ff0-2d5c9acd7c18', 'MAJ30', 'DE', 10, 9, now(), now()),
 ('edd643e7-b9f9-4cd1-8ff0-2d5c9acd7c17', 'MAJ30MULTI', 'PL', 10, 7, now(), now());