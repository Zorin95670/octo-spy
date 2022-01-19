Feature: DeploymentController, testing GET /deployments/[id]


  Scenario: Get deployment with unknown id should raise an error
    Given I use basic authentication
    And I clean project "test"

    When I request "/projects" with method "POST" with json
      | key      | value | type    |
      | name     | test  | string  |
      | isMaster | true  | boolean |
    Then I expect "201" as status code
    And  I set response field "id" to context "project_id"

    When I request "/deployments" with method "POST" with json
      | key         | value      | type    |
      | environment | Production | string  |
      | project     | test       | string  |
      | client      | client     | string  |
      | version     | 2.0.0      | string  |
      | alive       | true       | boolean |
      | inProgress  | true       | boolean |
    Then I expect "201" as status code
    And  I set response field "id" to context "deployment_id"

    When I request "/deployments/[deployment_id]" with method "DELETE"
    Then I expect "204" as status code

    When I request "/deployments/[deployment_id]"
    Then I expect "404" as status code
    And  I expect response field "field" is "id"
    And  I expect response field "message" is "Entity not found."

    When I request "/projects/[project_id]" with method "DELETE"
    Then I expect "204" as status code

  Scenario: Get deployment with a valid id should be a success
    Given I use basic authentication
    And I clean project "test"

    When I request "/projects" with method "POST" with json
      | key      | value | type    |
      | name     | test  | string  |
      | isMaster | true  | boolean |
    Then I expect "201" as status code
    And  I set response field "id" to context "project_id"

    When I request "/deployments" with method "POST" with json
      | key         | value      | type    |
      | environment | Production | string  |
      | project     | test       | string  |
      | client      | client     | string  |
      | version     | 1.0.0      | string  |
      | alive       | true       | boolean |
      | inProgress  | true       | boolean |
    Then I expect "201" as status code
    And  I set response field "id" to context "deployment_id"

    When I request "/deployments/[deployment_id]"
    And  I expect response fields length is "13"
    And  I expect response field "id" is "[deployment_id]"
    And  I expect response field "environment" is "Production"
    And  I expect response field "projectId" is "[project_id]"
    And  I expect response field "project" is "test"
    And  I expect response field "masterProject" is "test"
    And  I expect response field "color" is "NULL"
    And  I expect response field "masterProjectColor" is "null"
    And  I expect response field "client" is "client"
    And  I expect response field "version" is "1.0.0"
    And  I expect response field "alive" is "true"
    And  I expect response field "inProgress" is "true"
    And  I expect response field "insertDate" is "NOT_NULL"
    And  I expect response field "updateDate" is "NOT_NULL"

    When I request "/deployments/[deployment_id]" with method "DELETE"
    Then I expect "204" as status code

    When I request "/projects/[project_id]" with method "DELETE"
    Then I expect "204" as status code