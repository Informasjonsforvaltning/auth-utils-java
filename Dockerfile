FROM openjdk:11-jre

ENV TZ=Europe/Oslo
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

VOLUME /tmp
ARG JAR_FILE
ENV PORT 8084
ENV TYPE publisher
ADD target/${JAR_FILE} app.jar
RUN sh -c 'touch /app.jar'
CMD java -jar $JAVA_OPTS app.jar $PORT $TYPE