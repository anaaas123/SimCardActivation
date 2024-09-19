Feature: SIM Card Activation

  Scenario: Successful SIM card activation
    Given a SIM card with ICCID "1255789453849037777"
    When I activate the SIM card
    Then the activation should be successful
    And the SIM card should be active

  Scenario: Failed SIM card activation
    Given a SIM card with ICCID "8944500102198304826"
    When I activate the SIM card
    Then the activation should fail
    And the SIM card should not be active
