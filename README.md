# octo-spy

API to get and save all project's version.

## Database management

To make database working:

- download postgres:9.6 docker image.
- Run docker postgres with this command:

docker run -p 5432:5432 --rm -ti --name postgres -e POSTGRES_PASSWORD=password -e POSTGRES_USER=octo -e POSTGRES_DB=octo_db -e POSTGRES_HOST_AUTH_METHOD=trust postgres:9.6

- Execute this maven command to initialize database

mvn compile && mvn flyway:migrate -Dflyway.url=jdbc:postgresql://localhost:5432/octo_db -Dflyway.user=octo -Dflyway.password=password
