# Meeting Planner

The Meeting Planner is a backend service developed to manage room reservations for a company's meeting rooms. The objective was to create an API in Java that adheres to the given specifications, demonstrating skills in Java development, REST API implementation, and testing.

## Objective

Develop an API in Java to manage the complex room reservations for a company with the following constraints:
- A room has a name and a seating capacity.
- Rooms can be reserved in one-hour slots from 8 AM to 8 PM every day except weekends.
- A room must be free for 1 hour before the next reservation to allow for cleaning.
- A room can only be used to 70% of its normal capacity due to COVID-19 restrictions.
- Different types of meetings require different equipment.

## Meeting Types and Requirements

- **Video Conferences (VC):** Screen, speakerphone, and webcam
- **Case Study Sharing (SPEC):** Whiteboard
- **Simple Meetings (RS):** Room with a capacity greater than 3
- **Coupled Meetings (RC):** Whiteboard, screen, and speakerphone

## Room List and Characteristics

| Room Name | Normal Capacity | Equipment              |
|-----------|-----------------|------------------------|
| E1001     | 23              | None                   |
| E1002     | 10              | Screen                 |
| E1003     | 8               | Speakerphone           |
| E1004     | 4               | Whiteboard             |
| E2001     | 4               | None                   |
| E2002     | 15              | Screen, Webcam         |
| E2003     | 7               | None                   |
| E2004     | 9               | Whiteboard             |
| E3001     | 13              | Screen, Webcam, Speakerphone |
| E3002     | 8               | None                   |
| E3003     | 9               | Screen, Speakerphone   |
| E3004     | 4               | None                   |

## Reservation API

### Create Reservation

```http
POST /api/reservations/reserve?startTime=2024-05-22T16:39:56.188718&endTime=2024-05-22T18:39:56.188718&meetingType=RS&attendees=1
```

## Testing

The Meeting Planner project includes a comprehensive suite of unit and integration tests to ensure the correctness and reliability of the API. The tests cover various scenarios and edge cases to validate the behavior of the application.

To run the tests, use the following command:
```bash
mvn test
````
## Setup Instructions
### Clone the repository:
```agsl
git clone https://github.com/tahamedb/Meeting-Planner.git
cd Meeting-Planner
```
### Install dependencies:
```agsl
mvn install
```

### Configure the database:

   Update the database credentials in src/main/resources/application.properties:

```agsl
spring.datasource.url=jdbc:mysql://localhost:3306/your_db_name
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
```

### Run Flyway migrations:

```mvn flyway:migrate```

### Run the application:
```mvn spring-boot:run```
