FROM openjdk:11-jre

ENV TZ=Europe/Oslo
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

VOLUME /tmp
ARG JAR_FILE
ENV port 8084
ENV type organization
ENV org 910244132
ENV file.encoding Base64
ADD target/${JAR_FILE} app.jar
RUN sh -c 'touch /app.jar'
CMD java -Denvironment=$ENV -jar $JAVA_OPTS app.jar