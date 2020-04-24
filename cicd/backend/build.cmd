@ECHO off

SETLOCAL EnableDelayedExpansion
SET FROM_PATH=%CD%
SET FROM_DRIVE=%CD:~0,3%
SET SCRIPT_PATH=%~dp0
SET SCRIPT_DRIVE=%SCRIPT_PATH:~0,3%

CD /D %SCRIPT_DRIVE%
CD %SCRIPT_PATH%

ECHO ================================================================================
ECHO Building and packaging the backend submodules
ECHO ================================================================================
CD ../..
CALL mvnw.cmd ^
  %MVN_CLI_OPTS% ^
  --activate-profiles cicd ^
  -Dquarkus.package.uber-jar=false ^
  -DskipTests ^
  package
IF !ERRORLEVEL! NEQ 0 (
  CD /D %FROM_DRIVE%
  CD %FROM_PATH%
  exit /b !ERRORLEVEL!
)
ECHO --------------------------------------------------------------------------------
ECHO The relevant build artifacts are available at:
ECHO     %CD%backend\target
ECHO --------------------------------------------------------------------------------
CD cicd/backend/deployments

CD %FROM_DRIVE%
CD %FROM_PATH%
IF %ERRORLEVEL% NEQ 0 EXIT /b %ERRORLEVEL%
ENDLOCAL