Feature: ClientController, testing GET /clients

  Scenario: Get clients should be a success
    When I request "/clients"
    Then I expect "200" as status code
    And  I expect response is "array"
    And  I expect response is "empty"

  Scenario: Creating deployments with client and expect get clients return it
    Given I use basic authentication
    When I request "/projects" with method "POST" with json
      | key      | value | type    |
      | name     | test  | string  |
      | isMaster | true  | boolean |
    Then I expect "201" as status code
    And  I set response field "id" to context "project_id"

    When I request "/deployments" with method "POST" with json
      | key         | value       | type    |
      | project     | test        | string  |
      | environment | Development | string  |
      | client      | TestClient  | string  |
      | version     | 1.0.0       | string  |
      | alive       | true        | boolean |
    Then I expect "201" as status code

    When I request "/clients"
    Then I expect "200" as status code
    And  I expect response is "array"
    And  I expect response json is '["TestClient"]'

    When I request "/projects/[project_id]" with method "DELETE"
    Then I expect "204" as status code

    When I request "/clients"
    Then I expect "200" as status code
    And  I expect response is "array"
    And  I expect response is "empty"