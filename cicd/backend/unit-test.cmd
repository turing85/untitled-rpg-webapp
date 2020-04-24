@ECHO off

SETLOCAL EnableDelayedExpansion
SET FROM_PATH=%CD%
SET FROM_DRIVE=%CD:~0,3%
SET SCRIPT_PATH=%~dp0
SET SCRIPT_DRIVE=%SCRIPT_PATH:~0,3%

CD /D %SCRIPT_DRIVE%
CD %SCRIPT_PATH%

ECHO ================================================================================
ECHO Executing unit tests for the backend submodules
ECHO ================================================================================
CD ../..
CALL mvnw.cmd ^
  %MVN_CLI_OPTS% ^
  --activate-profiles cicd,!unit-test-coverage ^
  -DskipTests=false ^
  test
IF !ERRORLEVEL! NEQ 0 (
  CD /D %FROM_DRIVE%
  CD %FROM_PATH%
  exit /b !ERRORLEVEL!
)
ECHO --------------------------------------------------------------------------------
ECHO Surefire test reports for the backend submodules are available at:
ECHO     %CD%\target\surefire-reports
ECHO --------------------------------------------------------------------------------
CD cicd/backend/deployments

CD %FROM_DRIVE%
CD %FROM_PATH%
IF %ERRORLEVEL% NEQ 0 EXIT /b %ERRORLEVEL%
ENDLOCAL