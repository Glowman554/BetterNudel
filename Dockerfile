FROM maven:3.8.3-openjdk-16

COPY . /srv

WORKDIR /srv

RUN mvn package

WORKDIR /srv/target

ENTRYPOINT ["java", "-jar", "nudel-0.0.1-SNAPSHOT.jar", "--no-cfg"]