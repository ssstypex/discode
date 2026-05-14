ALTER TABLE discount_codes ADD PRIMARY KEY (id);

CREATE TABLE discount_code_usages(
      id UUID NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
      discount_code_id UUID NOT NULL REFERENCES discount_codes(id),
      user_id UUID NOT NULL,
      used_at TIMESTAMP NOT NULL DEFAULT now(),
      UNIQUE (discount_code_id, user_id)
);