FROM gradle:6.5.1-jdk11 as builder

COPY . /src
WORKDIR /src
RUN gradle clean assemble

FROM openjdk:11.0.4-jdk

ENV JAVA_OPTS="-Dspring.profiles.active=docker"
WORKDIR /usr/app
COPY --from=builder /src/build/libs/*.jar TODO-server.jar

EXPOSE 8080

CMD java $JAVA_OPTS -jar TODO-server.jar
