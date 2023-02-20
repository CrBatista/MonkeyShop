FROM amazoncorretto:17
EXPOSE 8082
ADD customer-infrastructure/target/customer-service.jar /myservice.jar
ENTRYPOINT ["java", "-jar", "/myservice.jar"]
