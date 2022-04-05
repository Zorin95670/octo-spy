Feature: dashboardController, testing POST /dashboards


  Scenario: Create dashboard without authentication should raise an error
    Given I use no authentication
    When I request "/dashboards" with method "POST" with json
      | key        | value         | type    |
      | name       | test          | string  |
      | parameters | { "id": "1" } | object  |
      | visible    | false         | boolean |
    Then I expect "401" as status code

  Scenario: Create a dashboard without name should raise an error
    Given I use basic authentication
    When I request "/dashboards" with method "POST" with json
      | key        | value         | type    |
      | parameters | { "id": "1" } | object  |
      | visible    | false         | boolean |
    Then I expect "400" as status code
    And  I expect response field "field" is "name"
    And  I expect response field "message" is "Field value is empty."

  Scenario: Create a dashboard without parameters should raise an error
    Given I use basic authentication
    When I request "/dashboards" with method "POST" with json
      | key     | value | type    |
      | name    | test  | string  |
      | visible | false | boolean |
    Then I expect "400" as status code
    And  I expect response field "field" is "parameters"
    And  I expect response field "message" is "Field value is empty."

  Scenario: Create a dashboard with empty parameters should raise an error
    Given I use basic authentication
    When I request "/dashboards" with method "POST" with json
      | key        | value | type    |
      | name       | test  | string  |
      | parameters | {}    | object  |
      | visible    | false | boolean |
    Then I expect "400" as status code
    And  I expect response field "field" is "parameters"
    And  I expect response field "message" is "Field value is empty."

  Scenario: Create a valid dashboard should be a success
    Given I use basic authentication
    And I clean dashboard "test"

    When I request "/dashboards" with method "POST" with json
      | key        | value         | type    |
      | name       | test          | string  |
      | parameters | { "id": "1" } | object  |
      | visible    | true          | boolean |
    Then I expect "201" as status code
    And  I set response field "id" to context "dashboard_id"
    And  I expect response fields length is "7"
    And  I expect response field "id" is "[dashboard_id]"
    And  I expect response field "name" is "test"
    And  I expect response field "parameters" is "{\"id\":\"1\"}" as "object"
    And  I expect response field "visible" is "true" as "boolean"
    And  I expect response field "canBeDeleted" is "true" as "boolean"
    And  I expect response field "insertDate" is "NOT_NULL"
    And  I expect response field "updateDate" is "NOT_NULL"

    When I request "/dashboards/[dashboard_id]" with method "DELETE"
    Then I expect "204" as status code

  Scenario: Create a dashboard without visibility should be a not visible
    Given I use basic authentication
    And I clean dashboard "test"

    When I request "/dashboards" with method "POST" with json
      | key        | value         | type   |
      | name       | test          | string |
      | parameters | { "id": "1" } | object |
    Then I expect "201" as status code
    And  I set response field "id" to context "dashboard_id"
    And  I expect response fields length is "7"
    And  I expect response field "id" is "[dashboard_id]"
    And  I expect response field "name" is "test"
    And  I expect response field "parameters" is "{\"id\":\"1\"}" as "object"
    And  I expect response field "visible" is "false" as "boolean"
    And  I expect response field "canBeDeleted" is "true" as "boolean"
    And  I expect response field "insertDate" is "NOT_NULL"
    And  I expect response field "updateDate" is "NOT_NULL"

    When I request "/dashboards/[dashboard_id]" with method "DELETE"
    Then I expect "204" as status code

