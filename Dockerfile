FROM openjdk:11
ARG JAR_FILE=build/libs/*all.jar
COPY ${JAR_FILE} keymanager-rest.jar
ENTRYPOINT ["java","-jar","/keymanager-rest.jar"]