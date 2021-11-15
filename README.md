# Poppulo Lottery

A REST interface to a simple lottery system. The rules of the game are described below.

## Lottery Rules

You may generate a ticket with _n_ lines. A line has 3 numbers chosen randomly which may be 0, 1, or 2.

1. If the sum of the numbers on a line is 2, the result for that line is 10.
2. If all numbers on a line are the same, the result is 5.
3. If both the second and third numbers are different from the first, the result is 1.
4. Otherwise, the result is 0.

## Implementation

Additionally, you may check the results of lines on a ticket. The lines are sorted into outcomes. It is possible to
amend a ticket with _n_ additional lines.

## Endpoints

```shell
/ticket       POST  Create a ticket with n lines
/ticket       GET   Fetch the list of tickets
/ticket/{id}  GET   Fetch an individual ticket
/ticket/{id}  PUT   Amend an individual ticket with n additional lines
```

## Run

The application can be run in development mode using the Spring Boot Maven Plugin

```shell
./mvnw clean compile spring-boot:run
```