package com.taha.planner.integration;

import com.taha.planner.model.Equipment;
import com.taha.planner.model.Reservation;
import com.taha.planner.model.Room;
import com.taha.planner.repository.EquipmentRepository;
import com.taha.planner.repository.ReservationRepository;
import com.taha.planner.repository.RoomRepository;
import com.taha.planner.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    private Room room;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @BeforeEach
    void setUp() {
        reservationRepository.deleteAll();
        roomRepository.deleteAll();
        equipmentRepository.deleteAll();

        startTime = LocalDateTime.now().plusDays(1);
        endTime = startTime.plusHours(2);

        Equipment eq1 = new Equipment("Pieuvre");
        Equipment eq2 = new Equipment("Screen");
        Equipment eq3 = new Equipment("Webcam");
        equipmentRepository.save(eq1);
        equipmentRepository.save(eq2);
        equipmentRepository.save(eq3);

        room = new Room();
        room.setCapacity(10);
        room.setEquipment(List.of(eq1, eq2, eq3));
        room.setName("Salle 1001");
        room = roomRepository.save(room);
    }

    @Test
    void testReserveRoom() {
        webTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/reservations/reserve")
                        .queryParam("startTime", startTime.toString())
                        .queryParam("endTime", endTime.toString())
                        .queryParam("meetingType", "VC")
                        .queryParam("attendees", 1)
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Reservation.class)
                .consumeWith(response -> {
                    Reservation reservation = response.getResponseBody();
                    assertNotNull(reservation);
                    assertEquals(room.getId(), reservation.getRoomId());
                    assertEquals(startTime, reservation.getStart());
                    assertEquals(endTime, reservation.getEnd());
                    assertEquals("VC", reservation.getType());
                    assertEquals(1, reservation.getAttendees());
                });
    }

    @Test
    void testReserveRoom_NoAvailableRoom() {
        webTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/reservations/reserve")
                        .queryParam("startTime", startTime.toString())
                        .queryParam("endTime", endTime.toString())
                        .queryParam("meetingType", "RS")
                        .queryParam("attendees", 1000)
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.message").isEqualTo("No available room found for the given criteria.");
    }
    @Test
    void testReserveRoom_InvalidMeetingType() {
        webTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/reservations/reserve")
                        .queryParam("startTime", startTime.toString())
                        .queryParam("endTime", endTime.toString())
                        .queryParam("meetingType", "INVALID_TYPE")
                        .queryParam("attendees", 1000)
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.message").isEqualTo("Invalid meeting type: INVALID_TYPE");
    }
}
