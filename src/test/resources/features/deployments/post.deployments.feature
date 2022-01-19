Feature: deploymentController, testing POST /deployments


  Scenario: Create deployment without authentication should raise an error
    Given I use no authentication
    When I request "/deployments" with method "POST" with json
      | key         | value      | type   |
      | environment | Production | string |
      | project     | test       | string |
      | client      | client     | string |
      | version     | 1.0.0      | string |
    Then I expect "401" as status code

  Scenario: Create a deployment without environment should raise an error
    Given I use basic authentication
    When I request "/deployments" with method "POST" with json
      | key     | value  | type   |
      | project | test   | string |
      | client  | client | string |
      | version | 1.0.0  | string |
    Then I expect "400" as status code
    And  I expect response field "field" is "environment"
    And  I expect response field "message" is "Field value is empty."

  Scenario: Create a deployment with unknown environment should raise an error
    Given I use basic authentication
    When I request "/deployments" with method "POST" with json
      | key         | value  | type   |
      | environment | bad    | string |
      | project     | test   | string |
      | client      | client | string |
      | version     | 1.0.0  | string |
    Then I expect "400" as status code
    And  I expect response field "field" is "environment"
    And  I expect response field "value" is "bad"
    And  I expect response field "message" is "Wrong field value."

  Scenario: Create a deployment without project should raise an error
    Given I use basic authentication
    When I request "/deployments" with method "POST" with json
      | key         | value      | type   |
      | environment | Production | string |
      | client      | client     | string |
      | version     | 1.0.0      | string |
    Then I expect "400" as status code
    And  I expect response field "field" is "project"
    And  I expect response field "message" is "Field value is empty."

  Scenario: Create a deployment with unknown project should raise an error
    Given I use basic authentication
    When I request "/deployments" with method "POST" with json
      | key         | value      | type   |
      | environment | Production | string |
      | project     | bad        | string |
      | client      | client     | string |
      | version     | 1.0.0      | string |
    Then I expect "400" as status code
    And  I expect response field "field" is "project"
    And  I expect response field "value" is "bad"
    And  I expect response field "message" is "Wrong field value."

  Scenario: Create a deployment without client should raise an error
    Given I use basic authentication
    When I request "/deployments" with method "POST" with json
      | key         | value      | type   |
      | environment | Production | string |
      | project     | test       | string |
      | version     | 1.0.0      | string |
    Then I expect "400" as status code
    And  I expect response field "field" is "client"
    And  I expect response field "message" is "Field value is empty."

  Scenario: Create a deployment without version should raise an error
    Given I use basic authentication
    When I request "/deployments" with method "POST" with json
      | key         | value      | type   |
      | environment | Production | string |
      | project     | test       | string |
      | client      | client     | string |
    Then I expect "400" as status code
    And  I expect response field "field" is "version"
    And  I expect response field "message" is "Field value is empty."

  Scenario: Create a valid deployment should be a success
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
    And  I expect response fields length is "13"
    And  I expect response field "id" is "NOT_NULL"
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


  Scenario Outline: Create a second deployment should make not <field> the previous
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
    And  I set response field "id" to context "first_deployment_id"

    When I request "/deployments" with method "POST" with json
      | key         | value      | type    |
      | environment | Production | string  |
      | project     | test       | string  |
      | client      | client     | string  |
      | version     | 2.0.0      | string  |
      | alive       | true       | boolean |
      | inProgress  | true       | boolean |
    Then I expect "201" as status code
    And  I set response field "id" to context "second_deployment_id"
    And  I expect response field "id" is "[second_deployment_id]"
    And  I expect response field "<field>" is "true"

    When I request "/deployments/[first_deployment_id]"
    Then I expect "200" as status code
    And  I expect response field "id" is "[first_deployment_id]"
    And  I expect response field "<field>" is "false"

    When I request "/deployments/[first_deployment_id]" with method "DELETE"
    Then I expect "204" as status code

    When I request "/deployments/[second_deployment_id]" with method "DELETE"
    Then I expect "204" as status code

    When I request "/projects/[project_id]" with method "DELETE"
    Then I expect "204" as status code

    Examples:
      | field      |
      | alive      |
      | inProgress |

