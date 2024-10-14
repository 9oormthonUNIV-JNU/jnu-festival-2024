-- zone
INSERT INTO zone (name, location, description, created_at, modified_at)
VALUES ('Central Stadium', 'STADIUM', 'Main stadium in the city', NOW(), NOW());

INSERT INTO zone (name, location, description, created_at, modified_at)
VALUES ('518 Memorial Square', 'SQUARE_518', 'Historical square dedicated to the May 18 movement', NOW(), NOW());

INSERT INTO zone (name, location, description, created_at, modified_at)
VALUES ('Backgate Street Market', 'BACKGATE_STREET', 'Famous street market with local vendors', NOW(), NOW());

INSERT INTO zone (name, location, description, created_at, modified_at)
VALUES ('City Square', 'SQUARE_518', 'Popular gathering spot in the city center', NOW(), NOW());

INSERT INTO zone (name, location, description, created_at, modified_at)
VALUES ('East Stadium', 'STADIUM', 'Newly built stadium on the east side of the city', NOW(), NOW());

-- partner
INSERT INTO partner (name, location, start_date, end_date, description, created_at, modified_at)
VALUES ('Partner A', 'New York', '2024-01-01', '2024-12-31', 'Leading partner in New York', NOW(), NOW());

INSERT INTO partner (name, location, start_date, end_date, description, created_at, modified_at)
VALUES ('Partner B', 'San Francisco', '2023-05-01', '2024-04-30', 'Main partner in San Francisco', NOW(), NOW());

INSERT INTO partner (name, location, start_date, end_date, description, created_at, modified_at)
VALUES ('Partner C', 'London', '2024-02-01', '2024-08-01', 'Top partner in London', NOW(), NOW());

INSERT INTO partner (name, location, start_date, end_date, description, created_at, modified_at)
VALUES ('Partner D', 'Berlin', '2023-07-15', '2024-07-14', 'Primary partner in Berlin', NOW(), NOW());

INSERT INTO partner (name, location, start_date, end_date, description, created_at, modified_at)
VALUES ('Partner E', 'Tokyo', '2024-03-01', '2025-02-28', 'Key partner in Tokyo', NOW(), NOW());

-- partner_image
INSERT INTO partner_image (partner_id, url)
VALUES (1, 'https://example.com/images/partnerA1.jpg');

INSERT INTO partner_image (partner_id, url)
VALUES (2, 'https://example.com/images/partnerB1.jpg');

INSERT INTO partner_image (partner_id, url)
VALUES (3, 'https://example.com/images/partnerC1.jpg');

INSERT INTO partner_image (partner_id, url)
VALUES (4, 'https://example.com/images/partnerD1.jpg');

INSERT INTO partner_image (partner_id, url)
VALUES (5, 'https://example.com/images/partnerE1.jpg');

-- content
INSERT INTO content (title, description, created_at, modified_at)
VALUES ('Content 1', 'Description for content 1', '2024-10-10 10:00:00', '2024-10-10 10:00:00');

INSERT INTO content (title, description, created_at, modified_at)
VALUES ('Content 2', 'Description for content 2', '2024-10-10 10:05:00', '2024-10-10 10:05:00');

INSERT INTO content (title, description, created_at, modified_at)
VALUES ('Content 3', 'Description for content 3', '2024-10-10 10:10:00', '2024-10-10 10:10:00');

INSERT INTO content (title, description, created_at, modified_at)
VALUES ('Content 4', 'Description for content 4', '2024-10-10 10:15:00', '2024-10-10 10:15:00');

INSERT INTO content (title, description, created_at, modified_at)
VALUES ('Content 5', 'Description for content 5', '2024-10-10 10:20:00', '2024-10-10 10:20:00');

-- content_image
INSERT INTO content_image (content_id, url)
VALUES (1, 'http://example.com/images/content1_img1.jpg');

INSERT INTO content_image (content_id, url)
VALUES (1, 'http://example.com/images/content1_img2.jpg');

INSERT INTO content_image (content_id, url)
VALUES (2, 'http://example.com/images/content2_img1.jpg');

INSERT INTO content_image (content_id, url)
VALUES (3, 'http://example.com/images/content3_img1.jpg');

INSERT INTO content_image (content_id, url)
VALUES (4, 'http://example.com/images/content4_img1.jpg');