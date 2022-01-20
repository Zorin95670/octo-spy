Feature: UserController, testing GET /users/me


  Scenario: Get my user information without authentication should raise an error
    Given I use no authentication
    When I request "/users/me"
    Then I expect "401" as status code

  Scenario: Get my user information should be a success
    Given I use basic authentication
    When I request "/users/me"
    Then I expect "200" as status code
    And I expect response field "roles" is "[\"ADMIN\"]" as "array"
    And I extract object "user" from response
    And I expect object fields length is "7"
    And I expect object field "login" is "admin" as "string"
    And I expect object field "firstname" is "Administrator" as "string"
    And I expect object field "lastname" is "NULL"
    And I expect object field "email" is "no-reply@change.it" as "string"
    And I expect object field "active" is "true" as "boolean"
    And I expect object field "insertDate" is "NOT_NULL"
    And I expect object field "updateDate" is "NOT_NULL"
