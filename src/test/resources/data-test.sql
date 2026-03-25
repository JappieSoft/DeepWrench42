INSERT INTO aircraft_types(created_date, edited_date, manufacturer, main_type, sub_type)
VALUES (now(), now(), 'Boeing', 'B737', 'B737-800'),
       (now(), now(), 'Airbus', 'A320', 'A320-200');

INSERT INTO engine_types(created_date, edited_date, manufacturer, main_type, sub_type)
VALUES (now(), now(), 'CFM', 'CFM56', '-7B26'),
       (now(), now(), 'CFM', 'CFM56', '-5B4'),
       (now(), now(), 'IAE', 'V2500', 'V2525-A5');

INSERT INTO storage_locations(created_date, edited_date, location, rack, shelf, comments)
VALUES (now(), now(), 'G4', 'A' ,'1', NULL),
       (now(), now(), 'G4', 'CHM' ,'3', 'Oil-Racking'),
       (now(), now(), 'D49', 'B12' ,'D01-A01', NULL);

