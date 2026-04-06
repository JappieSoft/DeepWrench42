INSERT INTO aircraft_types(created_date, edited_date, manufacturer, main_type, sub_type)
VALUES (now(), now(), 'Airbus', 'A330', 'A330-300'),
       (now(), now(), 'Airbus', 'A350', 'A350-900'),
       (now(), now(), 'Boeing', 'B787', 'B787-800'),
       (now(), now(), 'Boeing', 'B737', 'B737-800'),
       (now(), now(), 'Airbus', 'A320', 'A320-200');

INSERT INTO engine_types(created_date, edited_date, manufacturer, main_type, sub_type)
VALUES (now(), now(), 'Pratt & Whitney', 'PW4000', 'PW4168'),
       (now(), now(), 'Rolls Royce', 'XWB', 'XWB-84P'),
       (now(), now(), 'General Electric', 'GenX', 'GenX-1A'),
       (now(), now(), 'CFM', 'CFM56', '-7B26'),
       (now(), now(), 'CFM', 'CFM56', '-5B4'),
       (now(), now(), 'IAE', 'V2500', 'V2525-A5');

INSERT INTO aircraft(created_date, edited_date, ship_number, registration, aircraft_type_id, engine_type_id)
VALUES (now(), now(), '3311', 'N811NW', 1, 1),
       (now(), now(), '3527', 'N527DZ', 2, 2),
       (now(), now(), '8AB', 'N787AB', 3, 3),
       (now(), now(), '3310', 'N801NW', 1, 1);

INSERT INTO tools (created_date, edited_date, equipment_type, item_id, name, picture_file_name, storage_location_id, status, checked_out_by_user_id, checked_out_date, inspection, comments, tool_type, ata_code, part_number, serial_number,manufacturer, is_calibrated, tool_kit_id)
VALUES  (now(), now(), 'TOOL', 'R0232132', 'Precision Torque Wrench 10-100 Nm', NULL, NULL, 'IN_CALIBRATION',  NULL,      now(),  TRUE,   NULL,             'Torque Wrench',    3212, '50-590013-1', 'TW-123456789', 'Snap-on',   TRUE,   NULL),
        (now(), now(), 'TOOL', 'SPARK01',  'Spark Plug Socket 16mm',            NULL, NULL, 'CHECKED_OUT',     NULL,      NULL,  TRUE,  NULL,             'Sparkplug Socker', 7325, 'T8438',       'SPS-112233445','Craft',     FALSE,  NULL);

INSERT INTO tool_kits (created_date, edited_date, equipment_type, item_id, name, picture_file_name ,storage_location_id, status, checked_out_by_user_id, checked_out_date, inspection, comments, tool_kit_type, ata_code, part_number, serial_number, manufacturer, is_calibrated)
VALUES  (now(), now(), 'TOOLKIT', 'K03562141', 'A330 NLG Gland Nut kit',        NULL,NULL,'MISSING',       NULL,    now(), TRUE, 'ToolKit Missing','C-Spanner Kit',      2000, 'MAG003451',      'DL00158',          'Magema',           FALSE),
        (now(), now(), 'TOOLKIT', 'RR57698',   'XWB HMU AGB STUD Extractor Kit',NULL,NULL,'UNSERVICEABLE', NULL, NULL,  TRUE, NULL,             'HeliCoil Repair Kit',7328, 'RR689-95-17-14', '00018684001477',   'Rolls Royce',      FALSE);

UPDATE tools SET tool_kit_id = 1 WHERE id = 1;
UPDATE tools SET tool_kit_id = 2 WHERE id = 2;

INSERT INTO inspections (created_date, edited_date, inspection_date, inspection_type, inspection_passed, comments, next_due_date, inspection_interval, tool_id, tool_kit_id)
VALUES  (now(), now(), '2023-03-03 03:00:00', 'CALIBRATION',        TRUE, NULL,                 '2024-04-04 04:00:00', 5000, 1, NULL),
        (now(), now(), '2024-04-04 04:00:00', 'VISUAL_INSPECTION',  TRUE, NULL,                 '2025-05-05 05:00:00', 1250, 2, NULL),
        (now(), now(), '2025-05-05 05:00:00', 'UNKNOWN',            FALSE,'Certificate Missing','2026-06-06 06:00:00', 2500, NULL, 1),
        (now(), now(), '2026-06-06 06:00:00', 'SERVICE_INSPECTION', TRUE, NULL,                 '2027-07-07 07:00:00', 4000, NULL, 2);
