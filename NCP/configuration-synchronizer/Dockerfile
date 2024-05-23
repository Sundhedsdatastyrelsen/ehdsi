FROM maven:3-eclipse-temurin-21 as builder

WORKDIR /usr/src/app
COPY . .
RUN --mount=type=cache,target=/root/.m2 mvn package


FROM eclipse-temurin:21

WORKDIR /opt/configuration-synchronizer
COPY --from=builder /usr/src/app/target/configuration-synchronizer-*.jar configuration-synchronizer.jar
COPY run.sh run.sh

CMD ["./run.sh"]
