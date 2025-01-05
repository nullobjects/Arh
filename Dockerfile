FROM amazoncorretto:21
ARG JAR_FILE=target/*.jar
COPY ./target/Arh1-1.0.0.jar arh.jar
ENTRYPOINT ["java", "-jar", "/arh.jar"]