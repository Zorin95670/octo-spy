package com.octo.cucumber;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.builder.ImageFromDockerfile;

import java.io.File;

public class DockerConfiguration {

    static final String baseURI;

    private static final Network network = Network.newNetwork();
    private static final PostgreSQLContainer postgresqlContainer = (PostgreSQLContainer) new PostgreSQLContainer("postgres:13.3")
            .withInitScript("db/init-testcontainers.sql")
            .withDatabaseName("octo_db")
            .withUsername("octo")
            .withPassword("password")
            .withNetwork(network)
            .withNetworkAliases("db");
    private static final GenericContainer container = new GenericContainer(new ImageFromDockerfile()
            .withDockerfile(new File(System.getProperty("user.dir"), "Dockerfile").toPath()))
            .withExposedPorts(8080)
            .withNetwork(network)
            .withNetworkAliases("api")
            .waitingFor(Wait.forHttp("/octo-spy/api/info"));

    static {
        container.withEnv("DATABASE_HOST", "db");
        container.withEnv("DATABASE_PORT", "5432");

        postgresqlContainer.start();
        container.start();

        baseURI = String.format("http://%s:%s/octo-spy/api", container.getHost(), container.getMappedPort(8080));
    }
}
