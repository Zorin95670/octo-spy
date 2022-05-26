Feature: DashboardController, testing GET /dashboards

  Scenario: Get a visible dashboard with a valid search id should be a success
    Given I use basic authentication
    And I clean dashboard "test"

    When I request "/dashboards" with method "POST" with json
      | key        | value         | type    |
      | name       | test          | string  |
      | parameters | { "id": "1" } | object  |
      | visible    | true          | boolean |
    Then I expect "201" as status code
    And  I set response field "id" to context "dashboard_id"

    When I request "/dashboards" with query parameters
      | key | value          |
      | id  | [dashboard_id] |
    Then I expect response field "totalElements" is "1"
    And  I extract resources from response
    And  I expect one resource contains "id" equals to "[dashboard_id]" as "integer"

    When I request "/dashboards/[dashboard_id]" with method "DELETE"
    Then I expect "204" as status code


  Scenario: Get dashboards with without visibility filter should only retrieve visible dashboard
    Given I use basic authentication
    And I clean dashboard "lk_test*"

    When I request "/dashboards" with method "POST" with json
      | key        | value         | type    |
      | name       | test1         | string  |
      | parameters | { "id": "1" } | object  |
      | visible    | true          | boolean |
    Then I expect "201" as status code
    And  I set response field "id" to context "dashboard1_id"

    When I request "/dashboards" with method "POST" with json
      | key        | value         | type    |
      | name       | test2         | string  |
      | parameters | { "id": "1" } | object  |
      | visible    | false         | boolean |
    Then I expect "201" as status code
    And  I set response field "id" to context "dashboard2_id"

    When I request "/dashboards" with query parameters
      | key  | value    |
      | name | lk_test* |
    Then I expect response field "totalElements" is "1"
    And  I extract resources from response
    And  I expect one resource contains "id" equals to "[dashboard1_id]" as "integer"

    When I request "/dashboards/[dashboard1_id]" with method "DELETE"
    Then I expect "204" as status code

    When I request "/dashboards/[dashboard2_id]" with method "DELETE"
    Then I expect "204" as status code

  Scenario: Get a not visible dashboard should return an empty list
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
    When I request "/dashboards" with query parameters
      | key | value          |
      | id  | [dashboard_id] |
    Then I expect response field "totalElements" is "0"

    Given I use basic authentication
    When I request "/dashboards/[dashboard_id]" with method "DELETE"
    Then I expect "204" as status code