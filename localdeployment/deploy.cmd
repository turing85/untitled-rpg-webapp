@ECHO off

SET FROM_PATH=%CD%
SET FROM_DRIVE=%CD:~0,3%
SET SCRIPT_PATH=%~dp0
SET SCRIPT_DRIVE=%SCRIPT_PATH:~0,3%

CD /D %SCRIPT_DRIVE%
CD %SCRIPT_PATH%
ECHO ================================================================================
ECHO Starting docker deployments
ECHO ================================================================================
docker-compose up -d
IF %errorlevel% neq 0 (
  CD /D %FROM_DRIVE%
  CD %FROM_PATH%
  exit /b %errorlevel%
)

CD ..
ECHO ================================================================================
ECHO Migrating language database
ECHO ================================================================================
CALL mvnw.cmd flyway:migrate --projects :deployments.quarkus.microservices.language.impl

ECHO ================================================================================
ECHO Migrating user database
ECHO ================================================================================
CALL mvnw.cmd flyway:migrate --projects :deployments.quarkus.microservices.user.impl

CD /D %FROM_DRIVE%
CD %FROM_PATH%
IF %errorlevel% NEQ 0 EXIT /b %errorlevel%