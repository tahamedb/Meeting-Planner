INSERT INTO Equipment (name) VALUES
                                 ('Screen'),
                                 ('Pieuvre'),
                                 ('Webcam'),
                                 ('Whiteboard');

INSERT INTO Room (name, capacity) VALUES
                                      ('E1001', 23),
                                      ('E1002', 10),
                                      ('E1003', 8),
                                      ('E1004', 4),
                                      ('E2001', 4),
                                      ('E2002', 15),
                                      ('E2003', 7),
                                      ('E2004', 9),
                                      ('E3001', 13),
                                      ('E3002', 8),
                                      ('E3003', 9),
                                      ('E3004', 4);

-- E1001 has no equipment
-- E1002 has Screen
INSERT INTO Room_Equipment (room_id, equipment_id) VALUES (2, 1);

-- E1003 has Pieuvre
INSERT INTO Room_Equipment (room_id, equipment_id) VALUES (3, 2);

-- E1004 has Whiteboard
INSERT INTO Room_Equipment (room_id, equipment_id) VALUES (4, 4);

-- E2001 has no equipment
-- E2002 has Screen and Webcam
INSERT INTO Room_Equipment (room_id, equipment_id) VALUES (6, 1);
INSERT INTO Room_Equipment (room_id, equipment_id) VALUES (6, 3);

-- E2003 has no equipment
-- E2004 has Whiteboard
INSERT INTO Room_Equipment (room_id, equipment_id) VALUES (8, 4);

-- E3001 has Screen, Pieuvre, and Webcam
INSERT INTO Room_Equipment (room_id, equipment_id) VALUES (9, 1);
INSERT INTO Room_Equipment (room_id, equipment_id) VALUES (9, 2);
INSERT INTO Room_Equipment (room_id, equipment_id) VALUES (9, 3);

-- E3002 has no equipment
-- E3003 has Screen and Pieuvre
INSERT INTO Room_Equipment (room_id, equipment_id) VALUES (11, 1);
INSERT INTO Room_Equipment (room_id, equipment_id) VALUES (11, 2);

-- E3004 has no equipment

