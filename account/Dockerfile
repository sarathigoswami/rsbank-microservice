FROM openjdk:21-jdk-slim

LABEL "author"="sarathigoswami"
LABEL "organization"="RS Bank"
LABEL "version"="0.1"

COPY target/account-0.1.jar account-0.1.jar

ENTRYPOINT ["java", "-jar", "/account-0.1.jar"]