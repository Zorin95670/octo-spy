Feature: DeploymentController, testing DELETE /deployments/progress


  Scenario: Delete deployment progress without authentication should raise an error
    Given I use no authentication
    When I request "/deployments/progress" with method "DELETE"
    Then I expect "401" as status code

  Scenario: Delete deployment progress with no environment should raise an error
    Given I use basic authentication
    When I request "/deployments/progress" with method "DELETE"
    Then I expect "400" as status code
    And  I expect response field "field" is "environment"
    And  I expect response field "message" is "Field value is empty."

  Scenario: Delete deployment progress with empty environment should raise an error
    Given I use basic authentication
    When I request "/deployments/progress" with method "DELETE" with query parameters
      | key         | value |
      | environment |       |
    Then I expect "400" as status code
    And  I expect response field "field" is "environment"
    And  I expect response field "message" is "Field value is empty."

  Scenario: Delete deployment progress with no project should raise an error
    Given I use basic authentication
    When I request "/deployments/progress" with method "DELETE" with query parameters
      | key         | value      |
      | environment | Production |
    Then I expect "400" as status code
    And  I expect response field "field" is "project"
    And  I expect response field "message" is "Field value is empty."

  Scenario: Delete deployment progress with empty project should raise an error
    Given I use basic authentication
    When I request "/deployments/progress" with method "DELETE" with query parameters
      | key         | value      |
      | environment | Production |
      | project     |            |
    Then I expect "400" as status code
    And  I expect response field "field" is "project"
    And  I expect response field "message" is "Field value is empty."

  Scenario: Delete deployment progress with no linked deployments should raise an error
    Given I use basic authentication
    When I request "/deployments/progress" with method "DELETE" with query parameters
      | key         | value |
      | environment | BAD   |
      | project     | BAD   |
    Then I expect "404" as status code
    And  I expect response field "field" is "deployment"
    And  I expect response field "message" is "Entity not found."

  Scenario: Delete deployment progress of deployment without progress should raise an error
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
      | inProgress  | false      | boolean |
    Then I expect "201" as status code
    And  I set response field "id" to context "deployment_id"

    When I request "/deployments/progress" with method "DELETE" with query parameters
      | key         | value           |
      | environment | Production      |
      | project     | test            |
      | id          | [deployment_id] |
    Then I expect "404" as status code
    And  I expect response field "field" is "deploymentProgress"
    And  I expect response field "message" is "Entity not found."

    When I request "/deployments/[deployment_id]" with method "DELETE"
    Then I expect "204" as status code

    When I request "/projects/[project_id]" with method "DELETE"
    Then I expect "204" as status code

  Scenario: Delete deployment progress of deployment with progress should be a success
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

    When I request "/deployments/progress" with method "DELETE" with query parameters
      | key         | value           |
      | environment | Production      |
      | project     | test            |
      | id          | [deployment_id] |
    Then I expect "204" as status code

    When I request "/deployments/[deployment_id]"
    Then I expect "200" as status code
    And  I expect response field "inProgress" is "false"

    When I request "/deployments/[deployment_id]" with method "DELETE"
    Then I expect "204" as status code

    When I request "/projects/[project_id]" with method "DELETE"
    Then I expect "204" as status code