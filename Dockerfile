FROM openjdk:22-jdk-slim

WORKDIR /app

COPY  /target/book.catalog.war .

CMD ["java", "-jar", "book.catalog.war"]