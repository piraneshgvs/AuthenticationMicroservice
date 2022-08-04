# define base docker image
FROM openjdk:8
LABEL maintainer="piraneshs"
ADD target/AuthenticationMicroservice-0.0.1-SNAPSHOT.jar authentication-microservice.jar
ENTRYPOINT ["java", "-jar", "authentication-microservice.jar"]