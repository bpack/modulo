Feature: Testing the Modulo user deletion workflow

Background:
  * def rand_user = function(){ return 'user-' + java.util.UUID.randomUUID().toString().substring(0, 8)}
  * def user = callonce rand_user

Scenario: Test create and delete of a user
  Given url 'http://localhost:8080/modulo/api/user/'
  And header Authorization = call read('auth.js')
  And request { username: '#(user)', email: 'email@example.com', ip: '216.115.122.132' }
  When method POST
  Then status 201
  And match response.user.id == '#notnull'

  Given path response.user.id
  And def user_id = response.user.id
  When method GET
  Then status 200

  Given path response.user.id
  When method DELETE
  Then status 204

  Given url 'http://localhost:8080/modulo/api/user/' + user_id
  When method GET
  Then status 404

Scenario: Test create with deleted username fails
  Given url 'http://localhost:8080/modulo/api/user/'
  And header Authorization = call read('auth.js')
  And request { username: '#(user)', email: 'email@example.com', ip: '216.115.122.132' }
  When method POST
  # TODO: This should be an invalid request, not a server error
  Then status 500


