# Postal Service

This project exposes a small Spring Boot **WebFlux** application for querying and modifying postal details.

## Features
- Retrieve postal records by city or area
- Filter by both city and area
- Insert, update and delete postal details
- API documentation available through Swagger OpenAPI

## Building
Requires Maven and JDK 8.

```bash
mvn package
```

Unit tests can be executed with:

```bash
mvn test
```

## Running
The application starts a web server on port `8080`:

```bash
java -jar target/postal-service-1.0-SNAPSHOT.jar
```

A `Dockerfile` is provided to build a container image:

```bash
docker build -t postal-service .
```

## Endpoints
- `GET /postal/city/{city}` – fetch records for a city
- `GET /postal/area/{area}` – fetch records for an area
- `GET /postal/filter?area={area}&city={city}` – filter by city and area
- `POST /postal/insert` – insert new postal details
- `PUT /postal/update` – update existing postal details
- `DELETE /postal/delete` – delete postal details by id

The default database connection properties can be found in `src/main/resources/application.properties`.
