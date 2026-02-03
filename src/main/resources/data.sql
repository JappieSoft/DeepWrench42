INSERT INTO aircraft_types(created_date, edited_date, manufacturer, main_type, sub_type)
VALUES (now(), now(), 'Airbus', 'A330', 'A330-300'),
       (now(), now(), 'Airbus', 'A350', 'A350-900'),
       (now(), now(), 'Boeing', 'B787', 'B787-800');

INSERT INTO engine_types(created_date, edited_date, manufacturer, main_type, sub_type)
VALUES (now(), now(), 'Pratt & Whitney', 'PW4000', 'PW4168'),
       (now(), now(), 'Rolls Royce', 'XWB', 'XWB-84P'),
       (now(), now(), 'General Electric', 'GenX', 'GenX-1A');

INSERT INTO aircraft(created_date, edited_date, ship_number, registration, aircraft_type_id, engine_type_id)
VALUES (now(), now(), '3311', 'N811NW', 1, 1),
       (now(), now(), '3527', 'N527DZ', 2, 2),
       (now(), now(), '8AB', 'N787AB', 3, 3),
       (now(), now(), '3310', 'N801NW', 1, 1);

INSERT INTO users(created_date, edited_date, employee_id, schiphol_id, email, first_name, last_name, role)
VALUES (now(), now(), '516543', 'HEXCODE001', 'jasper.van-der-heijden@delta.com', 'Jasper', 'van der Heijden', 'ADMIN'),
       (now(), now(), '523043', 'HEXCODE002', 'keith.palmer@delta.com', 'Keith', 'Palmer', 'USER'),
       (now(), now(), '750339', 'HEXCODE003', 'barry.heesters@delta.com', 'Barry', 'Heesters', 'USER'),
       (now(), now(), '509704', 'HEXCODE004', 'jan.bakker@delta.com', 'Jan Willem', 'Bakker', 'LEAD');

INSERT INTO storage_locations(created_date, edited_date, location, rack, shelf, comments)
VALUES (now(), now(), 'G4', 'A' ,'1', NULL),
       (now(), now(), 'G4', 'CHM' ,'3', 'Oil-Racking'),
       (now(), now(), 'D49', 'B12' ,'D01-A01', NULL),
       (now(), now(), 'G4', 'B' ,'2', NULL);



INSERT INTO tools (created_date, edited_date, equipment_type, item_id, name, picture ,storage_location_id, status, checked_out_by_user_id, checked_out_date, inspection, inspection_id, comments, type, ata_code, part_number, serial_number, manufacturer, calibrated, toolkit_id)
VALUES  (now(), now(), 'TOOL', 'R0232132', 'Precision Torque Wrench 10-100 Nm', NULL,1,'IN_CALIBRATION',4,    now(), TRUE,  NULL,   NULL,           'Torque Wrench',    3212, '50-590013-1', 'TW-123456789', 'Snap-on', TRUE,  NULL),
        (now(), now(), 'TOOL', 'R0454545', 'Metric Socket Set 6-24mm',          NULL,3,'CHECKED_IN',    NULL, NULL,  FALSE, NULL,   '10mm missing', 'Socket Set',       2811, 'AS28431',     'SS-987654321', 'Proto',   FALSE, NULL),
        (now(), now(), 'TOOL', 'TL003',    'Feeler Gauge Set 0.05-1.00mm',      NULL,4,'CHECKED_IN',    NULL, NULL,  TRUE,  NULL,   NULL,           'Feeler Gauges',    2796, 'P23499',      'FG-456789123', 'Starrett',FALSE, NULL),
        (now(), now(), 'TOOL', 'SPARK01',  'Spark Plug Socket 16mm',            NULL,2,'CHECKED_OUT',   2,    now(), FALSE, NULL,   NULL,           'Sparkplug Socker', 7325, 'T8438',       'SPS-112233445','Craft',   FALSE, NULL);

INSERT INTO aircraft_type_tool_compatibility (tool_id, aircraft_type_id) VALUES (1, 1),(2, 2),(3, 3);
INSERT INTO engine_type_tool_compatibility (tool_id, engine_type_id)     VALUES (1, 1),(2, 2),(3, 3);


INSERT INTO inspections (created_date, edited_date, inspection_date, inspection_type, next_due_date, inspection_interval)
VALUES  (now(), now(), '2025-12-15 09:00:00', 'CALIBRATION', '2026-12-15 09:00:00', 5000),
        (now(), now(), '2026-01-20 14:30:00', 'VISUAL_INSPECTION', '2026-04-20 14:30:00', 1250),
        (now(), now(), '2025-08-10 11:00:00', 'UNKNOWN', '2026-02-10 11:00:00', 2500),
        (now(), now(), '2026-02-01 16:00:00', 'SERVICE_INSPECTION', '2027-02-01 16:00:00', 4000);
        UPDATE tools SET inspection_id = 1 WHERE id = 1;
        UPDATE tools SET inspection_id = 2 WHERE id = 3;

