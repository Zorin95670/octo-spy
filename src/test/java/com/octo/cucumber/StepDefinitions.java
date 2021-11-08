package com.octo.cucumber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.*;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.shaded.org.apache.commons.lang.text.StrSubstitutor;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.CoreMatchers.is;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class StepDefinitions {
    private static final Logger LOGGER = LoggerFactory.getLogger(StepDefinitions.class);

    private static Network network = Network.newNetwork();
    private static PostgreSQLContainer postgresqlContainer = (PostgreSQLContainer) new PostgreSQLContainer("postgres:13.3")
            .withInitScript("db/init-testcontainers.sql")
            .withDatabaseName("octo_db")
            .withUsername("octo")
            .withPassword("password")
            .withNetwork(network)
            .withNetworkAliases("db");
    private static GenericContainer container = new GenericContainer(new ImageFromDockerfile()
            .withDockerfile(new File(System.getProperty("user.dir"), "Dockerfile").toPath()))
            .withExposedPorts(8080)
            .withNetwork(network)
            .withNetworkAliases("api")
            .waitingFor(Wait.forHttp("/octo-spy/api/info"));

    private static HashMap<String, String> globalContext;
    private final Client client = ClientBuilder.newClient();


    private String baseURI;
    private boolean authenticate = false;
    private String authentication;
    private int statusCode;
    private JsonNode json;
    private JsonNode resources;
    private ObjectMapper mapper = new ObjectMapper();

    static {
        globalContext = new HashMap<>();
        globalContext.put("user", "admin");
        globalContext.put("password", "admin");
    }


    public StepDefinitions() {
        if (!postgresqlContainer.isRunning()) {
            postgresqlContainer.start();
        }
        if (!container.isRunning()) {
            container.withEnv("octo-spy.database.host", "db");
            container.withEnv("octo-spy.database.port", "5432");
            container.start();
        }

        baseURI = String.format("http://%s:%s/octo-spy/api", container.getHost(), container.getMappedPort(8080));
    }

    @Given("I use no authentication")
    public void setNoAuthentication() {
        authenticate = false;
        authentication = null;
    }

    @Given("I use basic authentication")
    public void setBasicAuthentication() {
        authenticate = true;
        globalContext.put("authenticationType", "Basic");
    }

    @Then("I set to context {string} with {string}")
    public void setToContext(String key, String value) {
        globalContext.put(key, value);
    }

    @When("I request {string}")
    public void request(String endpoint) throws URISyntaxException, IOException, InterruptedException {
        this.request(endpoint, "GET");
    }

    @When("I request {string} with query parameters")
    public void request(String endpoint, DataTable parameters) throws URISyntaxException, IOException, InterruptedException {
        String queryParameters = parameters
                .asMaps()
                .stream()
                .map((map) -> {
                    String value = replaceWithContext(map.get("value"));
                    try {
                        return String.format("%s=%s", map.get("key"), URLEncoder.encode(value, StandardCharsets.UTF_8.toString()));
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.joining( "&" ));
        this.request(String.format("%s?%s", endpoint, queryParameters), "GET");
    }

    @When("I request {string} with method {string}")
    public void request(String endpoint, String method) throws URISyntaxException, IOException, InterruptedException {
        this.requestFull(endpoint, method, null, null);
    }

    @When("I request {string} with method {string} with body")
    public void requestWithTable(String endpoint, String method, DataTable table) throws URISyntaxException, IOException, InterruptedException {
        this.requestFull(endpoint, method, createBody(table), MediaType.TEXT_PLAIN);
    }

    @When("I request {string} with method {string} with json")
    public void requestWithJson(String endpoint, String method, DataTable table) throws URISyntaxException, IOException, InterruptedException {
        List<Map<String, String>> list = table.asMaps();
        ObjectNode json = JsonNodeFactory.instance.objectNode();
        list.stream().forEach((map) -> {
            String key = map.get("key");
            String value = map.get("value");
            String type = map.get("type");

            if ("integer".equals(type)) {
                json.put(key, Integer.parseInt(value));
            } else if ("float".equals(type)) {
                json.put(key, Float.parseFloat(value));
            } else if ("boolean".equals(type)) {
                json.put(key, Boolean.parseBoolean(value));
            } else if ("array".equals(type) || "object".equals(type)) {
                try {
                    json.set(key, mapper.readTree(value));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            } else {
                json.put(key, value);
            }
        });

        this.requestFull(endpoint, method, Entity.json(json.toString()), MediaType.APPLICATION_JSON);
    }

    public void requestFull(String endpoint, String method, Entity<?> body, String contentType) throws IOException, InterruptedException, URISyntaxException {
        String uri = baseURI + replaceWithContext(endpoint);

        WebTarget target = this.client.target(uri);
        Builder builder = target.request();

        if (authenticate) {
            builder.header("Authorization", this.getToken());
        }
        if (contentType != null) {
            builder.header("Content-Type", contentType);
        }


        LOGGER.info("{} request to {}", method, uri);
        Response response;
        if (body != null) {
            response = builder.build(method, body).invoke();
        } else {
            response = builder.build(method).invoke();
        }

        statusCode = response.getStatus();
        LOGGER.info("Receive {} as status code", statusCode);
        if (statusCode != 204) {
            json = mapper.readTree(response.readEntity(String.class));
            LOGGER.info("With body: {}", json);
        }
    }

    public String replaceWithContext(String text) {
        StrSubstitutor formatter = new StrSubstitutor(globalContext, "[", "]");
        return formatter.replace(text);
    }

    public String getToken() {
        String token = String.format("%s:%s", globalContext.get("user"), globalContext.get("password"));
        return String.format("%s %s", globalContext.get("authenticationType"), Base64.getUrlEncoder().encodeToString(token.getBytes()));
    }

    public Entity createBody(DataTable table) {
        if (table == null || table.isEmpty()) {
            return null;
        }
        String value = table.cell(1, 0);

        if ("NULL".equals(value)) {
            return null;
        } else if ("EMPTY".equals(value)) {
            value = "";
        } else if ("BLANK".equals(value)) {
            value = " ";
        }

        String type = table.cell(1, 1);

        if ("BASE64".equals(type)) {
            String base64Value = Base64.getUrlEncoder().encodeToString(value.getBytes());
            return Entity.text(base64Value);
        }

        return Entity.text(value);
    }

    @Then("I expect \"{int}\" as status code")
    public void expectStatusCode(int statusCode) {
        assertThat(statusCode, equalTo(this.statusCode));
    }

    @Then("I extract resources from response")
    public void extractResponseResource() {
        this.resources = json.get("resources");
    }

    @Then("I expect one resource contains {string} equals to {string}")
    @Then("I expect one resource contains {string} equals to {string} as {string}")
    public void expectOneResourceContains(String field, String text, String type) {
        boolean check = false;
        String value = replaceWithContext(text);
        for(int index = 0; index < resources.size(); index += 1) {
            JsonNode resource = resources.get(index);
            if ("NULL".equals(value)) {
                check = resource.get(field).isNull();
            } else if ("NOT_NULL".equals(value)) {
                check = !resource.get(field).isNull();
            } else if ("EMPTY".equals(value)) {
                check = resource.get(field).isEmpty();
            } else if ("integer".equals(type)) {
                check = resource.get(field).asInt() == Integer.parseInt(value);
            } else if ("float".equals(type)) {
                check = resource.get(field).asDouble() == Double.parseDouble(value);
            } else if ("boolean".equals(type)) {
                check = resource.get(field).asBoolean() == Boolean.parseBoolean(value);
            } else if ("array".equals(type) || "object".equals(type)) {
                check = resource.get(field).toString().equals(value);
            } else {
                check = resource.get(field).asText().equals(value);
            }
            if (check) {
                break;
            }
        }
        assertThat(true, equalTo(check));
    }

    @Then("I expect response is {string}")
    public void expectResponseTypeIs(String type) {
        if("array".equals(type)) {
            assertThat(true, equalTo(json.isArray()));
        } else if("empty".equals(type)) {
            assertThat(true, equalTo(json.isEmpty()));
        } else {
            assertThat(true, equalTo(json.isObject()));
        }
    }

    @Then("I expect response json is {string}")
    public void expectResponseIs(String value) {
        assertThat(json.toString(), equalTo(value));
    }

    @Then("I expect response fields length is \"{int}\"")
    public void expectFieldsLengthIs(int length) {
        assertThat(json.size(), equalTo(length));
    }

    @Then("I expect response field {string} is {string}")
    public void expectFieldIsEqualTo(String field, String expected) {
        if ("NOT_NULL".equals(expected)) {
            assertThat(true, is(!json.get(field).isNull()));
            return;
        } else if ("NULL".equals(expected)) {
            assertThat(true, is(json.get(field).isNull()));
            return;
        }
        assertThat(json.get(field).asText(), equalTo(expected));
    }

    @And("I set response field {string} to context {string}")
    public void setResponseFieldToContext(String from, String to) {
        globalContext.put(to, json.get(from).asText());
    }
}
