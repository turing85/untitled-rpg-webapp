@ECHO off

SETLOCAL
SET FROM_PATH=%CD%
SET FROM_DRIVE=%CD:~0,3%
SET SCRIPT_PATH=%~dp0
SET SCRIPT_DRIVE=%SCRIPT_PATH:~0,3%

CD /D %SCRIPT_DRIVE%
CD %SCRIPT_PATH%

ECHO ================================================================================
ECHO Starting docker deployments: postgres and keycloak
ECHO ================================================================================
CALL docker-compose up -d dbms-service oidc-service
IF %errorlevel% neq 0 (
  CD /D %FROM_DRIVE%
  CD %FROM_PATH%
  exit /b %errorlevel%
)

IF %BUILD_PROJECT%==true (
  ECHO ================================================================================
  ECHO Building project and generating docker images
  ECHO ================================================================================
  CD ..
  CALL mvnw.cmd -Pdocker -P!unit-test-coverage -DskipTests package
  CD localdeployment
  IF %errorlevel% neq 0 (
    CD /D %FROM_DRIVE%
    CD %FROM_PATH%
    exit /b %errorlevel%
  )
)

ECHO ================================================================================
ECHO Migrating language database
ECHO ================================================================================
CD ..
CALL mvnw.cmd flyway:migrate --projects :deployments.quarkus.microservices.language.impl
CD localdeployment
IF %errorlevel% neq 0 (
  CD /D %FROM_DRIVE%
  CD %FROM_PATH%
  exit /b %errorlevel%
)

ECHO ================================================================================
ECHO Starting docker deployment: language-service
ECHO ================================================================================
CALL docker-compose up -d language-service
IF %errorlevel% neq 0 (
  CD /D %FROM_DRIVE%
  CD %FROM_PATH%
  exit /b %errorlevel%
)

ECHO ================================================================================
ECHO Migrating user database
ECHO ================================================================================
CD ..
CALL mvnw.cmd flyway:migrate --projects :deployments.quarkus.microservices.user.impl
CD localdeployment
IF %errorlevel% neq 0 (
  CD /D %FROM_DRIVE%
  CD %FROM_PATH%
  exit /b %errorlevel%
)

ECHO ================================================================================
ECHO Starting docker deployment: user-service
ECHO ================================================================================
CALL docker-compose up -d user-service

CD /D %FROM_DRIVE%
CD %FROM_PATH%
IF %errorlevel% NEQ 0 EXIT /b %errorlevel%
ENDLOCAL