@ECHO off

SETLOCAL EnableDelayedExpansion
SET FROM_PATH=%CD%
SET FROM_DRIVE=%CD:~0,3%
SET SCRIPT_PATH=%~dp0
SET SCRIPT_DRIVE=%SCRIPT_PATH:~0,3%

CD /D %SCRIPT_DRIVE%
CD %SCRIPT_PATH%

ECHO ================================================================================
ECHO Building the project
ECHO ================================================================================
CD ../../..
CALL mvnw.cmd ^
  %MVN_CLI_OPTS% ^
  -DskipTests ^
  package
IF !ERRORLEVEL! NEQ 0 (
  CD /D %FROM_DRIVE%
  CD %FROM_PATH%
  exit /b !ERRORLEVEL!
)
CD cicd/backend/deployments
ECHO --------------------------------------------------------------------------------
ECHO The relevant build artifacts can be found in
ECHO     %CD%backend\target
ECHO --------------------------------------------------------------------------------

CD %FROM_DRIVE%
CD %FROM_PATH%
IF %ERRORLEVEL% NEQ 0 EXIT /b %ERRORLEVEL%
ENDLOCAL