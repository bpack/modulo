Feature: Testing the Modulo user API

Background:
  * url base_url + base_path
  * def rand_user = function(){ return 'user-' + java.util.UUID.randomUUID().toString().substring(0, 8)}

Scenario: Test create and retrieve user and location
  Given header Authorization = call read('auth.js')
  And def rand = rand_user()
  And request { username: '#(rand)', email: 'email@example.com', ip: '216.115.122.132' }
  When method POST
  Then status 201
  And match response.id == '#notnull'

  Given path response.id
  When method GET
  Then status 200

  Given path response.id + '/location'
  When method GET
  Then status 200
  And match response.country == 'United States'


Scenario: Test create and update user
  Given header Authorization = call read('auth.js')
  And def rand = rand_user()
  And request { username: '#(rand)', email: 'user@example.com', ip: '216.115.122.132' }
  When method POST
  Then status 201
  And match response.id == '#notnull'

  Given path response.id
  And header Authorization = call read('auth.js')
  And request { email: 'user_updated@example.com', ip: '125.125.125.125' }
  When method PUT
  Then status 200
  And match response.email == 'user_updated@example.com'


Scenario: Test get user list
  Given header Authorization = call read('auth.js')
  When method GET
  Then status 200
