Feature: ProjectController, testing GET /projects/[id]


  Scenario: Get project with unknown id should raise an error
    Given I use basic authentication
    When I request "/projects" with method "POST" with json
      | key      | value | type    |
      | name     | test  | string  |
      | isMaster | true  | boolean |
    Then I expect "201" as status code
    And  I set response field "id" to context "project_id"

    When I request "/projects/[project_id]" with method "DELETE"
    Then I expect "204" as status code

    When I request "/projects/[project_id]"
    Then I expect "404" as status code
    And  I expect response field "field" is "id"
    And  I expect response field "message" is "Entity not found."

  Scenario: Get project with a valid id should be a success
    Given I use basic authentication
    When I request "/projects" with method "POST" with json
      | key      | value | type    |
      | name     | test  | string  |
      | isMaster | true  | boolean |
    Then I expect "201" as status code
    And  I set response field "id" to context "project_id"

    When I request "/projects/[project_id]"
    And  I expect response fields length is "5"
    And  I expect response field "id" is "NOT_NULL"
    And  I expect response field "name" is "test"
    And  I expect response field "color" is "NULL"
    And  I expect response field "insertDate" is "NOT_NULL"
    And  I expect response field "updateDate" is "NOT_NULL"

    When I request "/projects/[project_id]" with method "DELETE"
    Then I expect "204" as status code