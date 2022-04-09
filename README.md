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
    * `EC_TIME_INTERVALS=pt2m:p1d:300;pt5h:p10d:300;pt24h:p30d:300;pt48h:p60d:300;p90d:-:300`

Running in a container:
* `docker build -t endpoint-checker:v1 .`
* `docker run --env EC_TIME_INTERVALS="pt2m:p1d:300;pt5h:p10d:300;pt24h:p30d:300;pt48h:p60d:300;p90d:-:300" --env POOL_SIZE=10 --env EC_SYNC_TIME="120s" --env EC_THREADS=4 --env DB_URL=jdbc:postgresql://<ip>:<port>/<db> --env DB_USER=<db_user> --env DB_PASS=<db_user_pass> -it -d --network="host" endpoint-checker:v1`

### *Note:*
* `EC_TIME_INTERVALS:` <br>
  : - parameter separator <br>
  ; - interval separator <br>
  Parameter positions: "how often to check":"how long the endpoint is down":"page size";... <br> Intervals must be in ascending order "how long the endpoint is down". In the last interval, instead of the "how long the endpoint is down", you can put the sign -. It means to check all remaining endpoints. The date unit is preceded by 'p', and time unit is preceded by 'pt'. <br>
  For example: pt2m:p1d:300;pt5h:p10d:300;pt24h:p30d:300;pt48h:p60d:300;p90d:-:300` <br>
