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

/*INSERT INTO users(created_date, edited_date, employee_id, schiphol_id, email, first_name, last_name, role)
VALUES (now(), now(), '516543', 'HEXCODE001', 'jasper.van-der-heijden@delta.com', 'Jasper', 'van der Heijden', 'ADMIN'),
       (now(), now(), '523043', 'HEXCODE002', 'keith.palmer@delta.com', 'Keith', 'Palmer', 'USER'),
       (now(), now(), '750339', 'HEXCODE003', 'barry.heesters@delta.com', 'Barry', 'Heesters', 'USER'),
       (now(), now(), '509704', 'HEXCODE004', 'jan.bakker@delta.com', 'Jan Willem', 'Bakker', 'LEAD');*/

INSERT INTO users(created_date, edited_date,keycloak_id, employee_id, schiphol_id, email, first_name, last_name, role)
VALUES (now(), now(),'d0b1481f-8337-4a38-b656-b96fade1880b' ,'516543', 'HEXCODE001', 'jasper.van-der-heijden@delta.com', 'Jasper', 'van der Heijden', 'ADMIN'),
       (now(), now(),'76901e04-c59a-435f-8d52-763ce4646fb5' ,'523043', 'HEXCODE002', 'keith.palmer@delta.com', 'Keith', 'Palmer', 'USER'),
       (now(), now(),'3' ,'750339', 'HEXCODE003', 'barry.heesters@delta.com', 'Barry', 'Heesters', 'USER'),
       (now(), now(),'91c38d96-a1aa-4be8-82f5-a691a56c6c55' ,'509704', 'HEXCODE004', 'jan.bakker@delta.com', 'Jan Willem', 'Bakker', 'LEAD');

INSERT INTO storage_locations(created_date, edited_date, location, rack, shelf, comments)
VALUES (now(), now(), 'G4', 'A' ,'1', NULL),
       (now(), now(), 'G4', 'CHM' ,'3', 'Oil-Racking'),
       (now(), now(), 'D49', 'B12' ,'D01-A01', NULL),
       (now(), now(), 'G4', 'B' ,'1', NULL),
       (now(), now(), 'G4', 'B' ,'2', NULL),
       (now(), now(), 'G4', 'B' ,'3', NULL),
       (now(), now(), 'G4', 'B' ,'4', NULL),
       (now(), now(), 'G4', 'C' ,'1', NULL),
       (now(), now(), 'G4', 'C' ,'2', NULL),
       (now(), now(), 'G4', 'C' ,'3', NULL),
       (now(), now(), 'G4', 'C' ,'4', NULL),
       (now(), now(), 'G4', 'D' ,'1', NULL),
       (now(), now(), 'G4', 'D' ,'2', NULL),
       (now(), now(), 'G4', 'D' ,'3', NULL),
       (now(), now(), 'G4', 'D' ,'4', NULL);

INSERT INTO tools (created_date, edited_date, equipment_type, item_id, name, picture_file_name, storage_location_id, status, checked_out_by_user_id, checked_out_date, inspection, comments, tool_type, ata_code, part_number, serial_number,manufacturer, is_calibrated, tool_kit_id)
VALUES  (now(), now(), 'TOOL', 'R0232132', 'Precision Torque Wrench 10-100 Nm', 'R0232132.pdf', 1, 'IN_CALIBRATION',  4,      now(),  TRUE,   NULL,             'Torque Wrench',    3212, '50-590013-1', 'TW-123456789', 'Snap-on',   TRUE,   NULL),
        (now(), now(), 'TOOL', 'R0454545', 'Metric Socket Set 6-24mm',          NULL, 3, 'CHECKED_IN',      NULL,   NULL,   FALSE,  '10mm missing',   'Socket Set',       2811, 'AS28431',     'SS-987654321', 'Proto',     FALSE,  NULL),
        (now(), now(), 'TOOL', 'TL003',    'Feeler Gauge Set 0.05-1.00mm',      NULL, 4, 'CHECKED_IN',      NULL,   NULL,   TRUE,   NULL,             'Feeler Gauges',    2796, 'P23499',      'FG-456789123', 'Starrett',  TRUE,   NULL),
        (now(), now(), 'TOOL', 'SPARK01',  'Spark Plug Socket 16mm',            NULL, 2, 'CHECKED_OUT',     2,      now(),  FALSE,  NULL,             'Sparkplug Socker', 7325, 'T8438',       'SPS-112233445','Craft',     FALSE,  NULL);

INSERT INTO aircraft_type_tool_compatibility (tool_id, aircraft_type_id) VALUES (1, 1),(2, 2),(3, 3);
INSERT INTO engine_type_tool_compatibility (tool_id, engine_type_id)     VALUES (1, 1),(2, 2),(3, 3);

INSERT INTO tool_kits (created_date, edited_date, equipment_type, item_id, name, picture_file_name ,storage_location_id, status, checked_out_by_user_id, checked_out_date, inspection, comments, tool_kit_type, ata_code, part_number, serial_number, manufacturer, is_calibrated)
VALUES  (now(), now(), 'TOOLKIT', 'K03562141', 'A330 NLG Gland Nut kit',        NULL,5,'MISSING',       3,    now(), FALSE, 'ToolKit Missing','C-Spanner Kit',      2000, 'MAG003451',      'DL00158',          'Magema',           FALSE),
        (now(), now(), 'TOOLKIT', 'K06892146', 'Fluke 1587 Kit',                NULL,6,'CHECKED_IN',    NULL, NULL,  TRUE,  NULL,             'MultiMeter Kit',     2400, 'FK1587-kit',     'fk38FSD-345G',     'Fluke',            TRUE),
        (now(), now(), 'TOOLKIT', 'Boro001',   'GE IQ Mentor 3D Boroscope',     'Boro001.png',7,'CHECKED_IN',    NULL, NULL,  TRUE,  NULL,             'Boroscope Kit',      7200, 'MIQ-3D',         '52-98-88745',      'General Electric', TRUE),
        (now(), now(), 'TOOLKIT', 'RR57698',   'XWB HMU AGB STUD Extractor Kit',NULL,8,'UNSERVICEABLE', NULL, NULL,  FALSE, NULL,             'HeliCoil Repair Kit',7328, 'RR689-95-17-14', '00018684001477',   'Rolls Royce',      FALSE);

INSERT INTO aircraft_type_kit_compatibility (toolkit_id, aircraft_type_id) VALUES (1, 1),(2, 2),(3, 3);
INSERT INTO engine_type_kit_compatibility (toolkit_id, engine_type_id)     VALUES (1, 1),(2, 2),(3, 3);
UPDATE tools SET tool_kit_id = 4 WHERE id = 3;

INSERT INTO inspections (created_date, edited_date, inspection_date, inspection_type, inspection_passed, comments, next_due_date, inspection_interval, tool_id, tool_kit_id)
VALUES  (now(), now(), '2025-12-15 09:00:00', 'CALIBRATION',        TRUE, NULL,                 '2026-12-15 09:00:00', 5000, 1, NULL),
        (now(), now(), '2026-01-20 14:30:00', 'VISUAL_INSPECTION',  TRUE, NULL,                 '2026-04-20 14:30:00', 1250, 3, NULL),
        (now(), now(), '2025-08-10 11:00:00', 'UNKNOWN',            FALSE,'Certificate Missing','2026-02-10 11:00:00', 2500, NULL, 2),
        (now(), now(), '2026-02-01 16:00:00', 'SERVICE_INSPECTION', TRUE, NULL,                 '2027-02-01 16:00:00', 4000, NULL, 3);

INSERT INTO tool_log(time_Stamp, action_type, action_result ,action_user, item_number, item_type, item_name, ata_code, part_number, serial_number, manufacturer, aircraft_number ,comments)
VALUES (now(), 'CHECK_OUT', 'CHECKED_OUT', '516543', 'K03562141',  'TOOLKIT', 'A330 NLG Gland Nut kit',         2000, 'MAG003451',  'DL00158',      'Magema',   '3311', NULL),
       (now(), 'CHECK_IN',  'CHECKED_IN',  '523043', 'R0454545',   'TOOL',    'Metric Socket Set 6-24mm',       2811, 'AS28431',    'SS-987654321', 'Proto',    '3310', NULL),
       (now(), 'CHECK_OUT', 'CHECKED_OUT', '516543', 'SPARK01',    'TOOL',    'Spark Plug Socket 16mm',         7325, 'T8438',      'SPS-112233445','Craft',    '8AB',  NULL),
       (now(), 'CHECK_OUT', 'CHECKED_OUT', '509704', 'RR57698',    'TOOLKIT', 'XWB HMU AGB STUD Extractor Kit', 2796, 'P23499',     'FG-456789123', 'Starrett', '3527', 'calibration');

