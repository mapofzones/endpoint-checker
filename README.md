# endpoint-checker

Status of Last Deployment:<br>
<img src="https://github.com/mapofzones/endpoint-checker/workflows/Java%20CI/badge.svg"><br>

## Requirements

Running directly:
* java 11
* maven

Running directly:
* Docker

## Usage

Running directly:
* `mvn package -DskipTests` or `mvn package`
* `java -jar /opt/app.jar --spring.profiles.active=prod`
* `envs:`
    * `DB_URL=jdbc:postgresql://<ip>:<port>/<db>`
    * `DB_USER=<db_user>`
    * `DB_PASS=<db_user_pass>`

Running in a container:
* `docker build -t endpoint-checker:v1 .`
* `docker run --env DB_URL=jdbc:postgresql://<ip>:<port>/<db> --env DB_USER=<db_user> --env DB_PASS=<db_user_pass> -it -d --network="host" endpoint-checker:v1`