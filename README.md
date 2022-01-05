# endpoint-checker

Status of Last Deployment:<br>
<img src="https://github.com/mapofzones/endpoint-checker/workflows/Java%20CI/badge.svg"><br>

## Requirements

Running directly:
* java 11
* maven

Running in a container:
* Docker

## Usage

Running directly:
* `mvn package -DskipTests` or `mvn package`
* `java -jar /opt/app.jar --spring.profiles.active=prod`
* `envs:`
    * `DB_URL=jdbc:postgresql://<ip>:<port>/<db>`
    * `DB_USER=<db_user>`
    * `DB_PASS=<db_user_pass>`
    * `EC_SYNC_TIME="120s"`
    * `EC_THREADS=4`
    * `POOL_SIZE=4`

Running in a container:
* `docker build -t endpoint-checker:v1 .`
* `docker run --env POOL_SIZE=10 --env EC_SYNC_TIME="120s" --env EC_THREADS=4 --env DB_URL=jdbc:postgresql://<ip>:<port>/<db> --env DB_USER=<db_user> --env DB_PASS=<db_user_pass> -it -d --network="host" endpoint-checker:v1`
