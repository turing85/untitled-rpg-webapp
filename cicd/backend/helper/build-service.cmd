@ECHO off

if [%SERVICE%] == [] (
  ECHO "variable SERVICE is not set. It must be set to the service's name that should be compiled."
  return 1
)

SETLOCAL EnableDelayedExpansion
SET FROM_PATH=%cd%
SET FROM_DRIVE=%cd:~0,3%
SET SCRIPT_PATH=%~dp0
SET SCRIPT_DRIVE=%CURRENT:~0,3%

CD %SCRIPT_DRIVE%
CD %SCRIPT_PATH%

CD ../../..
ECHO ================================================================================
ECHO Building service %SERVICE%
ECHO ================================================================================
CALL mvnw.cmd ^
  %MVN_CLI_OPTS% ^
  -DskipTests ^
  --projects :untitled-rpg-webapp.backend.deployments.quarkus.microservices.%SERVICE%.impl ^
  package
IF !ERRORLEVEL! NEQ 0 (
  CD /D %FROM_DRIVE%
  CD %FROM_PATH%
  exit /b !ERRORLEVEL!
)
ECHO --------------------------------------------------------------------------------
ECHO The build artifact for %SERVICE% is available at
ECHO     %cd%\backend\target\%SERVICE%Service-runner.jar
ECHO --------------------------------------------------------------------------------

CD %FROM_DRIVE%
CD %FROM_PATH%
ENDLOCAL