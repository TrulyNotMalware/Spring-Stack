FROM eclipse-temurin:21.0.1_12-jdk
ARG JAR_FILE_NAME=auth-0.0.1
ARG SERVER_PORT=80

ARG PROFILES="prod,jwt,oauth-client"
ENV PROFILE=${PROFILES}
COPY ./build/libs/$JAR_FILE_NAME.jar /app.jar

EXPOSE $SERVER_PORT
ENTRYPOINT exec java -jar -Dspring.profiles.active=${PROFILE} -Duser.timezone=Asia/Seoul /app.jar