
FROM openjdk:8-jre-alpine

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JHIPSTER_SLEEP=0 \
    JAVA_OPTS=""

RUN adduser -D -s /bin/sh urla
WORKDIR /home/urla

ADD src/main/docker/entrypoint.sh /home/urla/entrypoint.sh
ADD ./target/*.jar /home/urla/app.jar
RUN chmod 755 entrypoint.sh && chown urla:urla /home/urla/entrypoint.sh

RUN apk update && apk add bash

USER urla

ENTRYPOINT ["/home/urla/entrypoint.sh"]

EXPOSE 8081 5701/udp
