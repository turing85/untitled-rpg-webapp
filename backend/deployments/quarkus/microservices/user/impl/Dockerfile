FROM adoptopenjdk/openjdk11@sha256:14340a4c6684ad5749c759cf1d7b3dbc974bad724281966144ab2baf51d60671

RUN mkdir /opt/app
COPY target/userService-runner.jar /opt/app
ENTRYPOINT [ \
  "java", \
  "-Duser.timezone=UTC", \
  "-Duser.language=en", \
  "-Dfile.encoding=UTF-8", \
  "-jar", \
  "/opt/app/userService-runner.jar" \
]