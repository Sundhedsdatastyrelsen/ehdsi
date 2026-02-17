FROM eclipse-temurin:11-jre-jammy


WORKDIR /usr/local/openncp-configuration-utility
COPY /target/utilities/openncp-configuration-utility.jar /usr/local/openncp-configuration-utility
COPY /openncp-configuration-utility/* /usr/local/openncp-configuration-utility

CMD ["java", "-jar", "openncp-configuration-utility.jar"]
