FROM maven:3.6.1-jdk-11 as build

WORKDIR /opt

COPY *pom.xml /opt/

COPY . /opt/

RUN mvn package -DskipTests

FROM openjdk:11-jdk-slim

COPY --from=build /opt/target/endpoint-*.jar /opt/app.jar

CMD java -jar /opt/app.jar --spring.profiles.active=prod
