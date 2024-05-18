package com.taha.planner;

import com.taha.planner.model.MeetingType;
import com.taha.planner.model.Reservation;
import com.taha.planner.service.ReservationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PlannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlannerApplication.class, args);
		System.out.println("hi");
	}

}
