## Modulo
This application is a multi-module application intended to demonstrate the use,
effectiveness, and maturity of Java Platform Module Support in Spring Boot,
Gradle, Maven, and other open source libraries.

### Building
`make build` - Compiles the application using both Maven and Gradle.

`make docker.build` - Depends on `build`. Creates the docker images for the Maven
and Gradle built versions of the application.

`make docker.run` - Depends on `docker.build`. Uses docker-compose to start a
Postgres database and both versions of the application. The gradle version uses
port 8080 and the maven version uses 8081.

`make docker.jlink` - Depends on `build`. Attepts to create a docker image using
a docker multi-stage build that leverages jlink. This doesn't currently work due to
the presence of multiple automatic modules in the dependency graph.


### API
- `GET modulo/api/user` - returns all users.
- `POST modulo/api/user` - creates a user from a JSON payload.
- `GET modulo/api/user/<id>` - returns a single user.
- `PUT modulo/api/user/<id>` - update the email and/or ip address for a user.
- `GET modulo/api/user/<id>/location` - returns location data on the given user
(based on ip address).
- `DELETE modulo/api/user/<id>` - deletes a user (sets visibility to false).

JSON (for POST)
```
{
    "username": "username",
    "email": "email@example.com",
    "ip": "216.115.122.132"
}
```

The username and password is currently hardcoded in the security module as `user`
and `password`.

### Structure
- `application` - aggregates and configures the modules into a deployable structure. Depends on all other modules.
- `core` - domain logic.
- `downstream` - client API access to downstream services. Depends on `core`.
- `persistence` - data access implementation. Depends on `core`.
- `rest` - exposes the Rest API. Depends on `core` and `persistence`.
- `security` - simple and insecure security configuration.

### Code Analysis
```
docker run -d --name sonarqube -p 9000:9000 -p 9092:9092 sonarqube
./gradlew test sonarqube
```
