#!/bin/bash

#Fail immediately on any error
#set -e

# This file is for preparing all the needed files and directories on the host.
# These directories are mounted into the docker containers.

SCRIPT_DIR=$(dirname $0)
SCRIPT_HOME="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
COMPOSE_HOME=$(cd "${SCRIPT_HOME}" && pwd)
ENV="${COMPOSE_HOME}/.env"

source "${ENV}"

export JF_PROJECT_NAME="rt"
export JF_ARTIFACTORY_USER=1030

. ${SCRIPT_HOME}/bin/systemYamlHelper.sh
. ${SCRIPT_HOME}/bin/dockerComposeHelper.sh #Important that this be included second - overwrites some methods

# Variables used for validations
RPM_DEB_RECOMMENDED_MIN_RAM=4194304          # 4G Total RAM => 4*1024*1024k=4194304
RPM_DEB_RECOMMENDED_MIN_CPU=2                # needs more than 2 CPU Cores

PRODUCT_NAME="$ARTIFACTORY_LABEL"
IS_POSTGRES_REQUIRED="$FLAG_N"

docker_hook_setDirInfo () {
  logDebug "Method docker_hook_setDirInfo"
  NGINX_DATA_ROOT="${JF_ROOT_DATA_DIR}${THIRDPARTY_DATA_ROOT}/nginx"
  POSTGRESQL_DATA_ROOT="${JF_ROOT_DATA_DIR}${THIRDPARTY_DATA_ROOT}/postgres"
}

docker_hook_postSystemYamlCreation () {
  logDebug "Method docker_hook_postSystemYamlCreation"
  _getDatabaseType
  if [ "$IS_POSTGRES_REQUIRED" == "$FLAG_Y" ] && [ "$DATABASE_TYPE" == "$SYS_KEY_SHARED_DATABASE_TYPE_VALUE_POSTGRES" ]; then
    docker_setSystemValue "$SYS_KEY_SHARED_DATABASE_USERNAME" "artifactory"
    docker_setSystemValue "$SYS_KEY_SHARED_DATABASE_URL" "jdbc:postgresql://$(wrapper_getHostIP):5432/artifactory"
  fi
  if [ "$JF_NODE_TYPE" = "$NODE_TYPE_CLUSTER_NODE" ]; then
    docker_setSystemValue "shared.node.haEnabled" "true"
  fi  
}

docker_hook_createDirectories() {
  logDebug "Method docker_hook_createDirectories"
  createRecursiveDir "${JF_ROOT_DATA_DIR}/var" "bootstrap artifactory tomcat lib" "$JF_ARTIFACTORY_USER" "${JF_ARTIFACTORY_GROUP}"
  createRecursiveDir "${JF_ROOT_DATA_DIR}/var" "bootstrap access etc security" "${JF_ARTIFACTORY_USER}" "${JF_ARTIFACTORY_GROUP}" >/dev/null 2>&1 || true
}

docker_hook_copyComposeFile() {
  logDebug "Method docker_hook_copyComposeFile"
  docker_setUpPostgresCompose

  local sourceFile="$COMPOSE_TEMPLATES/docker-compose.yaml"
  local targetFile="$COMPOSE_HOME/docker-compose.yaml"
  logDebug "Copying [$sourceFile] as [$targetFile]"
  cp "${sourceFile}" "${targetFile}"
  # remove volume /etc/localtime in docker-compose.yaml, if running on mac (RT)
  local OS_TYPE=$(uname)
  if [[ "${OS_TYPE}" == "Darwin" ]]; then
      removeYamlValue "services.artifactory.volumes[1]" ${targetFile}
  fi
}

#The folders will be changed to be owned by this user
DOCKER_USER="${JF_ARTIFACTORY_USER}"

FEATURE_FLAG_USE_WRAPPER="$FLAG_Y"
# Controls the variable used for healthcheck and display
SERVER_URL="http://$(wrapper_getHostIP):${JF_ROUTER_ENTRYPOINTS_EXTERNALPORT}"
PROJECT_ROOT_FOLDER="artifactory"
EXTERNAL_DATABASES="$DATABASE_POSTGRES"
MIGRATION_SUPPORTED="$FLAG_Y"
FLAG_MULTIPLE_DB_SUPPORT="$FLAG_Y"
SUPPORTED_DATABASE_TYPES="$SYS_KEY_SHARED_DATABASE_TYPE_VALUE_POSTGRES \
$SYS_KEY_SHARED_DATABASE_TYPE_VALUE_MSSQL \
$SYS_KEY_SHARED_DATABASE_TYPE_VALUE_MARIADB \
$SYS_KEY_SHARED_DATABASE_TYPE_VALUE_MYSQL \
$SYS_KEY_SHARED_DATABASE_TYPE_VALUE_ORACLE \
$SYS_KEY_SHARED_DATABASE_TYPE_VALUE_DERBY"

#Trigger the script
docker_main $*