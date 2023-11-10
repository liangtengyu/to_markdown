FROM openjdk:8

COPY /chromeDriver  /chromeDriver
EXPOSE 9999
COPY ./target/markdown_resolve.jar /markdown_resolve.jar
ENTRYPOINT ["java", "-jar", "/markdown_resolve.jar"]
