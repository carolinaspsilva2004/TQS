FROM openjdk:21-jdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENV JAVA_TOOL_OPTIONS="-Xdebug -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
CMD ["sh", "-c", "java -jar /app.jar 2>&1 | tee /proc/1/fd/1"]