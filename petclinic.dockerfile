FROM bellsoft/liberica-openjdk-alpine:latest

EXPOSE 8080

WORKDIR /var/petclinic

COPY . .

ENTRYPOINT ["sleep", "84600"]
