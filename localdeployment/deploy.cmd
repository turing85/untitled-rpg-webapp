@ECHO off

SET ERRORLEVEL=

SETLOCAL EnableDelayedExpansion

SET FROM_PATH=%CD%
SET FROM_DRIVE=%CD:~0,3%
SET SCRIPT_PATH=%~dp0
SET SCRIPT_DRIVE=%SCRIPT_PATH:~0,3%

CD /D %SCRIPT_DRIVE%
CD %SCRIPT_PATH%

ECHO ================================================================================
ECHO Starting docker deployments: jaeger, postgres and keycloak
ECHO ================================================================================
CALL docker-compose up -d tracing-service dbms-service oidc-service
IF !ERRORLEVEL! NEQ 0 (
  CD /D %FROM_DRIVE%
  CD %FROM_PATH%
  exit /b !ERRORLEVEL!
)

IF "%BUILD_PROJECT%" == "true" (
  ECHO ================================================================================
  ECHO Building project and generating docker images
  ECHO ================================================================================
  SET MIGRATE_DATABASES=true
  CD ..
  CALL mvnw.cmd -Pdocker -P!unit-test-coverage -DskipTests install
  IF !ERRORLEVEL! NEQ 0 (
    CD /D %FROM_DRIVE%
    CD %FROM_PATH%
    exit /b %ERRORLEVEL%
  )
  CD localdeployment
)

IF "!MIGRATE_DATABASES!" == "true" (
  ECHO ================================================================================
  ECHO Migrating language database
  ECHO ================================================================================
  CD ..
  CALL mvnw.cmd ^
    --projects :untitled-rpg-webapp.backend.deployments.quarkus.microservices.language.impl ^
    flyway:migrate
  IF !ERRORLEVEL! NEQ 0 (
    CD /D %FROM_DRIVE%
    CD %FROM_PATH%
    exit /b %ERRORLEVEL%
  )
  CD localdeployment
)

ECHO ================================================================================
ECHO Starting docker deployment: language-service
ECHO ================================================================================
CALL docker-compose up -d language-service
IF !ERRORLEVEL! neq 0 (
  CD /D %FROM_DRIVE%
  CD %FROM_PATH%
  exit /b %ERRORLEVEL%
)

IF "!MIGRATE_DATABASES!" == "true" (
  ECHO ================================================================================
  ECHO Migrating user database
  ECHO ================================================================================
  CD ..
  CALL mvnw.cmd ^
    --projects :untitled-rpg-webapp.backend.deployments.quarkus.microservices.user.impl ^
    flyway:migrate
  CD localdeployment
  IF !ERRORLEVEL! neq 0 (
    CD /D %FROM_DRIVE%
    CD %FROM_PATH%
    exit /b %ERRORLEVEL%
  )
)

ECHO ================================================================================
ECHO Starting docker deployment: user-service
ECHO ================================================================================
CALL docker-compose up -d user-service

CD /D %FROM_DRIVE%
CD %FROM_PATH%
IF !ERRORLEVEL! NEQ 0 EXIT /b %ERRORLEVEL%
ENDLOCAL