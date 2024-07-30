CREATE TABLE events (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255),
                        event_date DATE,
                        start_time TIME,
                        end_time TIME,
                        description TEXT,
                        location VARCHAR(255),
                        price DECIMAL(10, 2),
                        category VARCHAR(50),
                        organizer VARCHAR(255),
                        capacity INT,
                        ticket_availability BOOLEAN,
                        address VARCHAR(255),
                        active BOOLEAN
);
INSERT INTO events (name, event_date, start_time, end_time, description, location, price, category, organizer, capacity, ticket_availability, address, active)
VALUES
    ('Summer Music Fest', '2024-08-01', '12:00:00', '15:00:00', 'A vibrant music festival featuring local bands.', 'Central Park', 25.00, 'FESTIVAL', 'MusicOrg', 500, true, '123 Park Ave, Cityville', true),
    ('Art Exhibition', '2024-08-02', '14:00:00', '17:00:00', 'An exhibition showcasing contemporary art.', 'City Gallery', 30.00, 'EXHIBITION', 'ArtOrg', 150, true, '456 Art St, Cityville', true),
    ('Football Match', '2024-08-03', '16:00:00', '19:00:00', 'An exciting football match between top teams.', 'Sports Complex', 40.00, 'FUTBOL', 'SportsOrg', 200, true, '789 Sports Rd, Cityville', true),
    ('Theater Performance', '2024-08-04', '18:00:00', '21:00:00', 'A dramatic theater performance by renowned actors.', 'Downtown Theater', 50.00, 'THEATER', 'TheaterOrg', 300, true, '101 Theater Ln, Cityville', true),
    ('Jazz Night', '2024-08-05', '20:00:00', '23:00:00', 'An evening of smooth jazz music in a cozy setting.', 'Jazz Club', 35.00, 'CONCERT', 'JazzOrg', 100, true, '202 Jazz St, Cityville', true),
    ('Food Festival', '2024-08-06', '12:00:00', '16:00:00', 'A festival featuring a variety of delicious foods.', 'Food Park', 20.00, 'FESTIVAL', 'FoodOrg', 400, true, '303 Food Rd, Cityville', true),
    ('Science Conference', '2024-08-07', '10:00:00', '14:00:00', 'A conference showcasing innovative science projects.', 'Science Center', 15.00, 'CONFERENCE', 'ScienceOrg', 250, true, '404 Science Ave, Cityville', true),
    ('Comedy Show', '2024-08-08', '19:00:00', '22:00:00', 'A night of laughs with top comedians.', 'Comedy Club', 45.00, 'CONCERT', 'ComedyOrg', 200, true, '505 Comedy Ln, Cityville', true),
    ('Book Signing Event', '2024-08-09', '13:00:00', '16:00:00', 'Launch event for a new book by a popular author.', 'Bookstore', 25.00, 'BOOK_SIGNING', 'BookOrg', 150, true, '606 Book St, Cityville', true),
    ('Creative Workshop', '2024-08-10', '17:00:00', '20:00:00', 'A workshop to unleash your creativity with hands-on activities.', 'Community Center', 30.00, 'WORKSHOP', 'WorkshopOrg', 100, true, '707 Workshop Rd, Cityville', true);
