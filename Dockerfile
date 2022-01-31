FROM debian:stable

RUN apt update
RUN apt install -y openjdk-11-jdk maven nodejs npm

ARG REDISHOST
ARG REDISPORT
ARG REDISPASSWORD
ARG REDISUSER

COPY . /srv/

WORKDIR /srv/webinterface
RUN npm install

WORKDIR /srv/
RUN mkdir -p tmp
RUN mvn clean package

ENTRYPOINT ["java", "-jar", "/srv/target/BetterNudel-1.0-SNAPSHOT-REWRITE.jar"]