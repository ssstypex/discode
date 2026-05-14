CREATE TABLE discount_codes(
    id UUID NOT NULL DEFAULT gen_random_uuid(),
    code VARCHAR(50) UNIQUE NOT NULL,
    country_code VARCHAR(2) NOT NULL,
    max_usages INT NOT NULL,
    current_usages INT NOT NULL DEFAULT 0,
    last_usage TIMESTAMP,
    created_at TIMESTAMP NOT NULL
)