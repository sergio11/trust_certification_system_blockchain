FROM amazoncorretto:11.0.16-alpine3.14
# Add Maintainer Info
LABEL description="Trust Certfication System Discovery Server"
# Args for image
ARG PORT=8761

RUN apk update && apk upgrade
RUN ln -s /bin/bash /usr/bin
RUN mkdir -p /usr/src/app
RUN mkdir -p /opt/wallets
WORKDIR /usr/src/app

COPY resources/wait-for-it.sh  wait-for-it.sh
COPY target/trust_certification_system_discovery_server.jar app.jar

RUN dos2unix wait-for-it.sh
RUN chmod +x wait-for-it.sh
RUN uname -a
RUN pwd
RUN ls -al

EXPOSE ${PORT}

CMD ["sh", "-c", "echo 'waiting for 300 seconds for kafka to be accessable before starting application' && ./wait-for-it.sh -t 300 kafka:9092 -- java -jar app.jar"]
