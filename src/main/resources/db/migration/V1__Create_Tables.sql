CREATE TABLE IF NOT EXISTS Equipment (
                                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS Room (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    name VARCHAR(255) NOT NULL,
                                    capacity INT NOT NULL
);

CREATE TABLE IF NOT EXISTS Room_Equipment (
                                              room_id BIGINT NOT NULL,
                                              equipment_id BIGINT NOT NULL,
                                              PRIMARY KEY (room_id, equipment_id),
                                              FOREIGN KEY (room_id) REFERENCES Room(id),
                                              FOREIGN KEY (equipment_id) REFERENCES Equipment(id)
);

CREATE TABLE IF NOT EXISTS Reservation (
                                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           room_id BIGINT,
                                           start DATETIME(6),
                                           end DATETIME(6),
                                           type VARCHAR(255),
                                           attendees INT,
                                           FOREIGN KEY (room_id) REFERENCES Room(id)
);
