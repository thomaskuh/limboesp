FROM alpine:3.20
MAINTAINER LimboMedia <admin@limbomedia.net>

RUN apk add --update --no-cache openjdk21-jre-headless

ADD maven/limboesp.jar /limboesp.jar
RUN mkdir data

VOLUME /data

EXPOSE 8080

ENTRYPOINT ["java", "-Ddir=/data", "-jar", "/limboesp.jar"]