Feature: InfoController, testing GET /info

  Scenario: Get info should be a success
    When I request "/info"
    Then I expect "200" as status code
    And  I expect response fields length is "4"
    And  I expect response field "project" is "octo-spy"
    And  I expect response field "version" is "NOT_NULL"
    And  I expect response field "environment" is "Development"
    And  I expect response field "client" is "Internal"