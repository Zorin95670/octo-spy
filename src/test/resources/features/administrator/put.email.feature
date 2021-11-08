Feature: AdministratorController, testing PUT /email


  Scenario: Saving email without authentication should raise an error
    Given I use no authentication
    When I request "/administrator/email" with method "PUT" with body
      | value | type   |
      | test  | string |
    Then I expect "401" as status code

  Scenario: Saving bad email should raise an error
    Given I use basic authentication
    When I request "/administrator/email" with method "PUT" with body
      | value | type   |
      | test  | string |
    Then I expect "400" as status code
    And  I expect response field "field" is "Email."
    And  I expect response field "message" is "Wrong field value."

  Scenario: Saving valid email should be a success
    Given I use basic authentication
    When I request "/administrator/email" with method "PUT" with body
      | value          | type   |
      | test@gmail.com | string |
    Then I expect "204" as status code
