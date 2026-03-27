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