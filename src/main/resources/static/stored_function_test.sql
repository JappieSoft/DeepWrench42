CREATE OR REPLACE FUNCTION sp_update_genre(
    p_id BIGINT,
    p_name VARCHAR,
    p_description VARCHAR
)
RETURNS BIGINT AS $$
DECLARE rows_affected INT;

BEGIN
UPDATE genre SET name = p_name, description = p_description WHERE id = p_id;

GET DIAGNOSTICS rows_affected = ROW_COUNT;  --check if rows were updated

IF rows_affected > 0 THEN
        RETURN p_id;   -- updated successfully
ELSE
        RETURN 0;      -- no row updated
END IF;
END;

$$ LANGUAGE plpgsql;