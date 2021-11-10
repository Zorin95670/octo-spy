Feature: AdministratorController, testing PUT /password


  Scenario: Saving password without authentication should raise an error
    Given I use no authentication
    When I request "/administrator/password" with method "PUT" with body
      | value | type   |
      | test  | BASE64 |
    Then I expect "401" as status code

  Scenario: Saving empty password should raise an error
    Given I use basic authentication
    When I request "/administrator/password" with method "PUT" with body
      | value | type   |
      | EMPTY | BASE64 |
    Then I expect "400" as status code
    And  I expect response field "field" is "password"
    And  I expect response field "message" is "Field value is empty."

  Scenario: Saving too short password should raise an error
    Given I use basic authentication
    When I request "/administrator/password" with method "PUT" with body
      | value | type   |
      | test  | BASE64 |
    Then I expect "400" as status code
    And  I expect response field "field" is "Password length."
    And  I expect response field "message" is "Wrong field value."

  Scenario: Saving too long password should raise an error
    Given I use basic authentication
    When I request "/administrator/password" with method "PUT" with body
      | value                                               | type   |
      | TOO_LONG_PASSWORDXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX | BASE64 |
    Then I expect "400" as status code
    And  I expect response field "field" is "Password length."
    And  I expect response field "message" is "Wrong field value."

  Scenario: Saving valid password should be a success
    Given I use basic authentication
    When I request "/administrator/password" with method "PUT" with body
      | value         | type   |
      | Good_password | BASE64 |
    Then I expect "204" as status code
    And I set to context "password" with "Good_password"
