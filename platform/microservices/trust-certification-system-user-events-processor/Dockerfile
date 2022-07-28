FROM openjdk:8-jdk-alpine
# Add Maintainer Info
LABEL description="Trust Certification System User Events Processor"
# Args for image
ARG PORT=8080

RUN apk update && apk upgrade
RUN ln -s /bin/bash /usr/bin
RUN mkdir -p /usr/src/app
RUN mkdir -p /opt/wallets
WORKDIR /usr/src/app

COPY resources/wait-for-it.sh  wait-for-it.sh
COPY target/trust_certification_system_user_events_processor.jar app.jar

RUN dos2unix wait-for-it.sh
RUN chmod +x wait-for-it.sh
RUN uname -a
RUN pwd
RUN ls -al

EXPOSE ${PORT}

CMD ["sh", "-c", "echo 'waiting for 300 seconds for kafka to be accessable before starting application' && ./wait-for-it.sh -t 300 kafka:9092 && echo 'waiting for 300 seconds for tcs-discovery-server to be accessable before starting application' && ./wait-for-it.sh -t 300 tcs-discovery-server:8761 && echo 'waiting for 300 seconds for tcs-config-server to be accessable before starting application' && ./wait-for-it.sh -t 300 tcs-config-server:8888 -- java -jar app.jar"]
