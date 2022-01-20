Feature: DeploymentController, testing GET /deployments

  Scenario: Get deployment with a valid search id should be a success
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

    When I request "/deployments" with query parameters
      | key | value           |
      | id  | [deployment_id] |
    Then I expect response field "totalElements" is "1"
    And  I extract resources from response
    And  I expect one resource contains "id" equals to "[deployment_id]" as "integer"

    When I request "/deployments/[deployment_id]" with method "DELETE"
    Then I expect "204" as status code

    When I request "/projects/[project_id]" with method "DELETE"
    Then I expect "204" as status code