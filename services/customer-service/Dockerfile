FROM amazoncorretto:17
EXPOSE 8081
ADD infrastructure/target/customer-service.jar /myservice.jar
ENTRYPOINT ["java", "-jar", "/myservice.jar"]
