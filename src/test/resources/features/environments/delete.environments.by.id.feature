Feature: EnvironmentController, testing DELETE /environments/[id]


  Scenario: Delete environment without authentication should raise an error
    Given I use no authentication
    When I request "/environments/1" with method "DELETE"
    Then I expect "401" as status code

  Scenario: Delete environment with unknown id should raise an error
    Given I use basic authentication
    And   I clean environment "test"

    When I request "/environments" with method "POST" with json
      | key      | value | type    |
      | name     | test  | string  |
    Then I expect "201" as status code
    And  I set response field "id" to context "environment_id"

    When I request "/environments/[environment_id]" with method "DELETE"
    Then I expect "204" as status code

    When I request "/environments/[environment_id]" with method "DELETE"
    Then I expect "404" as status code
    And  I expect response field "field" is "id"
    And  I expect response field "message" is "Entity not found."

  Scenario: Delete environment with valid id should be a success
    Given I use basic authentication
    And   I clean environment "test"

    When I request "/environments" with method "POST" with json
      | key      | value | type    |
      | name     | test  | string  |
    Then I expect "201" as status code
    And  I set response field "id" to context "environment_id"

    When I request "/environments/[environment_id]" with method "DELETE"
    Then I expect "204" as status code