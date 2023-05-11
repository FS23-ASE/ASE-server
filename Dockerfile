FROM openjdk:17

COPY build/libs/ASE.jar home/spring/application.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "home/spring/application.jar"]