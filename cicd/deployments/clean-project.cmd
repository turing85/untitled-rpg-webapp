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
echo Cleaning the whole project
echo ================================================================================
mvnw.cmd ^
  %MVN_CLI_OPTS% ^
  clean

cd %FROM_DRIVE%
cd %FROM_PATH%
ENDLOCAL