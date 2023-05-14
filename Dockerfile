FROM gradle:7.6.1-jdk17 AS build

COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src

RUN ./gradlew build --no-daemon

FROM openjdk:17

COPY build/libs/ASE.jar home/spring/application.jar

EXPOSE 8080

ENV AWS_ACCESS_KEY_ID=AKIAX3J2CPTK5L2ENODJ
ENV AWS_SECRET_ACCESS_KEY=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
ENV AWS_DEFAULT_REGION=us-east-1

ENV S3_ENDPOINT=http://localhost:4566
ENV S3_FORCE_PATH_STYLE=true

ENTRYPOINT ["java", "-jar", "home/spring/application.jar"]

