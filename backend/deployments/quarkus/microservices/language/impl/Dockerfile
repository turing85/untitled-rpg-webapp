FROM adoptopenjdk/openjdk11@sha256:14340a4c6684ad5749c759cf1d7b3dbc974bad724281966144ab2baf51d60671

EXPOSE 8080/tcp
RUN mkdir /opt/app
COPY target/languageService-runner.jar /opt/app
CMD ["java", "-Duser.timezone=UTC", "-jar",   "/opt/app/languageService-runner.jar" ]