Feature: Testing the Modulo actuators

Scenario: Get info actuator
  Given url 'http://localhost:8080/modulo/actuator/info'
  When method GET
  Then status 200
  And response.build.artifact == 'modulo-application'

Scenario: Get health actuator
  Given url 'http://localhost:8080/modulo/actuator/health'
  When method GET
  Then status 200
  And response.status == 'UP'
