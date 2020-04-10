@echo off

SETLOCAL
SET FROM_PATH=%cd%
SET FROM_DRIVE=%cd:~0,3%
SET SCRIPT_PATH=%~dp0
SET SCRIPT_DRIVE=%SCRIPT_PATH:~0,3%

cd /D %SCRIPT_DRIVE%
cd %SCRIPT_PATH%

cd ../..
echo ================================================================================
echo Building the whole project
echo ================================================================================
mvnw.cmd ^
  %MVN_CLI_OPTS% ^
  -DskipTests ^
  package
echo --------------------------------------------------------------------------------
echo The relevant build artifacts can be found in
echo     %cd%backend\target
echo --------------------------------------------------------------------------------

cd %FROM_DRIVE%
cd %FROM_PATH%
ENDLOCAL