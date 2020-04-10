@echo off

SETLOCAL
SET FROM_PATH=%cd%
SET FROM_DRIVE=%cd:~0,3%
SET SCRIPT_PATH=%~dp0
SET SCRIPT_DRIVE=%CURRENT:~0,3%

cd /D %SCRIPT_DRIVE%
cd %SCRIPT_PATH%

cd ../..
echo ================================================================================
echo Running unit tests with coverage report
echo ================================================================================
call mvnw.cmd ^
  %MVN_CLI_OPTS% ^
  -DskipTests=false ^
  --activate-profiles unit-test-coverage ^
  verify
echo --------------------------------------------------------------------------------
echo Test reports for the backenc are available at:
echo     %cd%\backend\testaggregation\target\site\jacoco-aggregate-ut\index.html
echo --------------------------------------------------------------------------------

cd %FROM_DRIVE%
cd %FROM_PATH%
ENDLOCAL