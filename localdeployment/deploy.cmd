@echo off

SET FROM_PATH=%cd%
SET FROM_DRIVE=%cd:~0,3%
SET SCRIPT_PATH=%~dp0
SET SCRIPT_DRIVE=%SCRIPT_PATH:~0,3%

cd /D %SCRIPT_DRIVE%
cd %SCRIPT_PATH%
echo ================================================================================
echo Starting docker deployments
echo ================================================================================
docker-compose up -d

cd ..
echo ================================================================================
echo Migrating language database
echo ================================================================================
call mvnw.cmd flyway:migrate --projects :deployments.quarkus.microservices.language.impl

echo ================================================================================
echo Migrating user database
echo ================================================================================
call mvnw.cmd flyway:migrate --projects :deployments.quarkus.microservices.user.impl

cd /D %FROM_DRIVE%
cd %FROM_PATH%