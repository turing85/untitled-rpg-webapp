@echo off

if [%SERVICE%] == [] (
  echo "variable SERVICE is not set. It must be set to the service's name that should be compiled."
  return 1
)

SETLOCAL
SET FROM_PATH=%cd%
SET FROM_DRIVE=%cd:~0,3%
SET SCRIPT_PATH=%~dp0
SET SCRIPT_DRIVE=%CURRENT:~0,3%

cd %SCRIPT_DRIVE%
cd %SCRIPT_PATH%

cd ../..
echo ================================================================================
echo Building service %SERVICE%
echo ================================================================================
call mvnw.cmd ^
  %MVN_CLI_OPTS% ^
  -DskipTests ^
  --projects :deployments.quarkus.microservices.%SERVICE%.impl ^
  package
echo --------------------------------------------------------------------------------
echo The build artifact for %SERVICE% is available at
echo     %cd%\target\%SERVICE%Service-runner.jar
echo --------------------------------------------------------------------------------

cd %FROM_DRIVE%
cd %FROM_PATH%
ENDLOCAL