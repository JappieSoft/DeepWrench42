/*temp file for data, needs to be changed for this system !!*/

INSERT INTO genres(created_date, edited_date, name, description)
VALUES (now(), now(), 'Rock', 'The best stuff ever!'),
       (now(), now(), 'Pop', 'This stuff is ok'),
       (now(), now(), 'Jazz', 'The best jazz ever!');

INSERT INTO publishers(created_date, edited_date, name, address, contact_details)
VALUES (now(), now(), 'Bach', 'Vienna', 'Grave #1'),
       (now(), now(), 'Lady Gaga', 'London', 'Instagram: @ladygaga'),
       (now(), now(), 'Louis Armstrong', 'New York', 'Phone: 1-800-LOUIS');

INSERT INTO artists(created_date, edited_date, name, biography)
VALUES (now(), now(), 'Johan Sebastian Bach', 'Johann Sebastian Bach was een Duits componist van barokmuziek, organist, klavecinist, violist, muziekpedagoog en dirigent.'),
       (now(), now(), 'Lady Gaga', 'Stefani Joanne Angelina Germanotta, beter bekend als Lady Gaga, is een Amerikaans zangeres, songwriter, actrice en pianiste. Ze brak in 2009 wereldwijd door met de singles Just Dance en Poker Face, die in veel landen nummer 1-hits werden'),
       (now(), now(), 'Louis Armstrong', 'Louis Daniel Armstrong was een Amerikaans jazztrompettist, zanger, acteur en entertainer. Zijn bijnaam was Satchmo of Satch, een afkorting voor Satchelmouth. Hij was ook bekend als Dippermouth, net als Satchmo een referentie aan zijn grote mond.');

INSERT INTO albums(created_date, edited_date, title, release_year, genre_id, publisher_id)
VALUES (now(), now(), 'The Dark Side of the Moon', 1973 ,1, 1),
       (now(), now(), 'Hold My Hand', 2022 ,2, 2),
       (now(), now(), 'Seven Nation Army', 1997 ,3, 3);

INSERT INTO stock(created_date, edited_date, condition, price, album_id)
VALUES (now(), now(), 'good', '19.35', 1),
       (now(), now(), 'poor', '29.01', 2),
       (now(), now(), 'excellent', '39.95', 3);

INSERT INTO albums_artists(album_id, artist_id)
VALUES (1, 1), (2, 2), (3, 1), (3, 2), (3, 3);