Feature: DashboardController, testing GET /dashboards/[id]


  Scenario: Get dashboard with unknown id should raise an error
    Given I use basic authentication
    And I clean dashboard "test"

    When I request "/dashboards" with method "POST" with json
      | key        | value         | type    |
      | name       | test          | string  |
      | parameters | { "id": "1" } | object  |
      | visible    | true          | boolean |
    Then I expect "201" as status code
    And  I set response field "id" to context "dashboard_id"

    When I request "/dashboards/[dashboard_id]" with method "DELETE"
    Then I expect "204" as status code

    When I request "/dashboards/[dashboard_id]"
    Then I expect "404" as status code
    And  I expect response field "field" is "id"
    And  I expect response field "message" is "Entity not found."

  Scenario: Get dashboard with a valid id should be a success
    Given I use basic authentication
    And I clean dashboard "test"

    When I request "/dashboards" with method "POST" with json
      | key        | value         | type    |
      | name       | test          | string  |
      | parameters | { "id": "1" } | object  |
      | visible    | true          | boolean |
    Then I expect "201" as status code
    And  I set response field "id" to context "dashboard_id"

    When I request "/dashboards/[dashboard_id]"
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

  Scenario: Get a not visible dashboard as no user should raise an error
    Given I use basic authentication
    And I clean dashboard "test"

    When I request "/dashboards" with method "POST" with json
      | key        | value         | type    |
      | name       | test          | string  |
      | parameters | { "id": "1" } | object  |
      | visible    | false         | boolean |
    Then I expect "201" as status code
    And  I set response field "id" to context "dashboard_id"

    Given I use no authentication
    When I request "/dashboards/[dashboard_id]"
    Then I expect "404" as status code
    And  I expect response field "field" is "id"
    And  I expect response field "message" is "Entity not found."

    Given I use basic authentication
    When I request "/dashboards/[dashboard_id]" with method "DELETE"
    Then I expect "204" as status code