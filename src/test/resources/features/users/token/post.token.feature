Feature: ProjectController, testing POST /projects


  Scenario: Create project without authentication should raise an error
    Given I use no authentication
    When I request "/projects" with method "POST" with json
      | key      | value | type    |
      | name     | test  | string  |
      | isMaster | true  | boolean |
    Then I expect "401" as status code

  Scenario: Create a project without name should raise an error
    Given I use basic authentication
    When I request "/projects" with method "POST" with json
      | key      | value | type    |
      | isMaster | true  | boolean |
    Then I expect "400" as status code
    And  I expect response field "field" is "name"
    And  I expect response field "message" is "Field value is empty."

  Scenario: Create two master project with same name should raise an error
    Given I use basic authentication
    And   I clean project "test"

    When I request "/projects" with method "POST" with json
      | key      | value | type    |
      | name     | test  | string  |
      | isMaster | true  | boolean |
    Then I expect "201" as status code
    And  I set response field "id" to context "project_id"

    When I request "/projects" with method "POST" with json
      | key      | value | type    |
      | name     | test  | string  |
      | isMaster | true  | boolean |
    Then I expect "400" as status code
    And  I expect response field "field" is "name"
    And  I expect response field "value" is "duplicate"
    And  I expect response field "message" is "Wrong field value."

    When I request "/projects/[project_id]" with method "DELETE"
    Then I expect "204" as status code

  Scenario: Create two sub-project with same name in the same master project should raise an error
    Given I use basic authentication
    And   I clean project "master"

    When I request "/projects" with method "POST" with json
      | key      | value  | type    |
      | name     | master | string  |
      | isMaster | true   | boolean |
    Then I expect "201" as status code
    And  I set response field "id" to context "master_project_id"

    When I request "/projects" with method "POST" with json
      | key        | value      | type    |
      | name       | subProject | string  |
      | isMaster   | false      | boolean |
      | masterName | master     | string  |
    Then I expect "201" as status code

    When I request "/projects" with method "POST" with json
      | key        | value      | type    |
      | name       | subProject | string  |
      | isMaster   | false      | boolean |
      | masterName | master     | string  |
    Then I expect "400" as status code
    And  I expect response field "field" is "name"
    And  I expect response field "value" is "duplicate"
    And  I expect response field "message" is "Wrong field value."

    When I request "/projects/[master_project_id]" with method "DELETE"
    Then I expect "204" as status code

  Scenario: Create a valid project should be a success
    Given I use basic authentication
    And   I clean project "test"

    When I request "/projects" with method "POST" with json
      | key      | value | type    |
      | name     | test  | string  |
      | color    | 0,0,0 | string  |
      | isMaster | true  | boolean |
    Then I expect "201" as status code
    And  I set response field "id" to context "project_id"
    And  I expect response fields length is "5"
    And  I expect response field "id" is "NOT_NULL"
    And  I expect response field "name" is "test"
    And  I expect response field "color" is "0,0,0"
    And  I expect response field "insertDate" is "NOT_NULL"
    And  I expect response field "updateDate" is "NOT_NULL"

    When I request "/projects/[project_id]" with method "DELETE"
    Then I expect "204" as status code

  Scenario: Create a project on a master project should be a success
    Given I use basic authentication
    And   I clean project "master"

    When I request "/projects" with method "POST" with json
      | key      | value  | type    |
      | name     | master | string  |
      | color    | 0,0,0  | string  |
      | isMaster | true   | boolean |
    Then I expect "201" as status code
    And  I set response field "id" to context "master_project_id"

    When I request "/projects" with method "POST" with json
      | key        | value      | type    |
      | name       | subProject | string  |
      | isMaster   | false      | boolean |
      | masterName | master     | string  |
    Then I expect "201" as status code
    And  I expect response fields length is "5"
    And  I expect response field "id" is "NOT_NULL"
    And  I expect response field "name" is "subProject"
    And  I expect response field "color" is "NULL"
    And  I expect response field "insertDate" is "NOT_NULL"
    And  I expect response field "updateDate" is "NOT_NULL"

    When I request "/projects/[master_project_id]" with method "DELETE"
    Then I expect "204" as status code