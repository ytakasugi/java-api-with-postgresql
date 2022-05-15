FROM openjdk:15-slim

RUN apt-get update && \
    apt-get -y install git && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /workspace

COPY . ./workspace