FROM maven:3.8.3-openjdk-16

RUN curl https://betternudel-production.up.railway.app/api/status?status=Deploying%20update...

COPY . /srv

WORKDIR /srv

RUN mvn package

WORKDIR /srv/target

RUN mkdir plugins

WORKDIR /srv/target/plugins

RUN curl https://github.com/Nudeltruppe/NudelAutoBackup/releases/download/latest/nudel-autobackup.jar -o nudel-autobackup.jar -L

WORKDIR /srv/target

RUN java -jar nudel-0.0.1-SNAPSHOT.jar --download-perms=https://betternudel-production.up.railway.app/api/perms

ENTRYPOINT ["java", "-jar", "nudel-0.0.1-SNAPSHOT.jar", "--no-cfg", "--tiny-crash", "--allow-unsafe-exec"]
