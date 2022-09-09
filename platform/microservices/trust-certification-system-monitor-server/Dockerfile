FROM openjdk:11
# Add Maintainer Info
LABEL description="Trust Certification System Monitor Server"
# Args for image
ARG PORT=8060

RUN apt-get update -y && apt-get upgrade -y
RUN apt-get install dos2unix -y
RUN ln -s /bin/bash /usr/bin
RUN mkdir -p /usr/src/app
RUN mkdir -p /opt/wallets
WORKDIR /usr/src/app

COPY resources/wait-for-it.sh  wait-for-it.sh
COPY target/trust_certification_system_monitor_server.jar app.jar

RUN dos2unix wait-for-it.sh
RUN chmod +x wait-for-it.sh
RUN uname -a
RUN pwd
RUN ls -al

EXPOSE ${PORT}

CMD ["sh", "-c", "echo 'waiting for 300 seconds for kafka to be accessable before starting application' && ./wait-for-it.sh -t 300 kafka:9092 && echo 'waiting for 300 seconds for tcs-discovery-server to be accessable before starting application' && ./wait-for-it.sh -t 300 tcs-discovery-server:8761 && echo 'waiting for 300 seconds for tcs-config-server to be accessable before starting application' && ./wait-for-it.sh -t 300 tcs-config-server:8888 -- java -jar app.jar"]

