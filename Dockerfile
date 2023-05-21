FROM gradle:7.6.1-jdk17 AS build

COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src

RUN ./gradlew build --no-daemon

FROM openjdk:17

COPY build/libs/ASE-server.jar home/spring/application.jar

EXPOSE 8080

ENV S3_ENDPOINT=http://localhost:4566
ENV S3_FORCE_PATH_STYLE=true

ENTRYPOINT ["java", "-jar", "home/spring/application.jar"]



