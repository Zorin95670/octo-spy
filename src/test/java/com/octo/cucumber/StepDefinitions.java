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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.shaded.org.apache.commons.lang.text.StrSubstitutor;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class StepDefinitions extends DockerConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(StepDefinitions.class);

    private static final HashMap<String, String> globalContext;

    static {
        globalContext = new HashMap<>();
        globalContext.put("user", "admin");
        globalContext.put("password", "admin");
    }

    private final Client client = ClientBuilder.newClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private boolean authenticate = false;
    private int statusCode;
    private JsonNode json;
    private JsonNode resources;
    private JsonNode responseObject;

    @Given("I use no authentication")
    public void setNoAuthentication() {
        authenticate = false;
        String authentication = null;
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
    public void request(String endpoint) {
        this.request(endpoint, "GET");
    }

    @When("I request {string} with query parameters")
    public void request(String endpoint, DataTable parameters) throws URISyntaxException, IOException, InterruptedException {
        this.request(endpoint, "GET", parameters);
    }

    @When("I request {string} with method {string} with query parameters")
    public void request(String endpoint, String method, DataTable parameters) {
        String queryParameters = parameters
                .asMaps()
                .stream()
                .map((map) -> {
                    String value = replaceWithContext(map.getOrDefault("value", ""));
                    if (value == null) {
                        value = "";
                    }
                    try {
                        return String.format("%s=%s", map.get("key"), URLEncoder.encode(value, StandardCharsets.UTF_8.toString()));
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.joining("&"));
        this.request(String.format("%s?%s", endpoint, queryParameters), method);
    }

    @When("I request {string} with method {string}")
    public void request(String endpoint, String method) {
        this.requestFull(endpoint, method, null, null);
    }

    @When("I request {string} with method {string} with body")
    public void requestWithTable(String endpoint, String method, DataTable table) {
        this.requestFull(endpoint, method, createBody(table), MediaType.TEXT_PLAIN);
    }

    @When("I request {string} with method {string} with json")
    public void requestWithJson(String endpoint, String method, DataTable table) {
        List<Map<String, String>> list = table.asMaps();
        ObjectNode json = JsonNodeFactory.instance.objectNode();
        list.forEach((map) -> {
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

    public void requestFull(String endpoint, String method, Entity<?> body, String contentType) {
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
        if (body == null) {
            LOGGER.info("With no body");
        } else {
            LOGGER.info("With body: {}", body.toString());
        }
        Response response;
        if (body != null) {
            response = builder.build(method, body).invoke();
        } else {
            response = builder.build(method).invoke();
        }

        statusCode = response.getStatus();
        LOGGER.info("Receive {} as status code", statusCode);
        if (statusCode != 204) {
            try {
                json = mapper.readTree(response.readEntity(String.class));
                LOGGER.info("With body: {}", json);
            } catch (IOException e) {
                LOGGER.error("Can't read body", e);
            }
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

    public Entity<String> createBody(DataTable table) {
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
        assertThat(this.statusCode, equalTo(statusCode));
    }

    @Then("I extract resources from response")
    public void extractResponseResource() {
        this.resources = json.get("content");
    }

    @Then("I extract first resource from response")
    public void extractResponseFirstResource() {
        this.json = json.get("content").get(0);
    }

    @Then("I extract object {string} from response")
    public void extractResponseObject(String name) {
        this.responseObject = json.get(name);
    }

    @Then("I expect object field {string} is {string}")
    public void expectObjectContains(String field, String text) {
        this.expectObjectContains(field, text, "string");
    }

    @Then("I expect object field {string} is {string} as {string}")
    public void expectObjectContains(String field, String text, String type) {
        String value = replaceWithContext(text);
        assertThat(this.checkValue(responseObject, field, value, type), equalTo(true));
    }


    @Then("I expect response resources length is \"{int}\"")
    public void expectResourcesLengthIs(int length) {
        assertThat(resources.size(), equalTo(length));
    }

    @Then("I expect one resource contains {string} equals to {string}")
    public void expectOneResourceContains(String field, String text) {
        this.expectOneResourceContains(field, text, "string");
    }

    @Then("I expect one resource contains {string} equals to {string} as {string}")
    public void expectOneResourceContains(String field, String text, String type) {
        boolean check = false;
        String value = replaceWithContext(text);
        for (int index = 0; index < resources.size(); index += 1) {
            JsonNode resource = resources.get(index);
            check = this.checkValue(resource, field, value, type);
            if (check) {
                break;
            }
        }
        assertThat(true, equalTo(check));
    }

    public boolean checkValue(JsonNode resource, String field, String value, String type) {
        if ("NULL".equals(value)) {
            return resource.get(field).isNull();
        } else if ("NOT_NULL".equals(value)) {
            return !resource.get(field).isNull();
        } else if ("EMPTY".equals(value)) {
            return resource.get(field).isEmpty();
        } else if ("integer".equals(type)) {
            return resource.get(field).asInt() == Integer.parseInt(value);
        } else if ("float".equals(type)) {
            return resource.get(field).asDouble() == Double.parseDouble(value);
        } else if ("boolean".equals(type)) {
            return resource.get(field).asBoolean() == Boolean.parseBoolean(value);
        } else if ("array".equals(type) || "object".equals(type)) {
            return resource.get(field).toString().equals(value);
        }
        return resource.get(field).asText().equals(value);
    }

    @Then("I expect response resources is {string}")
    public void expectResponseTypeIs(String type) {
        if ("array".equals(type)) {
            assertThat(resources.isArray(), equalTo(true));
        } else if ("empty".equals(type)) {
            assertThat(resources.isEmpty(), equalTo(true));
        } else {
            assertThat(resources.isObject(), equalTo(true));
        }
    }

    @Then("I expect response resources value is {string}")
    public void expectResponseIs(String value) {
        assertThat(resources.toString(), equalTo(value));
    }

    @Then("I expect response fields length is \"{int}\"")
    public void expectFieldsLengthIs(int length) {
        assertThat(json.size(), equalTo(length));
    }

    @Then("I expect object fields length is \"{int}\"")
    public void expectObjectFieldsLengthIs(int length) {
        assertThat(responseObject.size(), equalTo(length));
    }

    @Then("I expect response field {string} is {string}")
    public void expectFieldIsEqualTo(String field, String expected) {
        this.expectFieldIsEqualTo(field, expected, "string");
    }

    @Then("I expect response field {string} is {string} as {string}")
    public void expectFieldIsEqualTo(String field, String expected, String type) {
        if ("NOT_NULL".equals(expected)) {
            assertThat(true, is(!json.get(field).isNull()));
            return;
        } else if ("NULL".equals(expected)) {
            assertThat(true, is(json.get(field).isNull()));
            return;
        }

        assertThat(checkValue(json, field, replaceWithContext(expected), type), equalTo(true));
    }

    @And("I set response field {string} to context {string}")
    public void setResponseFieldToContext(String from, String to) {
        globalContext.put(to, json.get(from).asText());
    }

    @Given("I clean project {string}")
    public void cleanProject(String name) throws URISyntaxException, IOException, InterruptedException {
        this.clean("projects", String.format("name=%s", name));
    }

    @Given("I clean environment {string}")
    public void cleanEnvironment(String name) throws URISyntaxException, IOException,
            InterruptedException {
        this.clean("environments", String.format("name=%s", name));
    }

    @Given("I clean dashboard {string}")
    public void cleanDashboard(String name) throws URISyntaxException, IOException,
            InterruptedException {
        this.clean("dashboards", String.format("name=%s&visible=true", name));
        this.clean("dashboards", String.format("name=%s&visible=false", name));
    }

    @Given("I clean token {string}")
    public void cleanToken(String name) {
        this.request(String.format("/users/token/%s", name), "DELETE");
    }

    public void clean(String entity, String query) throws URISyntaxException, IOException, InterruptedException {
        this.request(String.format("/%s?%s", entity, query));
        if (statusCode == 200 && json.get("totalElements").asInt() > 0) {
            this.extractResponseResource();
            resources.forEach((resource) -> {
                this.request(String.format("/%s/%s", entity, resource.get("id").asText()), "DELETE");
            });
        }
    }
}
