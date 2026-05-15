ALTER TABLE discount_codes DROP CONSTRAINT discount_codes_code_key;
ALTER TABLE discount_codes ADD CONSTRAINT discount_codes_code_country_unique UNIQUE (code, country_code);