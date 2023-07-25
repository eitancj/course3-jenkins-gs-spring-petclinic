FROM bellsoft/liberica-openjdk-alpine:latest

EXPOSE 8080

#COPY . .

RUN apk add openjdk17

WORKDIR /var/petclinic

#ENTRYPOINT ["sleep", "84600"]
ENTRYPOINT ["java", "-jar"]

# The 'command' property in 'docker-compose.yml' passes on the jarfile-path argument
# (which is relative to WORKDIR), which in turn is appended to the above ENTRYPOINT property
# when running 'docker-compose up'.