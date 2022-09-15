FROM openjdk:11-jdk

ENV LC_ALL=C.UTF-8

WORKDIR /

COPY . .

RUN chmod +x ./gradlew

CMD ["java", "-jar", "./QR-0.0.1-SNAPSHOT.jar"]