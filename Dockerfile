FROM java:openjdk-8-jre

COPY target/fallback-cache-1.0.0-standalone.jar fallback-cache-1.0.0-standalone.jar

ENV PORT 8080

EXPOSE 8080

CMD ["java", "-jar", "fallback-cache-1.0.0-standalone.jar"]