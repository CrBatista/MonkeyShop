FROM amazoncorretto:17
EXPOSE 8081
ADD auth-infrastructure/target/auth-service.jar /myservice.jar
ENTRYPOINT ["java", "-jar", "/myservice.jar"]
