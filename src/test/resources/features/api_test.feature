Feature: API test
  Scenario: user gets a random joke
    When user sends request to get a random joke
    Then response status should be 200
    And returned JSON should match the schema '/schemas/joke.json'
    And joke is valid

  Scenario: user gets a random programming joke
    When user sends request to get a random 'programming' joke
    Then response status should be 200
    And returned JSON should match the schema '/schemas/jokes_array.json'
    And all jokes are valid

  Scenario: user gets ten random programming jokes
    When user sends request to get ten random 'programming' jokes
    Then response status should be 200
    And returned JSON should match the schema '/schemas/jokes_array.json'
    And all jokes are valid

  Scenario: user gets a random joke by id
    When user sends request to get a random joke by id 375
    Then response status should be 200
    And returned JSON should match the schema '/schemas/joke.json'
    And joke is valid

  Scenario: user gets a random programming joke with invalid type
    When user sends request to get a random 'invalid type' joke
    Then response status should be 200
    And returned JSON should match the schema '/schemas/jokes_array.json'
    And response should be empty

  Scenario: user gets a random programming joke without type
    When user sends request to get a random joke without type
    Then response status should be 404

  Scenario: user gets a random joke by invalid id
    When user sends request to get a random joke by id 375555
    Then response status should be 404
    And returned JSON should match the schema '/schemas/message.json'
    And message with type 'error' should say 'joke not found'











