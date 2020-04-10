@echo off

SET FROM_PATH=%cd%
SET FROM_DRIVE=%cd:~0,3%
SET SCRIPT_PATH=%~dp0
SET SCRIPT_DRIVE=%CURRENT:~0,3%

cd /D %SCRIPT_DRIVE%
cd %SCRIPT_PATH%
echo ================================================================================
echo Shutting down docker deployments
echo ================================================================================
docker-compose down

cd /D %FROM_DRIVE%
cd %FROM_PATH%