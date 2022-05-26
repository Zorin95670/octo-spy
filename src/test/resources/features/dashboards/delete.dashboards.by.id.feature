Feature: DashboardController, testing DELETE /dashboards/[id]


  Scenario: Delete dashboard without authentication should raise an error
    Given I use no authentication
    When I request "/dashboards/1" with method "DELETE"
    Then I expect "401" as status code

  Scenario: Delete dashboard with unknown id should raise an error
    Given I use basic authentication
    And   I clean dashboard "test"

    When I request "/dashboards" with method "POST" with json
      | key        | value         | type    |
      | name       | test          | string  |
      | parameters | { "id": "1" } | object  |
      | visible    | false         | boolean |
    Then I expect "201" as status code
    And  I set response field "id" to context "dashboard_id"

    When I request "/dashboards/[dashboard_id]" with method "DELETE"
    Then I expect "204" as status code

    When I request "/dashboards/[dashboard_id]" with method "DELETE"
    Then I expect "404" as status code
    And  I expect response field "field" is "id"
    And  I expect response field "message" is "Entity not found."

  Scenario: Delete dashboard with valid id should be a success
    Given I use basic authentication
    And   I clean dashboard "test"

    When I request "/dashboards" with method "POST" with json
      | key        | value         | type    |
      | name       | test          | string  |
      | parameters | { "id": "1" } | object  |
      | visible    | false         | boolean |
    Then I expect "201" as status code
    And  I set response field "id" to context "dashboard_id"

    When I request "/dashboards/[dashboard_id]" with method "DELETE"
    Then I expect "204" as status code

  Scenario: Default dashboard can't be deleted
    Given I use basic authentication

    When I request "/dashboards" with query parameters
      | key   | value               |
      | name  | All master projects |
      | sort  | asc                 |
      | order | id                  |
      | count | 1                   |
    Then I extract first resource from response
    And  I set response field "id" to context "dashboard_id"

    When I request "/dashboards/[dashboard_id]" with method "DELETE"
    Then I expect "400" as status code
    And  I expect response field "field" is "canBeDeleted"
    And  I expect response field "value" is "false"
    And  I expect response field "message" is "Wrong field value."