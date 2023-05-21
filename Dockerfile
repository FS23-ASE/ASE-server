FROM gradle:7.6.1-jdk17 AS build

COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src

RUN ./gradlew build --stacktrace

FROM openjdk:17

COPY build/libs/ASE.jar home/spring/application.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "home/spring/application.jar"]