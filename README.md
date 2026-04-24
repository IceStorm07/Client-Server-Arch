# Client-Server-Arch
Smart Building Monitoring API

## How to Run

1. Clone the repository.
2. Open the project in Apache NetBeans IDE 25.
3. Make sure to build the Maven dependencies download successfully.
4. Run `Main.java`.
5. The API starts at `http://localhost:8080/api/v1`.
6. Use Postman or curl to test the endpoints.

## API Overview

This project is a JAX-RS RESTful API for a Smart Campus system. It manages rooms, sensors, and sensor readings. The API uses Jersey with an embedded Grizzly HTTP server and stores data in memory using Java collections instead of a database.

## Endpoints

- `GET /api/v1` - discovery endpoint
- `GET /api/v1/rooms` - list rooms
- `POST /api/v1/rooms` - create room
- `GET /api/v1/rooms/{roomId}` - get one room
- `DELETE /api/v1/rooms/{roomId}` - delete room
- `GET /api/v1/sensors` - list sensors
- `GET /api/v1/sensors?type=CO2` - filter sensors by type
- `POST /api/v1/sensors` - create sensor
- `GET /api/v1/sensors/{sensorId}/readings` - get readings
- `POST /api/v1/sensors/{sensorId}/readings` - add reading

## Sample curl Commands

### Get all rooms
curl http://localhost:8080/api/v1

### Create a room
curl -X POST http://localhost:8080/api/v1/rooms \
-H "Content-Type: application/json" \
-d "{\"id\":\"LIB-301\",\"name\":\"Library Quiet Study\",\"capacity\":50}"

### Create a Sensor
curl -X POST http://localhost:8080/api/v1/sensors \
-H "Content-Type: application/json" \
-d "{\"id\":\"CO2-001\",\"type\":\"CO2\",\"status\":\"ACTIVE\",\"currentValue\":400.0,\"roomId\":\"LIB-301\"}"

### Filter Sensors
curl http://localhost:8080/api/v1/sensors?type=CO2

### Add a reading
curl -X POST http://localhost:8080/api/v1/sensors/CO2-001/readings \
-H "Content-Type: application/json" \
-d "{\"value\":425.5}"


## Coursework Questions and Answers

### Part 1

**1. Explain the default lifecycle of a JAX-RS resource class.**  
Resource classes are typically requests by default, meaning a new instance is usually created for each incoming request. Because of this, shared in-memory data should not be stored inside normal resource object fields. Instead, shared collections such as maps and lists should be stored in a separate class. In this project, I used a shared in-memory store so the data persists across requests and to reduce race-condition risks.

**2. Why is hypermedia considered a hallmark of advanced RESTful design?**  
Hypermedia helps clients discover available actions and related resources through links returned in responses. This reduces dependence on fixed documentation and makes the API easier to navigate.

### Part 2

**1. What are the implications of returning only room IDs versus full room objects?**  
Returning only IDs reduces response size and saves bandwidth, which is useful when many rooms exist versus returning full objects gives clients more useful information immediately, but increases size and may require more network bandwidth.

**2. Is DELETE idempotent in your implementation?**  
Yes, If the room is successfully deleted, repeating the same DELETE request does not delete it again because the resource no longer exists.

### Part 3

**1. What happens if a client sends a different format instead of JSON?**  
If the POST method uses `@Consumes(MediaType.APPLICATION_JSON)`, JAX-RS expects JSON. If the client sends another media type such as `text/plain` or `application/xml`, it may reject the request because the content type does not match what the method wants.

**2. Why is a query parameter better than putting the type in the path?**  
A query parameter is better for filtering because it keeps the base collection resource the same and simply narrows the result set.

### Part 4

**1. What are the benefits of the sub-resource locator pattern?**  
The sub-resource locator pattern keeps nested resource logic separate. It makes the code easier to read and maintain because sensor reading logic is placed in its own class instead of making one large controller to handle every nested route.

**2. How is data consistency maintained when posting a reading?**  
When a new reading is added, the sensor’s `currentValue` field is updated to match the new reading value. This keeps the sensor summary data consistent with the reading history.
