FROM clojure:temurin-25-trixie-slim

WORKDIR /app

# https://github.com/hexops-graveyard/dockerfile#do-not-use-a-uid-below-10000
RUN groupadd -g 10001 app && \
    useradd -m -r -u 10001 -g app app && \
    chown app:app /app

USER app

# Fetch and install dependencies
COPY deps.edn .
RUN clojure -P

COPY logback.xml logback.xml
COPY src src
CMD ["clojure", "-M:run"]
