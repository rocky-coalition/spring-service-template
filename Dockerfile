FROM gradle:jdk14 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean build test --no-daemon

FROM openjdk:14.0.1-oracle
ENV APP_DIST="spring-service-template"
ENV ENV=""
EXPOSE 8080
COPY --from=build /home/gradle/src/build/libs/${APP_DIST}* /${APP_DIST}.jar

ENTRYPOINT ["java", "-Xmx384m", "-Dspring.profiles.active=${ENV:local}", "-jar", "./${APP_DIST}.jar" ]
