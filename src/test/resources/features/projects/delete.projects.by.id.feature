Feature: ProjectController, testing DELETE /projects/[id]


  Scenario: Delete project without authentication should raise an error
    Given I use no authentication
    When I request "/projects/1" with method "DELETE"
    Then I expect "401" as status code

  Scenario: Delete project with unknown id should raise an error
    Given I use basic authentication
    When I request "/projects" with method "POST" with json
      | key      | value | type    |
      | name     | test  | string  |
      | isMaster | true  | boolean |
    Then I expect "201" as status code
    And  I set response field "id" to context "project_id"

    When I request "/projects/[project_id]" with method "DELETE"
    Then I expect "204" as status code

    When I request "/projects/[project_id]" with method "DELETE"
    Then I expect "404" as status code
    And  I expect response field "field" is "id"
    And  I expect response field "message" is "Entity not found."

  Scenario: Delete project with valid id should be a success
    Given I use basic authentication
    When I request "/projects" with method "POST" with json
      | key      | value | type    |
      | name     | test  | string  |
      | isMaster | true  | boolean |
    Then I expect "201" as status code
    And  I set response field "id" to context "project_id"

    When I request "/projects/[project_id]" with method "DELETE"
    Then I expect "204" as status code