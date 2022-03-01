# MIMUW-DB-project

Simple game server application with the database support.

# VisionDoc

## Functionality

User is able to log as or register new player on the platform. Player, when logged can:
* display rankings calculated based on players' performances (for each game available on the server),
* browse players' profiles, where he can find information about their ranks, stats, performances and match history.
* modify data, including: adding new games and creating custom rankings calculated based upon custom formula (only when logged as the `admin` user - the password is corresponding),
* simulate playing a match with other players (TODO),
* close his own account on the server.

## Technology

* Database - [MySQL](https://www.mysql.com/)
* Application Logic - Java language with frameworks: [Spring](https://spring.io/), [Hibernate](https://hibernate.org/)
* Pages - HTML, CSS, JS, [Bootstrap](https://getbootstrap.com/), [Thymeleaf](https://www.thymeleaf.org/)

## Data sources

For now, data is randomly generated, but I am planning to add support for fetching real-time data (probably by using some API) in the future - have to find some spare time, though.