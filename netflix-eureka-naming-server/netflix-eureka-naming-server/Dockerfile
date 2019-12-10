FROM openjdk:8
EXPOSE 8761
ADD /target/netflix-eureka-naming-server-0.0.1-SNAPSHOT.jar netflix-eureka-naming-server-docker.jar
ENTRYPOINT ["java","-jar","netflix-eureka-naming-server-docker.jar"]