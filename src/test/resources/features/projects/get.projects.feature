Feature: ProjectController, testing GET /projects

  Scenario: Get project with a valid search id should be a success
    Given I use basic authentication
    When I request "/projects" with method "POST" with json
      | key      | value | type    |
      | name     | test  | string  |
      | isMaster | true  | boolean |
    Then I expect "201" as status code
    And  I set response field "id" to context "project_id"

    When I request "/projects" with query parameters
      | key | value        |
      | id  | [project_id] |
    Then I expect response field "total" is "1"
    And  I extract resources from response
    And  I expect one resource contains "id" equals to "[project_id]" as "integer"

    When I request "/projects/[project_id]" with method "DELETE"
    Then I expect "204" as status code