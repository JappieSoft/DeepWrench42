CREATE OR REPLACE PROCEDURE checkout_equipment(
    p_equipment_item_id TEXT,
    p_employee_number TEXT,
    p_aircraft_num TEXT,
    p_comments TEXT,
    OUT p_log_id BIGINT,
    OUT p_success BOOLEAN,
    OUT p_error_msg TEXT
)
LANGUAGE plpgsql
AS $$
DECLARE
v_id BIGINT;
    v_status TEXT;
    v_due_date TIMESTAMP;
    v_unserviceable BOOLEAN;
BEGIN
    -- Safe convert TEXT ID to BIGINT
BEGIN
        v_id := p_equipment_item_id::BIGINT;
EXCEPTION WHEN OTHERS THEN
        p_success := FALSE;
        p_error_msg := 'Invalid item ID format';
        RETURN;
END;

    -- Check TOOL first
SELECT status, due_date, unserviceable
INTO v_status, v_due_date, v_unserviceable
FROM tools WHERE id = v_id;

IF FOUND THEN
        IF v_status != 'CHECKED_IN' THEN
            p_success := FALSE;
            p_error_msg := CASE
                WHEN v_status = 'CHECKED_OUT' THEN 'already checked out'
                ELSE 'Tool Restricted'
END;
            RETURN;
END IF;
        IF v_unserviceable OR (v_due_date IS NOT NULL AND v_due_date < NOW()) THEN
            p_success := FALSE;
            p_error_msg := 'Invalid state';
            RETURN;
END IF;

        -- Update TOOL
UPDATE tools
SET checked_out_by_user_id = (SELECT id FROM users WHERE employee_number = p_employee_number),
    checked_out_date = NOW()
WHERE id = v_id;

-- Log
INSERT INTO tool_log (action_type, action_by, item_number, item_type, aircraft_number, comments)
VALUES ('CHECK_OUT', p_employee_number,
        p_equipment_item_id, 'TOOL', p_aircraft_num, p_comments)
    RETURNING id INTO p_log_id;

p_success := TRUE;
        RETURN;
END IF;

    -- TOOLKIT branch (symmetric)
SELECT status, due_date INTO v_status, v_due_date
FROM tool_kits WHERE id = v_id;

IF FOUND THEN
        IF v_status != 'CHECKED_IN' THEN
            p_success := FALSE;
            p_error_msg := CASE
                WHEN v_status = 'CHECKED_OUT' THEN 'already checked out'
                ELSE 'Tool Restricted'
END;
            RETURN;
END IF;
        IF v_due_date IS NOT NULL AND v_due_date < NOW() THEN
            p_success := FALSE;
            p_error_msg := 'Invalid state';
            RETURN;
END IF;

        -- Update TOOLKIT (adjust fields if unserviceable differs)
UPDATE tool_kits
SET checked_out_by_user_id = (SELECT id FROM users WHERE employee_number = p_employee_number),
    checked_out_date = NOW()
WHERE id = v_id;

INSERT INTO tool_log (action_type, action_by, item_number, item_type, aircraft_number, comments)
VALUES ('CHECK_OUT', p_employee_number,
        p_equipment_item_id, 'TOOLKIT', p_aircraft_num, p_comments)
    RETURNING id INTO p_log_id;

p_success := TRUE;
        RETURN;
END IF;

    p_success := FALSE;
    p_error_msg := 'Item not found';
END;
$$;