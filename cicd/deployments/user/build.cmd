@echo off

SETLOCAL
SET FROM_PATH=%cd%
SET FROM_DRIVE=%cd:~0,3%
SET SCRIPT_PATH=%~dp0
SET SCRIPT_DRIVE=%SCRIPT_PATH:~0,3%

cd /D %SCRIPT_DRIVE%
cd %SCRIPT_PATH%
SET SERVICE=user
../../helper/build-service.cmd

cd /D %FROM_DRIVE%
cd %FROM_PATH%
ENDLOCAL