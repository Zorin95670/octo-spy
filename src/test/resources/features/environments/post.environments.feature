Feature: EnvironmentController, testing POST /environments


  Scenario: Create environment without authentication should raise an error
    Given I use no authentication
    When I request "/environments" with method "POST" with json
      | key  | value | type   |
      | name | test  | string |
    Then I expect "401" as status code

  Scenario: Create a environment without name should raise an error
    Given I use basic authentication
    When I request "/environments" with method "POST" with json
      | key      | value | type    |
      | position | 1     | integer |
    Then I expect "400" as status code
    And  I expect response field "field" is "name"
    And  I expect response field "message" is "Field value is empty."

  Scenario: Create a valid environment should be a success
    Given I use basic authentication
    And   I clean environment "test"

    When I request "/environments" with method "POST" with json
      | key      | value | type    |
      | name     | test  | string  |
      | position | 1     | integer |
    Then I expect "201" as status code
    And  I set response field "id" to context "env_id"
    And  I expect response fields length is "3"
    And  I expect response field "id" is "NOT_NULL"
    And  I expect response field "name" is "Test"
    And  I expect response field "position" is "1"

    When I request "/environments/[env_id]" with method "DELETE"
    Then I expect "204" as status code

  Scenario: Create two environment with same name should raise an error
    Given I use basic authentication
    And   I clean environment "test"

    When I request "/environments" with method "POST" with json
      | key  | value | type   |
      | name | test  | string |
    Then I expect "201" as status code
    And  I set response field "id" to context "env_id"

    When I request "/environments" with method "POST" with json
      | key  | value | type   |
      | name | test  | string |
    Then I expect "400" as status code
    And  I expect response field "field" is "name"
    And  I expect response field "value" is "duplicate"
    And  I expect response field "message" is "Wrong field value."

    When I request "/environments/[env_id]" with method "DELETE"
    Then I expect "204" as status code
