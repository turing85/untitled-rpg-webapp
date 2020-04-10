echo ================================================================================
echo Starting docker deployments
echo ================================================================================
docker-compose up -d

cd ..
echo ================================================================================
echo Migrating language database
echo ================================================================================
mvnw.cmd flyway:migrate --projects :deployments.quarkus.microservices.language.impl

echo ================================================================================
echo Migrating user database
echo ================================================================================
mvnw.cmd flyway:migrate --projects :deployments.quarkus.microservices.user.impl
