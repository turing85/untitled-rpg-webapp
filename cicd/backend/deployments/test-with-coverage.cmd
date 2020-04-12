@ECHO off

SETLOCAL
SET FROM_PATH=%CD%
SET FROM_DRIVE=%CD:~0,3%
SET SCRIPT_PATH=%~dp0
SET SCRIPT_DRIVE=%SCRIPT_PATH:~0,3%

CD /D %SCRIPT_DRIVE%
CD %SCRIPT_PATH%

CD ../../..
ECHO ================================================================================
ECHO Running unit tests with coverage report
ECHO ================================================================================
CALL mvnw.cmd ^
  %MVN_CLI_OPTS% ^
  -DskipTests=false ^
  --activate-profiles unit-test-coverage ^
  verify
ECHO --------------------------------------------------------------------------------
ECHO Test reports for the backenc are available at:
ECHO     %CD%\backend\testaggregation\target\site\jacoco-aggregate-ut\index.html
ECHO --------------------------------------------------------------------------------

CD %FROM_DRIVE%
CD %FROM_PATH%
IF %ERRORLEVEL% NEQ 0 EXIT /b %ERRORLEVEL%
ENDLOCAL