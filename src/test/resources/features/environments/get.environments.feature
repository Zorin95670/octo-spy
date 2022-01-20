Feature: EnvironmentController, testing GET /environments

  Scenario: Get all environments should be a success
    Given I use no authentication
    When I request "/environments"
    Then I expect response field "totalElements" is "4"
    And  I extract resources from response
    And  I expect one resource contains "name" equals to "Development"
    And  I expect one resource contains "name" equals to "Integration"
    And  I expect one resource contains "name" equals to "Pre-production"
    And  I expect one resource contains "name" equals to "Production"