# Build production image for ePrescriptionPoC

FROM eclipse-temurin:17-jre-alpine

EXPOSE 8080

COPY src/main/scripts/run-in-container.sh .
RUN chmod +x /run-in-container.sh

COPY target/epps-application.jar .

CMD /run-in-container.sh epps-application.jar
