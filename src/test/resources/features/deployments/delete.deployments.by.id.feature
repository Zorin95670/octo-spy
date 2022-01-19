Feature: DeploymentController, testing DELETE /deployments/[id]


  Scenario: Delete deployment without authentication should raise an error
    Given I use no authentication
    When I request "/deployments/1" with method "DELETE"
    Then I expect "401" as status code

  Scenario: Delete deployment with unknown id should raise an error
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

    When I request "/deployments/[deployment_id]" with method "DELETE"
    Then I expect "204" as status code

    When I request "/deployments/[deployment_id]" with method "DELETE"
    Then I expect "404" as status code
    And  I expect response field "field" is "id"
    And  I expect response field "message" is "Entity not found."

    When I request "/projects/[project_id]" with method "DELETE"
    Then I expect "204" as status code

  Scenario: Delete deployment with valid id should be a success
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

    When I request "/deployments/[deployment_id]" with method "DELETE"
    Then I expect "204" as status code

    When I request "/projects/[project_id]" with method "DELETE"
    Then I expect "204" as status code