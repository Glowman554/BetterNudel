FROM maven:3.8.3-openjdk-16

COPY . /srv

RUN curl https://betternudel-production.up.railway.app/api/status?status=Deploying%20update...

WORKDIR /srv
RUN mvn package

WORKDIR /srv/target
RUN mkdir plugins

WORKDIR /srv/target/plugins
RUN curl https://github.com/Nudeltruppe/NudelAutoBackup/releases/download/latest/nudel-autobackup.jar -o nudel-autobackup.jar -L
RUN curl https://github.com/Glowman554/BetterNudelAutoBackup/releases/download/latest/better-nudel-autobackup.jar -o better-nudel-autobackup.jar -L
RUN curl https://github.com/Glowman554/Glowman554.github.io/raw/main/WebsiteDownNotify/website-down-notify.jar -o website-down-notify.jar -L

WORKDIR /srv/target
RUN java -jar nudel-0.0.1-SNAPSHOT.jar --download-perms=https://betternudel-production.up.railway.app/api/perms
RUN java -jar nudel-0.0.1-SNAPSHOT.jar --download-science=https://betternudel-production.up.railway.app/api/science

ENTRYPOINT ["java", "-jar", "nudel-0.0.1-SNAPSHOT.jar", "--no-cfg", "--tiny-crash", "--allow-unsafe-exec"]
