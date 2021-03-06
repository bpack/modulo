Feature: Testing various error conditions

Background:
  * url base_url + base_path

Scenario: Test unauthenticated access
  When method GET
  Then status 401


Scenario: Test user not found
  Given path '00000000-1111-2222-3333-444444555555'
  And header Authorization = call read('auth.js')
  When method GET
  Then status 404
  And response.error == 'Not Found'
  And response.timestamp == '#notnull'


Scenario: Test not id allowed on create
  Given header Authorization = call read('auth.js')
  And request { id: 'disallow', username: 'invalid', email: 'email@example.com', ip: '216.115.122.132' }
  When method POST
  Then status 400
  And response.error == 'Bad Request'

