FROM carsharing/alpine-oraclejdk8-bash
VOLUME /tmp
ADD aps-0.0.1-SNAPSHOT.jar aps.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/aps.jar"]