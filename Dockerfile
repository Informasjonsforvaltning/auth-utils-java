FROM openjdk:11-jre


COPY ./target/auth-mock-1.0-SNAPSHOT-jar-with-dependencies.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "auth-mock-1.0-SNAPSHOT-jar-with-dependencies.jar"]