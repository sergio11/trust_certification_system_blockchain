# Trust Certification System powered by Blockchain Technology.

<p>
  <img src="https://img.shields.io/github/last-commit/sergio11/trust_certification_system_blockchain.svg" />
</p>

<img width="auto" align="left" src="./tcs_logo.jpg" />

TCS uses Blockchain technology in order to provide inviolability, immutability and easy verification for all your certificates. This platform guarantees students and certificate authorities the possibility of issuein a digital and unforgeable version of their certificates and it is guaranteed that any interested third party can verify their integrity.

A Certification Authority and a Student reach an agreement in order to issue a certificate on the platform, this certificate is generated, signed and stored using cryptographic techniques. Later, the Student can go to any other institution and share their certificate, whose veracity can be verified directly on the platform without having to contact the institution that issued it again.

</br>

<p align="center">
  <img src="https://img.shields.io/badge/Solidity-2E8B57?style=for-the-badge&logo=solidity&logoColor=white" />
  <img src="https://img.shields.io/badge/Ethereum-3C3C3D?style=for-the-badge&logo=Ethereum&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white" />
  <img src="https://img.shields.io/badge/-Spring%20Cloud-2E8B57?style=for-the-badge&logo=spring&logoColor=white" />
  <img src="https://img.shields.io/badge/Vault-%23000000.svg?&style=for-the-badge&logo=Vault&logoColor=white" />
  <img src="https://img.shields.io/badge/MongoDB-4EA94B?style=for-the-badge&logo=mongodb&logoColor=white" />
  <img src="https://img.shields.io/badge/-IPFS-3e5f8a?style=for-the-badge&logo=ipfs&logoColor=white" />
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" />
  <img src="https://img.shields.io/badge/apache%20kafka-%23000000.svg?&style=for-the-badge&logo=Apache%20Kafka&logoColor=white" />
  <img src="https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white" />
  <img src="https://img.shields.io/badge/Firebase-039BE5?style=for-the-badge&logo=Firebase&logoColor=white" />
  <img src="https://img.shields.io/badge/json%20web%20tokens-323330?style=for-the-badge&logo=json-web-tokens&logoColor=pink" />
  <img src="https://img.shields.io/badge/Blockchain.com-121D33?logo=blockchaindotcom&logoColor=fff&style=for-the-badge" />
  <img src="https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white" />
  <img src="https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white" />
  <img src="https://img.shields.io/badge/Prometheus-E6522C?style=for-the-badge&logo=Prometheus&logoColor=white" />
  <img src="https://img.shields.io/badge/grafana-%23F46800.svg?style=for-the-badge&logo=grafana&logoColor=white" />

</p>

## Why Blockchain Technology?

The Blockchain technology is a new technology that appeared in 2008 that allows information to be recorded with guarantees of originality and security never possible before. It is based on a cryptographic system that allows the issuers to be irrefutably identified and that guarantees the immutability of the information.

The application of this technology in this context makes it possible to create a new sort of qualification that is more reliable, transparent, economical and faster. Its ability to guarantee the identity of the certification authority of the certificate represents a paradigm shift that is already beginning to revolutionize the sector.

The implementation of the blockchain network means that there are many copies of the information, all of them necessary and absolutely identical. This makes fraud impossible, and also guarantees the survival of the certificates issued by the certification authority, even though that said Institution disappears. The certificates thus issued are perennial and immutable.

Taking into account all of this, blockchain technology can hasten the end of the paper certification system. Until now, the use of digital certificates had been paralyzed due to the ease with which they could be forged. Blockchain provides organizations with a way to issue digital certificates that are unalterable and valid in perpetuity, since their authenticity can be checked against the system itself. Certificates are transferred as a token on the blockchain and are always available. These advantages over current systems significantly increase the value proposition of digital certificates, possibly leading to their widespread use.

## Advantages of the Trust Certification System.

### Security

Digital certificates cannot be altered or manipulated. They are generated and securely stored on a private blockchain network. Nothing and no one can change the information registered in the Blockchain.

### Integrity

They assure in the face of third parties the authenticity of the identity and the information they contained. The registered information can be verified by third parties, such as companies, selection committees, etc.

### Simplicity

Students or third parties can confirm the authenticity of the certificates immediately, using only the mobile phone.

### Ownership

The student or professional is the sovereign owner of the information and decides with whom to share it. The unequivocal identification of the individual or the products is guaranteed without the possibility of the information being falsified.

## Benefits for students, certification authorities and third parties

### Students

* Comfortable system to share and validate certificates

### Certificate Authorities

* Eliminate the possibility of forging certificates.
* Fast certification process.
* Cheaper process, we use our own monetary system based on ERC 20 tokens.
* It stick off as an innovative and avant-garde institution.
* Provides greater value for students.
* Preserves the prestige of the school by ensuring the veracity of the certificates.

### Third parties such as companies or other verification institutions.

* Ability to verify candidates' qualifications through a secure, fast and free system for the verifier.
* Safer selection processes that enable economic savings derived from hiring errors.

## Architecture Overview


## Used technology.

* Spring Cloud Starter Config.
* Spring Cloud Starter Netflix Eureka Client.
* Spring Cloud Stream.
* Spring Cloud Starter Stream Kafka.
* Spring Boot Jasypt.
* Spring Boot Starter Actuator.
* Spring Cloud Config Monitor.
* Spring Vault Core.
* Spring Ldap Core.
* Spring Boot Starter Data MongoDB.
* Spring Boot Starter Data Redis.
* Spring Boot Starter Web.
* Spring Boot Starter Security.
* Spring Boot Starter Mail.
* Spring Boot Starter Thymeleaf.
* Apache POI / PdfBox / Docx4j.
* Web3j.
* PayPal Checkout Sdk.
* Java IPFS Http Client.

## Running Applications as Docker containers.

### Rake Tasks

The available tasks are detailed below (rake --task)

| Task | Description |
| ------ | ------ |
| tcs:check_docker_task | Check Docker and Docker Compose Task |
| tcs:cleaning_environment_task | Cleaning Evironment Task |
| tcs:deploy | Deploys Trust certification System Blockchain and launches all services and daemons needed to properly work |
| tcs:ethereum:check_network_deployment_file | Check Private Ethereum Network Deployment File |
| tcs:ethereum:start | Start Private Ethereum Network |
| tcs:ethereum:stop | Stop Private Ethereum Network |
| tcs:ethereum:undeploy | UnDeploy Private Ethereum Network |
| tcs:ipfs:check_cluster_deployment_file | Check IPFS Cluster Deployment File |
| tcs:ipfs:start | Start IPFS Cluster |
| tcs:ipfs:stop | Stop IPFS Cluster |
| tcs:ipfs:undeploy | UnDeploy IPFS Cluster |
| tcs:login | Authenticating with existing credentials |
| tcs:platform:check_deployment_file | Check Platform Deployment File |
| tcs:platform:compile | Compile Project |
| tcs:platform:init_ldap_backup | Load initial ldap backup |
| tcs:platform:package | Build Docker Images |
| tcs:platform:start | Start Platform |
| tcs:platform:stop | Stop Platform |
| tcs:platform:undeploy | UnDeploy Platform |
| tcs:start | Start Trust certification System Blockchain |
| tcs:status | Status Containers |
| tcs:stop | Stop Containers |
| tcs:undeploy | UnDeploy Trust certification System Blockchain |


To start the platform make sure you have Ruby installed, go to the root directory of the project and run the `rake deploy` task, this task will carry out a series of preliminary checks, discard images and volumes that are no longer necessary and also proceed to download all the images and the initialization of the containers.

### Containers Ports

In this table you can view the ports assigned to each service to access to the Web tools or something else you can use to monitoring the flow.

| Container | Port |
| ------ | ------ |
| Ethereum Lite Explorer | localhost:3001 |
| Ethereum Netstats | localhost:3000 |
| Kafka Topics UI | localhost:8088 |
| MongoDB Express | localhost:8083 |
| Swagger UI API Gateway | localhost:8080 |
| IPFS WebUI | localhost:5001/webui |


## Some screenshots.

As follow, I include some images that help us to understand how each part of the system works.

<img width="auto" src="./doc/screenshots/ethereum_netstats_2.PNG" />
<img width="auto" src="./doc/screenshots/ethreum_block_explorer.PNG" />
<img width="auto" src="./doc/screenshots/ethreum_miner.PNG" />
<img width="auto" src="./doc/screenshots/ethreum_platform.PNG" />
<img width="auto" src="./doc/screenshots/ethreum_remix.PNG" />
<img width="auto" src="./doc/screenshots/ethreum_remix_2.PNG" />
<img width="auto" src="./doc/screenshots/event_logs.PNG" />
<img width="auto" src="./doc/screenshots/ipfs_platform.PNG" />
<img width="auto" src="./doc/screenshots/ipfs_webui.PNG" />
<img width="auto" src="./doc/screenshots/ipfs_webui_2.PNG" />
<img width="auto" src="./doc/screenshots/kafka_image.PNG" />
<img width="auto" src="./doc/screenshots/kafka_image_2.PNG" />
<img width="auto" src="./doc/screenshots/kafka_image_3.PNG" />
<img width="auto" src="./doc/screenshots/platform_containers.PNG" />
<img width="auto" src="./doc/screenshots/spring_eureka.PNG" />

















