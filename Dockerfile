FROM maven:3.6.3-ibmjava-8-alpine as builder
ARG PACKAGE_NAME
WORKDIR /simple-protocol
COPY . /simple-protocol
RUN mvn clean compile package "-Dpackage.name=${PACKAGE_NAME}"
RUN ls

FROM java:8-jre-alpine
ARG PACKAGE_NAME
ARG SERVER_PORT
ENV PACKAGE_NAME=${PACKAGE_NAME}-jar-with-dependencies.jar
ENV TINI_VERSION=0.18.0-r0 \
    BASH_VERSION=4.4.19-r1
RUN apk add --no-cache --repository=http://dl-cdn.alpinelinux.org/alpine/v3.8/main/x86_64/ \
    bash
RUN apk add --no-cache --repository=http://dl-cdn.alpinelinux.org/alpine/v3.8/community/x86_64/ \
    tini
WORKDIR /simple-protocol

COPY --from=builder /simple-protocol/target/${PACKAGE_NAME} /simple-protocol
COPY --from=builder /simple-protocol/scripts/container/entrypoint /simple-protocol

EXPOSE $SERVER_PORT
ENTRYPOINT ["/sbin/tini", "--", "/simple-protocol/entrypoint"]


