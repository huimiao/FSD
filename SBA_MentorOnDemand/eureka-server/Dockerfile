FROM java:8-jdk-alpine

COPY ./target/eureka-server-1.0.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch eureka-server-1.0.jar'

ENTRYPOINT ["java", "-jar", "eureka-server-1.0.jar"]
