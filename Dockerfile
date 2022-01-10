FROM maven:3.8.3-openjdk-16

COPY . /srv/

ARG TOKEN

RUN curl https://betternudel-production.up.railway.app/api/status?status=Deploying%20update...\&token=$TOKEN

ARG BRAIN_SHOP_BID
ARG BRAIN_SHOP_KEY

WORKDIR /srv
RUN mvn package

WORKDIR /srv/target
RUN mkdir plugins

WORKDIR /srv/target/plugins
RUN curl https://github.com/Nudeltruppe/NudelAutoBackup/releases/download/latest/nudel-autobackup.jar -o nudel-autobackup.jar -L
RUN curl https://github.com/Glowman554/BetterNudelAutoBackup/releases/download/latest/better-nudel-autobackup.jar -o better-nudel-autobackup.jar -L
RUN curl https://github.com/Glowman554/Glowman554.github.io/raw/main/WebsiteDownNotify/website-down-notify.jar -o website-down-notify.jar -L
RUN cp /srv/host/js/uwuify.js /srv/target/plugins/uwuify.js
RUN cp /srv/host/js/eval.js /srv/target/plugins/eval.js

WORKDIR /srv/target
RUN java -jar nudel-0.0.1-SNAPSHOT.jar --download-perms=https://betternudel-production.up.railway.app/api/perms
RUN java -jar nudel-0.0.1-SNAPSHOT.jar --download-science=https://betternudel-production.up.railway.app/api/science
RUN java -jar nudel-0.0.1-SNAPSHOT.jar --download-science-v2=https://betternudel-production.up.railway.app/api/science/v2 --token=$TOKEN
RUN java -jar nudel-0.0.1-SNAPSHOT.jar --download-tokens=https://betternudel-production.up.railway.app/api/tokens --token=$TOKEN

ARG HTTP_HOST_PATH

RUN java -jar nudel-0.0.1-SNAPSHOT.jar --load-host --token=$TOKEN --no-cfg

ENTRYPOINT ["java", "-jar", "nudel-0.0.1-SNAPSHOT.jar", "--no-cfg", "--tiny-crash", "--allow-unsafe-exec"]
