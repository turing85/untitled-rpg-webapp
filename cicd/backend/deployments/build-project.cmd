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
ECHO Building the whole project
ECHO ================================================================================
CALL mvnw.cmd ^
  %MVN_CLI_OPTS% ^
  -DskipTests ^
  package
ECHO --------------------------------------------------------------------------------
ECHO The relevant build artifacts can be found in
ECHO     %CD%backend\target
ECHO --------------------------------------------------------------------------------

CD %FROM_DRIVE%
CD %FROM_PATH%
IF %ERRORLEVEL% NEQ 0 EXIT /b %ERRORLEVEL%
ENDLOCAL