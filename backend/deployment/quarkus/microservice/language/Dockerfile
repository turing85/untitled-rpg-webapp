FROM quay.io/quarkus/centos-quarkus-maven:19.2.1

ADD target/languageService-runner /
EXPOSE 8080
CMD ["/languageService-runner", "-Dquarkus.http.host=0.0.0.0"]
