#!/bin/bash

# Flags
FLAG_Y="y"
FLAG_N="n"
FLAGS_Y_N="$FLAG_Y $FLAG_N"
FLAG_NOT_APPLICABLE="_NA_"

WRAPPER_SCRIPT_TYPE_RPMDEB="RPMDEB"
WRAPPER_SCRIPT_TYPE_DOCKER_COMPOSE="DOCKERCOMPOSE"

SENSITIVE_KEY_VALUE="__sensitive_key_hidden___"

# Shared system keys
SYS_KEY_SHARED_JFROGURL="shared.jfrogUrl"
SYS_KEY_SHARED_JFROGURLTIMEOUT="shared.jfrogUrlTimeOut"
SYS_KEY_SHARED_SECURITY_JOINKEY="shared.security.joinKey"
SYS_KEY_SHARED_SECURITY_MASTERKEY="shared.security.masterKey"

SYS_KEY_PDNNODE_PDNSERVERURL="pdnNode.pdnServerUrl"
SYS_KEY_PDNNODE_JOINKEY="pdnNode.joinKey"
SYS_KEY_PDNNODE_SELFGRPCADDRESS="pdnNode.selfGrpcAddress"
SYS_KEY_PDNNODE_SELFHTTPADDRESS="pdnNode.selfHttpAddress"
SYS_KEY_PDNNODE_PORT="pdnNode.port"
SYS_KEY_PDNNODE_HTTPPORT="pdnNode.httpPort"
SYS_KEY_PDNNODE_HTTPBOUNCERPORT="pdnNode.httpBouncerPort"
SYS_KEY_ROUTER_ENTRYPOINTS_EXTERNALPORT="router.entrypoints.externalPort"

SYS_KEY_PDNSERVER_SELFADDRESS="pdnServer.selfAddress"

SYS_KEY_SHARED_NODE_ID="shared.node.id"
SYS_KEY_SHARED_NODE_NAME="shared.node.name"
SYS_KEY_SHARED_JAVAHOME="shared.javaHome"

SYS_KEY_SHARED_DATABASE_TYPE_VALUE_MYSQL="mysql"
SYS_KEY_SHARED_DATABASE_TYPE_VALUE_ORACLE="oracle"
SYS_KEY_SHARED_DATABASE_TYPE_VALUE_MSSQL="mssql"
SYS_KEY_SHARED_DATABASE_TYPE_VALUE_MARIADB="mariadb"
SYS_KEY_SHARED_DATABASE_TYPE_VALUE_POSTGRES="postgresql"
# If database type is selected as derby, assume its internal
SYS_KEY_SHARED_DATABASE_TYPE_VALUE_DERBY="derby"

SYS_KEY_SHARED_DATABASE_DRIVER_VALUE_MYSQL="com.mysql.jdbc.Driver"
SYS_KEY_SHARED_DATABASE_DRIVER_VALUE_ORACLE="oracle.jdbc.OracleDriver"
SYS_KEY_SHARED_DATABASE_DRIVER_VALUE_MSSQL="com.microsoft.sqlserver.jdbc.SQLServerDriver"
SYS_KEY_SHARED_DATABASE_DRIVER_VALUE_MARIADB="org.mariadb.jdbc.Driver"
SYS_KEY_SHARED_DATABASE_DRIVER_VALUE_POSTGRES="org.postgresql.Driver"
SYS_KEY_SHARED_DATABASE_DRIVER_VALUE_DERBY="org.apache.derby.jdbc.EmbeddedDriver"

SYS_KEY_SHARED_DATABASE_TYPE="shared.database.type"
SYS_KEY_SHARED_DATABASE_DRIVER="shared.database.driver"
SYS_KEY_SHARED_DATABASE_URL="shared.database.url"
SYS_KEY_SHARED_DATABASE_USERNAME="shared.database.username"
SYS_KEY_SHARED_DATABASE_PASSWORD="shared.database.password"

SYS_KEY_SHARED_REDIS_PASSWORD="shared.redis.password"
SYS_KEY_SHARED_RABBITMQ_URL="shared.rabbitMq.url"
SYS_KEY_SHARED_RABBITMQ_PASSWORD="shared.rabbitMq.password"

SYS_KEY_SHARED_ELASTICSEARCH_URL="shared.elasticsearch.url"
SYS_KEY_SHARED_ELASTICSEARCH_USERNAME="shared.elasticsearch.username"
SYS_KEY_SHARED_ELASTICSEARCH_PASSWORD="shared.elasticsearch.password"
SYS_KEY_SHARED_ELASTICSEARCH_CLUSTERSETUP="shared.elasticsearch.clusterSetup"
SYS_KEY_SHARED_ELASTICSEARCH_UNICASTFILE="shared.elasticsearch.unicastFile"
SYS_KEY_SHARED_ELASTICSEARCH_EXTRAJAVAOPTS="shared.elasticsearch.extraJavaOpts"
SYS_KEY_SHARED_ELASTICSEARCH_CLUSTERSETUP_VALUE="YES"
SYS_KEY_SHARED_ELASTICSEARCH_EXTERNALISE="shared.elasticsearch.external"
SYS_KEY_ELASTICSEARCH_APP_VERSION="elasticsearch.app.version"

getDatabaseDriver(){
    local databaseType="$1"
    case "$databaseType" in
        $SYS_KEY_SHARED_DATABASE_TYPE_VALUE_POSTGRES)
            echo -n ${SYS_KEY_SHARED_DATABASE_DRIVER_VALUE_POSTGRES}
        ;;
        $SYS_KEY_SHARED_DATABASE_TYPE_VALUE_MYSQL)
            echo -n ${SYS_KEY_SHARED_DATABASE_DRIVER_VALUE_MYSQL}
        ;;
        $SYS_KEY_SHARED_DATABASE_TYPE_VALUE_MARIADB)
            echo -n ${SYS_KEY_SHARED_DATABASE_DRIVER_VALUE_MARIADB}
        ;;
        $SYS_KEY_SHARED_DATABASE_TYPE_VALUE_MSSQL)
            echo -n ${SYS_KEY_SHARED_DATABASE_DRIVER_VALUE_MSSQL}
        ;;
        $SYS_KEY_SHARED_DATABASE_TYPE_VALUE_ORACLE)
            echo -n ${SYS_KEY_SHARED_DATABASE_DRIVER_VALUE_ORACLE}
        ;;
    esac
}

KEY_DATABASE_DRIVER=${SYS_KEY_SHARED_DATABASE_DRIVER}

WSS_SERVER_BASE_URL="http://localhost:8080"
SYS_KEY_SERVER_WSS_BASE_URL_KEY="server.externalScannerBaseUrl"
SYS_KEY_SERVER_INTEGRATION_ENABLE_KEY="server.isExtIntegrationEnable"

# Define this in product specific script. Should contain the path to unitcast file
# File used by insight server to write cluster active nodes info. This will be read by elasticsearch
#SYS_KEY_SHARED_ELASTICSEARCH_UNICASTFILE_VALUE=""

SYS_KEY_RABBITMQ_ACTIVE_NODE_NAME="shared.rabbitMq.active.node.name"
SYS_KEY_RABBITMQ_ACTIVE_NODE_IP="shared.rabbitMq.active.node.ip"

IGNORE_RABBITMQ_CONFIGS="cluster_formation.peer_discovery_backend cluster_formation.classic_config.nodes.1"
SYS_KEY_RABBITMQ_NODE_RABBITMQCONF="shared.rabbitMq.node.rabbitmqConf"

IGNORE_SYSTEM_YAML_VALIDATION="${FLAG_Y}"

# Filenames
FILE_NAME_SYSTEM_YAML="system.yaml"
FILE_NAME_SYSTEM_YAML_TEMPLATE="system.full-template.yaml"
FILE_NAME_INSTALLER_STATE_YAML="installerState.yaml"
FILE_NAME_JOIN_KEY="join.key"
FILE_NAME_MASTER_KEY="master.key"
FILE_NAME_INSTALLER_YAML="installer.yaml"

# Global constants used in business logic
NODE_TYPE_STANDALONE="standalone"
NODE_TYPE_CLUSTER_NODE="node"
NODE_TYPE_DATABASE="database"

# External(isable) databases 
DATABASE_POSTGRES="POSTGRES"
DATABASE_ELASTICSEARCH="ELASTICSEARCH"
DATABASE_RABBITMQ="RABBITMQ"

MYSQL_LABEL="MYSQL"
ORACLE_LABEL="ORACLE"
MSSQL_LABEL="MSSQL"
MARIADB_LABEL="MARIADB"
POSTGRES_LABEL="PostgreSQL"
ELASTICSEARCH_LABEL="Elasticsearch"
RABBITMQ_LABEL="Rabbitmq"
REDIS_LABEL="Redis"

ARTIFACTORY_LABEL="Artifactory"
JFMC_LABEL="Mission Control"
INSIGHT_LABEL="Insight"
DISTRIBUTION_LABEL="Distribution"
XRAY_LABEL="Xray"
PDN_NODE_LABEL="Private Distribution Network Node"
PDN_SERVER_LABEL="Private Distribution Network Server"
RT_XRAY_TRIAL_LABEL="JFrog Platform Trial Pro X"

POSTGRES_CONTAINER="postgres"
ELASTICSEARCH_CONTAINER="elasticsearch"
RABBITMQ_CONTAINER="rabbitmq"
REDIS_CONTAINER="redis"

#Adding a small timeout before a read ensures it is positioned correctly in the screen
read_timeout=0.5

DEFAULT_CURL_TIMEOUT=10
# Options related to data directory location
PROMPT_DATA_DIR_LOCATION="Installation Directory"
KEY_DATA_DIR_LOCATION="installer.data_dir"

SYS_KEY_SHARED_NODE_HAENABLED="shared.node.haEnabled"
PROMPT_ADD_TO_CLUSTER="Are you adding an additional node to an existing product cluster?"
KEY_ADD_TO_CLUSTER="installer.ha"
VALID_VALUES_ADD_TO_CLUSTER="$FLAGS_Y_N"
SYS_KEY_ADD_TO_CLUSTER=${KEY_ADD_TO_CLUSTER}

SYS_KEY_SHARED_NODE_IP="shared.node.ip"
MESSAGE_HOST_IP="For IPv6 address, enclose value within square brackets as follows : [<ipv6_address>]"
PROMPT_HOST_IP="Please specify the IP address of this machine"
KEY_HOST_IP="installer.node.ip"

MESSAGE_POSTGRES_INSTALL="The installer can install a $POSTGRES_LABEL database, or you can connect to an existing compatible $POSTGRES_LABEL database\n(https://service.jfrog.org/installer/System+Requirements#SystemRequirements-RequirementsMatrix)\nIf you are upgrading from an existing installation, select N if you have externalized PostgreSQL, select Y if not."
PROMPT_POSTGRES_INSTALL="Do you want to install $POSTGRES_LABEL?"
KEY_POSTGRES_INSTALL="installer.install_postgresql"
KEY_POSTGRES_VERSION="installer.postgresql_version"
SYS_KEY_POSTGRES_VERSION=${KEY_POSTGRES_VERSION}
VALID_VALUES_POSTGRES_INSTALL="$FLAGS_Y_N"
SYS_KEY_POSTGRES_INSTALL=${KEY_POSTGRES_INSTALL}

# Postgres connection details
RPM_DEB_POSTGRES_HOME_DEFAULT="/var/opt/jfrog/postgres"
RPM_DEB_MESSAGE_STANDALONE_POSTGRES_DATA="$POSTGRES_LABEL home will have data and its configuration"
RPM_DEB_PROMPT_STANDALONE_POSTGRES_DATA="Type desired $POSTGRES_LABEL home location"
RPM_DEB_KEY_STANDALONE_POSTGRES_DATA="installer.postgresql.home"

MESSAGE_DATABASE_URL="Provide the database connection details"

GET_MULTI_SUPPORT_DATABASE_URL(){
    databaseURlExample=
    databaseLabel=
    getSystemValue "$SYS_KEY_SHARED_DATABASE_TYPE" "$SYS_KEY_SHARED_DATABASE_TYPE_VALUE_POSTGRES" "false" "$INSTALLER_YAML"
    case "$YAML_VALUE" in
        $SYS_KEY_SHARED_DATABASE_TYPE_VALUE_POSTGRES)
            databaseURlExample="jdbc:postgresql://<IP_ADDRESS>:<PORT>/artifactory"
            databaseLabel="$POSTGRES_LABEL"
        ;;
        $SYS_KEY_SHARED_DATABASE_TYPE_VALUE_MYSQL)
            databaseURlExample="jdbc:mysql://localhost:3306/artdb?characterEncoding=UTF-8&elideSetAutoCommits=true&useSSL=false"
            databaseLabel="$MYSQL_LABEL"
        ;;
        $SYS_KEY_SHARED_DATABASE_TYPE_VALUE_MARIADB)
            databaseURlExample="jdbc:mariadb://localhost:3306/artdb?characterEncoding=UTF-8&elideSetAutoCommits=true&useSSL=false"
            databaseLabel="$MARIADB_LABEL"
        ;;
        $SYS_KEY_SHARED_DATABASE_TYPE_VALUE_MSSQL)
            databaseURlExample="jdbc:sqlserver://localhost:1433;databaseName=artifactory;sendStringParametersAsUnicode=false;applicationName=Artifactory Binary Repository"
            databaseLabel="$MSSQL_LABEL"
        ;;
        $SYS_KEY_SHARED_DATABASE_TYPE_VALUE_ORACLE)
            databaseURlExample="jdbc:oracle:thin:@localhost:1521:ORCL"
            databaseLabel="$ORACLE_LABEL"
        ;;
    esac
}

PROMPT_DATABASE_URL(){
    databaseURlExample=
    databaseLabel=
    case "$PRODUCT_NAME" in
            $ARTIFACTORY_LABEL)
                GET_MULTI_SUPPORT_DATABASE_URL
            ;;
            $JFMC_LABEL)
                databaseURlExample="jdbc:postgresql://<IP_ADDRESS>:<PORT>/mission_control?sslmode=disable"
                databaseLabel=$POSTGRES_LABEL
            ;;
            $INSIGHT_LABEL)
                databaseURlExample="jdbc:postgresql://<IP_ADDRESS>:<PORT>/insight?sslmode=disable"
                databaseLabel=$POSTGRES_LABEL
            ;;
            $DISTRIBUTION_LABEL)
                databaseURlExample="jdbc:postgresql://<IP_ADDRESS>:<PORT>/distribution?sslmode=disable"
                databaseLabel=$POSTGRES_LABEL
            ;;
            $XRAY_LABEL)
                databaseURlExample="postgres://<IP_ADDRESS>:<PORT>/xraydb?sslmode=disable"
                databaseLabel=$POSTGRES_LABEL
            ;;
        esac
    if [ -z "$databaseURlExample" ]; then
        echo -n "Database URL" # For consistency with username and password
        return
    fi
    echo -n "$databaseLabel url. Example: [$databaseURlExample]"
}

REGEX_MULTI_SUPPORT_DATABASE_URL(){
    databaseURlExample=
    getSystemValue "$SYS_KEY_SHARED_DATABASE_TYPE" "$SYS_KEY_SHARED_DATABASE_TYPE_VALUE_POSTGRES" "false" "$INSTALLER_YAML"
    case "$YAML_VALUE" in
            $SYS_KEY_SHARED_DATABASE_TYPE_VALUE_POSTGRES)
                databaseURlExample=".*postgresql://.*/.*"
            ;;
            $SYS_KEY_SHARED_DATABASE_TYPE_VALUE_MYSQL)
                databaseURlExample=".*mysql://.*"
            ;;
            $SYS_KEY_SHARED_DATABASE_TYPE_VALUE_MARIADB)
                databaseURlExample=".*mariadb://.*"
            ;;
            $SYS_KEY_SHARED_DATABASE_TYPE_VALUE_MSSQL)
                databaseURlExample=".*sqlserver://.*"
            ;;
            $SYS_KEY_SHARED_DATABASE_TYPE_VALUE_ORACLE)
                databaseURlExample=".*oracle:.*"
            ;;
        esac
}

REGEX_DATABASE_URL(){
    databaseURlExample=
    case "$PRODUCT_NAME" in
            $ARTIFACTORY_LABEL)
                REGEX_MULTI_SUPPORT_DATABASE_URL
            ;;
            $JFMC_LABEL)
                databaseURlExample="jdbc:postgresql://.*/.*"
            ;;
            $INSIGHT_LABEL)
                databaseURlExample="jdbc:postgresql://.*/.*"
            ;;
            $DISTRIBUTION_LABEL)
                databaseURlExample="jdbc:postgresql://.*/.*"
            ;;
            $XRAY_LABEL)
                databaseURlExample="postgres://.*/.*"
            ;;
        esac
    echo -n "^$databaseURlExample\$"
}

ERROR_MESSAGE_DATABASE_URL="Invalid database URL"
KEY_DATABASE_URL="$SYS_KEY_SHARED_DATABASE_URL"

MESSAGE_DB_PASSWORD="To setup $POSTGRES_LABEL, please enter a password"
PROMPT_DB_PASSWORD="database password"
IS_SENSITIVE_DB_PASSWORD="$FLAG_Y"
KEY_DB_PASSWORD="$SYS_KEY_SHARED_DATABASE_PASSWORD"
REGEX_DB_PASSWORD="^[a-zA-Z0-9]+$"
ERROR_MESSAGE_DB_PASSWORD="Please enter valid $POSTGRES_LABEL password"
REPLACE_POSTGRES_PASSWORD="__REPLACE_POSTGRES_PASSWORD__"

KEY_REDIS_PASSWORD="$SYS_KEY_SHARED_REDIS_PASSWORD"
DEFAULT_REDIS_PASSWORD="distri-redis"

KEY_RABBITMQ_PASSWORD="$SYS_KEY_SHARED_RABBITMQ_PASSWORD"
DEFAULT_RABBITMQ_PASSWORD="xray-rabbitmq"

MESSAGE_DATABASE_TYPE(){
    echo -n "Provide the type of your external database that you want to connect to."
    if [[ "${SUPPORTED_DATABASE_TYPES}" =~ .*${SYS_KEY_SHARED_DATABASE_TYPE_VALUE_DERBY}.* ]]; then 
        echo -en "\nNote : If you choose ${SYS_KEY_SHARED_DATABASE_TYPE_VALUE_DERBY}, it will be considered as an internal database and no further details will be asked"
    fi
}

KEY_DATABASE_TYPE="$SYS_KEY_SHARED_DATABASE_TYPE"

PROMPT_DATABASE_TYPE() {
    echo -n "Enter database type, supported values [ $SUPPORTED_DATABASE_TYPES ]"
}

REGEX_DATABASE_TYPE(){
    if [ -z "$SUPPORTED_DATABASE_TYPES" ]; then
        echo -n "$[a-z]*$"
    else
        local supportedDbList=""
        for dbtype in $SUPPORTED_DATABASE_TYPES; do
            [ -z "${supportedDbList}" ] && supportedDbList="${dbtype}" || supportedDbList="${supportedDbList}|${dbtype}"
        done
        echo -n "${supportedDbList}"
    fi
}

ERROR_MESSAGE_DATABASE_TYPE="Invalid database type"

 #NOTE: It is important to display the label. Since the message may be hidden if URL is known
PROMPT_DATABASE_USERNAME="Database username (If your existing connection URL already includes the username, leave this empty)"
KEY_DATABASE_USERNAME="$SYS_KEY_SHARED_DATABASE_USERNAME"
IS_OPTIONAL_DATABASE_USERNAME="$FLAG_Y"

 #NOTE: It is important to display the label. Since the message may be hidden if URL is known
PROMPT_DATABASE_PASSWORD="Database password (If your existing connection URL already includes the password, leave this empty)"
KEY_DATABASE_PASSWORD="$SYS_KEY_SHARED_DATABASE_PASSWORD"
IS_SENSITIVE_DATABASE_PASSWORD="$FLAG_Y"
IS_OPTIONAL_DATABASE_PASSWORD="$FLAG_Y"

MESSAGE_STANDALONE_ELASTICSEARCH_INSTALL="The installer can install a $ELASTICSEARCH_LABEL database or you can connect to an existing compatible $ELASTICSEARCH_LABEL database"
PROMPT_STANDALONE_ELASTICSEARCH_INSTALL="Do you want to install $ELASTICSEARCH_LABEL?"
KEY_STANDALONE_ELASTICSEARCH_INSTALL="installer.install_elasticsearch"
VALID_VALUES_STANDALONE_ELASTICSEARCH_INSTALL="$FLAGS_Y_N"
SYS_KEY_STANDALONE_ELASTICSEARCH_INSTALL=${KEY_STANDALONE_ELASTICSEARCH_INSTALL}

MESSAGE_XRAY_ARTIFACTORY_PAIRING="If you are not performing an upgrade, you can ignore the following question and press ${FLAG_Y}"
PROMPT_XRAY_ARTIFACTORY_PAIRING="Have you disconnected Artifactory Xray pairings, except one prior to performing this upgrade (Refer http://service.jfrog.org/wiki/Xray+and+Artifactory+One+to+One+Pairing for more details) ?"
KEY_XRAY_ARTIFACTORY_PAIRING="installer.is_xray_artifactory_pairing_disconnected"
VALID_VALUES_XRAY_ARTIFACTORY_PAIRING="$FLAGS_Y_N"

# Elasticsearch connection details
MESSAGE_ELASTICSEARCH_URL="Provide the $ELASTICSEARCH_LABEL connection details"
PROMPT_ELASTICSEARCH_URL="$ELASTICSEARCH_LABEL URL"
KEY_ELASTICSEARCH_URL="$SYS_KEY_SHARED_ELASTICSEARCH_URL"

MESSAGE_ELASTICSEARCH_USERNAME="To setup $ELASTICSEARCH_LABEL, please enter a username and password"
PROMPT_ELASTICSEARCH_USERNAME="elasticsearch username"
KEY_ELASTICSEARCH_USERNAME="$SYS_KEY_SHARED_ELASTICSEARCH_USERNAME"
REPLACE_ELASTICSEARCH_USERNAME="__REPLACE_ELASTICSEARCH_USERNAME__"

PROMPT_ELASTICSEARCH_PASSWORD="elasticsearch password"
KEY_ELASTICSEARCH_PASSWORD="$SYS_KEY_SHARED_ELASTICSEARCH_PASSWORD"
IS_SENSITIVE_ELASTICSEARCH_PASSWORD="$FLAG_Y"
REPLACE_ELASTICSEARCH_PASSWORD="__REPLACE_ELASTICSEARCH_PASSWORD__"

# Cluster related questions
MESSAGE_CLUSTER_MASTER_KEY="Provide the cluster's master key. It can be found in the data directory of the first node under /etc/security/master.key"
PROMPT_CLUSTER_MASTER_KEY="Master Key"
KEY_CLUSTER_MASTER_KEY="$SYS_KEY_SHARED_SECURITY_MASTERKEY"
IS_SENSITIVE_CLUSTER_MASTER_KEY="$FLAG_Y"

MESSAGE_JOIN_KEY="The Join key is the secret key used to establish trust between services in the JFrog Platform.\n(You can copy the Join Key from Admin > User Management > Settings)"
PROMPT_JOIN_KEY="Join Key"
KEY_JOIN_KEY="$SYS_KEY_SHARED_SECURITY_JOINKEY"
IS_SENSITIVE_JOIN_KEY="$FLAG_Y"
REGEX_JOIN_KEY="^[a-zA-Z0-9]{16,}\$"
ERROR_MESSAGE_JOIN_KEY="Invalid Join Key"

# Rabbitmq related cluster information
MESSAGE_RABBITMQ_ACTIVE_NODE_NAME="Provide an active ${RABBITMQ_LABEL} node name. Run the command [ hostname -s ] on any of the existing nodes in the product cluster to get this"
PROMPT_RABBITMQ_ACTIVE_NODE_NAME="${RABBITMQ_LABEL} active node name"
KEY_RABBITMQ_ACTIVE_NODE_NAME="$SYS_KEY_RABBITMQ_ACTIVE_NODE_NAME"

# Rabbitmq related cluster information (necessary only for docker-compose)
PROMPT_RABBITMQ_ACTIVE_NODE_IP="${RABBITMQ_LABEL} active node ip"
KEY_RABBITMQ_ACTIVE_NODE_IP="$SYS_KEY_RABBITMQ_ACTIVE_NODE_IP"

MESSAGE_JFROGURL(){
    echo -e "The JFrog URL allows ${PRODUCT_NAME} to connect to a JFrog Platform Instance.\n(You can copy the JFrog URL from Administration > User Management > Settings > Connection details)"
}

PROMPT_JFROGURL="JFrog URL"
KEY_JFROGURL="$SYS_KEY_SHARED_JFROGURL"
REGEX_JFROGURL="^https?://.*:{0,}[0-9]{0,4}\$"
ERROR_MESSAGE_JFROGURL="Invalid JFrog URL"

MESSAGE_PDNNODE_JOINKEY="PDN Join key value generated in the Artifactory node an used for establishing a connection between the Nodes to the PDN Server.\n(You can copy the PDN Access Token Join Key from Administration > User Management > Settings > Connection details)"
PROMPT_PDNNODE_JOINKEY="PDN Join Key"
KEY_PDNNODE_JOINKEY="$SYS_KEY_PDNNODE_JOINKEY"
IS_SENSITIVE_PDNNODE_JOINKEY="$FLAG_Y"
REGEX_PDNNODE_JOINKEY="^[a-zA-Z0-9]{16,}\$"
ERROR_MESSAGE_PDNNODE_JOINKEY="Invalid PDN Join Key"

KEY_PDNNODE_PORT="$SYS_KEY_PDNNODE_PORT"
KEY_PDNNODE_HTTPPORT="$SYS_KEY_PDNNODE_HTTPPORT"

MESSAGE_PDNNODE_HTTPBOUNCERPORT="The PDN HTTP Bouncer server secure port is used as a load balancer between the PDN nodes."
PROMPT_PDNNODE_HTTPBOUNCERPORT="HTTP Bouncer Port"
KEY_PDNNODE_HTTPBOUNCERPORT="$SYS_KEY_PDNNODE_HTTPBOUNCERPORT"

MESSAGE_ROUTER_ENTRYPOINTS_EXTERNALPORT="The external port, registered in the service registry, used by external services to communicate with services in this node"
PROMPT_ROUTER_ENTRYPOINTS_EXTERNALPORT="Router Entrypoints ExternalPort"
KEY_ROUTER_ENTRYPOINTS_EXTERNALPORT="$SYS_KEY_ROUTER_ENTRYPOINTS_EXTERNALPORT"

MESSAGE_PDNNODE_PDNSERVERURL(){
    echo -e "PDN Server URL on which the server accepts secure gRPC connections"
}

PROMPT_PDNNODE_PDNSERVERURL="PDN Server URL[<PDN Server URL>:8095]"
KEY_PDNNODE_PDNSERVERURL="$SYS_KEY_PDNNODE_PDNSERVERURL"
REGEX_PDNNODE_PDNSERVERURL=".*:{0,}[0-9]{0,4}\$"
ERROR_MESSAGE_PDNNODE_PDNSERVERURL="Invalid PDN Server URL"

MESSAGE_PDNNODE_SELFHTTPADDRESS(){
    echo -e "The HTTP address to expose for external nodes with the HTTP port."
}

PROMPT_PDNNODE_SELFHTTPADDRESS="PDN Node Self Address and http Port [<Self Address>:8089]"
KEY_PDNNODE_SELFHTTPADDRESS="$SYS_KEY_PDNNODE_SELFHTTPADDRESS"
REGEX_PDNNODE_SELFHTTPADDRESS=".*:{0,}[0-9]{0,4}\$"
ERROR_MESSAGE_PDNNODE_SELFHTTPADDRESS="Invalid PDN Node Self HttpAddress"

MESSAGE_PDNNODE_SELFGRPCADDRESS(){
    echo -e "The PDN Node Self gRPC Address is the external hostname used to connect the node to PDN server."
}

PROMPT_PDNNODE_SELFGRPCADDRESS="PDN Node Self Address and gRPC Port [<Self Address>:8088]"
KEY_PDNNODE_SELFGRPCADDRESS="$SYS_KEY_PDNNODE_SELFGRPCADDRESS"
REGEX_PDNNODE_SELFGRPCADDRESS=".*:{0,}[0-9]{0,4}\$"
ERROR_MESSAGE_PDNNODE_SELFGRPCADDRESS="Invalid PDN Node Self GrpcAddress"

MESSAGE_PDNSERVER_SELFADDRESS(){
    echo -e "The PDN Server Self gRPC Address is the external hostname used to connect the PDN nodes."
}

PROMPT_PDNSERVER_SELFADDRESS="PDN Server Self Address and gRPC Port [<Self Address>:8095]"
KEY_PDNSERVER_SELFADDRESS="$SYS_KEY_PDNSERVER_SELFADDRESS"
REGEX_PDNSERVER_SELFGRPCADDRESS=".*:{0,}[0-9]{0,4}\$"
ERROR_MESSAGE_PDNSERVER_SELFGRPCADDRESS="Invalid PDN Server Self GrpcAddress"


# Set this to FLAG_Y on upgrade
IS_UPGRADE="${FLAG_N}"

# This belongs in JFMC but is the ONLY one that needs it so keeping it here for now. Can be made into a method and overridden if necessary
MESSAGE_MULTIPLE_PG_SCHEME="Please setup $POSTGRES_LABEL with schema as described in https://service.jfrog.org/installer/Installing+Mission+Control"

_getMethodOutputOrVariableValue() {
    unset EFFECTIVE_MESSAGE
    local keyToSearch=$1
    local effectiveMessage=
    local result="0"
    # logSilly "Searching for method: [$keyToSearch]"
    LC_ALL=C type "$keyToSearch" > /dev/null 2>&1 || result="$?"
    if [[ "$result" == "0" ]]; then
        # logSilly "Found method for [$keyToSearch]"
        EFFECTIVE_MESSAGE="$($keyToSearch)"
        return
    fi
    eval EFFECTIVE_MESSAGE=\${$keyToSearch}
    if [ ! -z "$EFFECTIVE_MESSAGE" ]; then
        return
    fi
    # logSilly "Didn't find method or variable for [$keyToSearch]"
}

# List of keys to be maintained in FILE_NAME_INSTALLER_STATE_YAML
setInstallerStateKeys() {
    local commonKeys="KEY_ADD_TO_CLUSTER KEY_POSTGRES_INSTALL KEY_POSTGRES_VERSION"
    case "$PRODUCT_NAME" in
        $ARTIFACTORY_LABEL)
            INSTALLER_STATE_KEYS="${commonKeys}"
        ;;
        $JFMC_LABEL)
            INSTALLER_STATE_KEYS="${commonKeys} KEY_STANDALONE_ELASTICSEARCH_INSTALL"
        ;;
        $INSIGHT_LABEL)
            INSTALLER_STATE_KEYS="${commonKeys} KEY_STANDALONE_ELASTICSEARCH_INSTALL"
        ;;
        $DISTRIBUTION_LABEL)
            INSTALLER_STATE_KEYS="${commonKeys}"
        ;;
        $XRAY_LABEL)
            INSTALLER_STATE_KEYS="${commonKeys}"
        ;;
        $RT_XRAY_TRIAL_LABEL)
            INSTALLER_STATE_KEYS="${commonKeys}"
        ;;
        $PDN_NODE_LABEL)
            INSTALLER_STATE_KEYS="KEY_ADD_TO_CLUSTER"
        ;;
        $PDN_SERVER_LABEL)
            INSTALLER_STATE_KEYS="KEY_ADD_TO_CLUSTER"
        ;;
    esac

}

# To bump up Elasticsearch version in JFMC (native installers) change version
RPM_DEB_ES_VERSION="7.17.6"

# To bump up Redis version in Distribution (native installers) change version
REDIS_VERSION="7.0.6"
RPM_DEB_REDIS_VERSION="${REDIS_VERSION}-1"

# To bump up Erlang version in xray (native installers) change version
ERLANG_VERSION="23.2.3"
RPM_DEB_ERLANG_VERSION="${ERLANG_VERSION}"

# To bump up postgresql version in all products (native installer) change version 
POSTGRES_VERSION="13.9"
POSTGRES_MAJOR_VER="13"
POSTGRES_SLES_MAJOR_VER="13"
POSTGRES_SLES_VER="13.9-1"
POSTGRES_RPM_VER="13.9-1"
POSTGRES_DEB_VER="13_13.9-1"
POSTGRES_UBUNTU16_VER="13_13.3-1"

# To bump up postgresql version in all products (compose installers) change version 
setPostgresVersion() {
    # To bump up postgresql in product add new version as new a variable for example JFROG_POSTGRES_12X_VERSION="13-5v"
    JFROG_POSTGRES_10X_VERSION="10-13v"
    JFROG_POSTGRES_12_3X_VERSION="12-3v"
    JFROG_POSTGRES_12_5X_VERSION="12-5v"
    JFROG_POSTGRES_13_2X_VERSION="13-2v"
    JFROG_POSTGRES_13_4X_VERSION="13-4v"
    JFROG_POSTGRES_13_9X_VERSION="13-9v"

    case "$PRODUCT_NAME" in
        $ARTIFACTORY_LABEL)
            JFROG_POSTGRES_9X_VERSION="9-6-11v"
            # To bump up postgresql in artifactory add new version in array
            JFROG_POSTGRES_VERSIONS=("9.6.11" "10.13" "12.3" "12.5" "13.2" "13.4" "13.9")
        ;;
        $JFMC_LABEL)
            JFROG_POSTGRES_9X_VERSION="9-6-11v"
            # To bump up postgresql in jfmc add new version in array
            JFROG_POSTGRES_VERSIONS=("9.6.11" "10.13" "12.3" "12.5" "13.2" "13.4" "13.9")
        ;;
        $INSIGHT_LABEL)
            # To bump up postgresql in insight add new version in array
            JFROG_POSTGRES_VERSIONS=("13.2" "13.4" "13.9")
        ;;
        $DISTRIBUTION_LABEL)
            JFROG_POSTGRES_9X_VERSION="9-6-10v"
            # To bump up postgresql in distribution add new version in array
            JFROG_POSTGRES_VERSIONS=("9.6.10" "10.13" "12.3" "12.5" "13.2" "13.4" "13.9")
        ;;
        $XRAY_LABEL)
            JFROG_POSTGRES_9X_VERSION="9-5-2v"
            # To bump up postgresql in xray add new version in array
            JFROG_POSTGRES_VERSIONS=("9.5.2" "10.13" "12.3" "12.5" "13.2" "13.4" "13.9")
        ;;
    esac
}

# Used in docker-compose trial flow when run on docker for windows and mac
export JFROG_HOST_DOCKER_INTERNAL="host.docker.internal"




# REF https://misc.flogisoft.com/bash/tip_colors_and_formatting
cClear="\e[0m"
cBlue="\e[38;5;69m"
cRedDull="\e[1;31m"
cYellow="\e[1;33m"
cRedBright="\e[38;5;197m"
cBold="\e[1m"


_loggerGetModeRaw() {
    local MODE="$1"
    case $MODE in
    INFO)
        printf ""
    ;;
    DEBUG)
        printf "%s" "[${MODE}] "
    ;;
    WARN)
        printf "${cRedDull}%s%s${cClear}" "[" "${MODE}" "] "
    ;;
    ERROR)
        printf "${cRedBright}%s%s${cClear}" "[" "${MODE}" "] "
    ;;
    esac
}


_loggerGetMode() {
    local MODE="$1"
    case $MODE in
    INFO)
        printf "${cBlue}%s%-5s%s${cClear}" "[" "${MODE}" "]"
    ;;
    DEBUG)
        printf "%-7s" "[${MODE}]"
    ;;
    WARN)
        printf "${cRedDull}%s%-5s%s${cClear}" "[" "${MODE}" "]"
    ;;
    ERROR)
        printf "${cRedBright}%s%-5s%s${cClear}" "[" "${MODE}" "]"
    ;;
    esac
}

# Capitalises the first letter of the message
_loggerGetMessage() {
    local originalMessage="$*"
    local firstChar=$(echo "${originalMessage:0:1}" | awk '{ print toupper($0) }')
    local resetOfMessage="${originalMessage:1}"
    echo "$firstChar$resetOfMessage"
}

# The spec also says content should be left-trimmed but this is not necessary in our case. We don't reach the limit.
_loggerGetStackTrace() {
    printf "%s%-30s%s" "[" "$1:$2" "]"
}

_loggerGetThread() {
    printf "%s" "[main]"
}

_loggerGetServiceType() {
    printf "%s%-5s%s" "[" "shell" "]"
}

#Trace ID is not applicable to scripts
_loggerGetTraceID() {
    printf "%s" "[]"
}

logRaw() {
    echo ""
    printf "$1"
    echo ""
}

logBold(){
    echo ""
    printf "${cBold}$1${cClear}"
    echo ""
}

# The date binary works differently based on whether it is GNU/BSD
is_date_supported=0
date --version > /dev/null 2>&1 || is_date_supported=1
IS_GNU=$(echo $is_date_supported)

_loggerGetTimestamp() {
    if [ "${IS_GNU}" == "0" ]; then
        echo -n $(date -u +%FT%T.%3NZ)
    else
        echo -n $(date -u +%FT%T.000Z)
    fi
}

# https://www.shellscript.sh/tips/spinner/
_spin()
{
  spinner="/|\\-/|\\-"
  while :
  do
    for i in `seq 0 7`
    do
      echo -n "${spinner:$i:1}"
      echo -en "\010"
      sleep 1
    done
  done
}

showSpinner() {
    # Start the Spinner:
    _spin &
    # Make a note of its Process ID (PID):
    SPIN_PID=$!
    # Kill the spinner on any signal, including our own exit.
    trap "kill -9 $SPIN_PID" `seq 0 15` &> /dev/null || return 0
}

stopSpinner() {
    local occurrences=$(ps -ef | grep -wc "${SPIN_PID}")
    let "occurrences+=0"
    # validate that it is present (2 since this search itself will show up in the results)
    if [ $occurrences -gt 1 ]; then
        kill -9 $SPIN_PID &>/dev/null || return 0
        wait $SPIN_ID &>/dev/null
    fi
}

_getEffectiveMessage(){
    local MESSAGE="$1"
    local MODE=${2-"INFO"}

    if [ -z "$CONTEXT" ]; then
        CONTEXT=$(caller)
    fi

    _EFFECTIVE_MESSAGE=
    if [ -z "$LOG_BEHAVIOR_ADD_META" ]; then
        _EFFECTIVE_MESSAGE="$(_loggerGetModeRaw $MODE)$(_loggerGetMessage $MESSAGE)"
    else
        local SERVICE_TYPE="script"
        local TRACE_ID=""
        local THREAD="main"
        
        local CONTEXT_LINE=$(echo "$CONTEXT" | awk '{print $1}')
        local CONTEXT_FILE=$(echo "$CONTEXT" | awk -F"/" '{print $NF}')
        
        _EFFECTIVE_MESSAGE="$(_loggerGetTimestamp) $(_loggerGetServiceType) $(_loggerGetMode $MODE) $(_loggerGetTraceID) $(_loggerGetStackTrace $CONTEXT_FILE $CONTEXT_LINE) $(_loggerGetThread) - $(_loggerGetMessage $MESSAGE)"
    fi
    CONTEXT=
}

# Important - don't call any log method from this method. Will become an infinite loop. Use echo to debug
_logToFile() {
    local MODE=${1-"INFO"}
    local targetFile="$LOG_BEHAVIOR_ADD_REDIRECTION"
    # IF the file isn't passed, abort
    if [ -z "$targetFile" ]; then
        return
    fi
    # IF this is not being run in verbose mode and mode is debug or lower, abort
    if [ "${VERBOSE_MODE}" != "$FLAG_Y" ] && [ "${VERBOSE_MODE}" != "true" ] && [ "${VERBOSE_MODE}" != "debug" ]; then
        if [ "$MODE" == "DEBUG" ] || [ "$MODE" == "SILLY" ]; then
            return
        fi
    fi

    # Create the file if it doesn't exist
    if [ ! -f "${targetFile}" ]; then
        return
        # touch $targetFile > /dev/null 2>&1 || true
    fi
    # # Make it readable
    # chmod 640 $targetFile > /dev/null 2>&1 || true

    # Log contents
    printf "%s\n" "$_EFFECTIVE_MESSAGE" >> "$targetFile" || true
}

logger() {
    if [ "$LOG_BEHAVIOR_ADD_NEW_LINE" == "$FLAG_Y" ]; then
        echo ""
    fi
    CONTEXT=$(caller)
    _getEffectiveMessage "$@"
    local MODE=${2-"INFO"}
    printf "%s\n" "$_EFFECTIVE_MESSAGE"
    _logToFile "$MODE"
}

logDebug(){
    VERBOSE_MODE=${VERBOSE_MODE-"false"}
    CONTEXT=$(caller)
    if [ "${VERBOSE_MODE}" == "$FLAG_Y" ] || [ "${VERBOSE_MODE}" == "true" ] || [ "${VERBOSE_MODE}" == "debug" ];then
        logger "$1" "DEBUG"
    else
        logger "$1" "DEBUG" >&6
    fi
    CONTEXT=
}

logSilly(){
    VERBOSE_MODE=${VERBOSE_MODE-"false"}
    CONTEXT=$(caller)
    if [ "${VERBOSE_MODE}" == "silly" ];then
        logger "$1" "DEBUG"
    else
        logger "$1" "DEBUG" >&6
    fi
    CONTEXT=
}

logError() {
    CONTEXT=$(caller)
    logger "$1" "ERROR"
    CONTEXT=
}

errorExit () {
    CONTEXT=$(caller)
    logger "$1" "ERROR"
    CONTEXT=
    exit 1
}

warn () {
    CONTEXT=$(caller)
    logger "$1" "WARN"
    CONTEXT=
}

note () {
    CONTEXT=$(caller)
    logger "$1" "NOTE"
    CONTEXT=
}

bannerStart() {
    title=$1
    echo
    echo -e "\033[1m${title}\033[0m"
    echo
}

bannerSection() {
    title=$1
    echo
    echo -e "******************************** ${title} ********************************"
    echo
}

bannerSubSection() {
    title=$1
    echo
    echo -e "************** ${title} *******************"
    echo
}

bannerMessge() {
    title=$1
    echo
    echo -e "********************************"
    echo -e "${title}"
    echo -e "********************************"
    echo
}

setRed () {
    local input="$1"
    echo -e \\033[31m${input}\\033[0m
}
setGreen () {
    local input="$1"
    echo -e \\033[32m${input}\\033[0m
}
setYellow () {
    local input="$1"
    echo -e \\033[33m${input}\\033[0m
}

logger_addLinebreak () {
    echo -e "---\n"
}

bannerImportant() {
    title=$1
    local bold="\033[1m"
    local noColour="\033[0m"
    echo
    echo -e "${bold}######################################## IMPORTANT ########################################${noColour}"
    echo -e "${bold}${title}${noColour}"
    echo -e "${bold}###########################################################################################${noColour}"
    echo
}

bannerEnd() {
    #TODO pass a title and calculate length dynamically so that start and end look alike
    echo
    echo "*****************************************************************************"
    echo
}

banner() {
    title=$1
    content=$2
    bannerStart "${title}"
    echo -e "$content"
}

# The logic below helps us redirect content we'd normally hide to the log file. 
    #
    # We have several commands which clutter the console with output and so use 
    # `cmd > /dev/null` - this redirects the command's output to null.
    # 
    # However, the information we just hid maybe useful for support. Using the code pattern
    # `cmd >&6` (instead of `cmd> >/dev/null` ), the command's output is hidden from the console 
    # but redirected to the installation log file
    # 

#Default value of 6 is just null
exec 6>>/dev/null
redirectLogsToFile() {
    echo ""
    # local file=$1

    # [ ! -z "${file}" ] || return 0

    # local logDir=$(dirname "$file")

    # if [ ! -f "${file}" ]; then
    #     [ -d "${logDir}" ] || mkdir -p ${logDir} || \
    #     ( echo "WARNING : Could not create parent directory (${logDir}) to redirect console log : ${file}" ; return 0 )
    # fi

    # #6 now points to the log file
    # exec 6>>${file}
    # #reference https://unix.stackexchange.com/questions/145651/using-exec-and-tee-to-redirect-logs-to-stdout-and-a-log-file-in-the-same-time
    # exec 2>&1 > >(tee -a "${file}")
}

# Check if a give key contains any sensitive string as part of it
# Based on the result, the caller can decide its value can be displayed or not
#   Sample usage : isKeySensitive "${key}" && displayValue="******" || displayValue=${value}
isKeySensitive(){
    local key=$1
    # keep all the sensitive keys in lower case
    local sensitiveKeys="password|secret|key|token|extrajavaopts|database\.url"

    if [ -z "${key}" ]; then
        return 1
    else
        local lowercaseKey=$(echo "${key}" | tr '[:upper:]' '[:lower:]' 2>/dev/null)
        [[ "${lowercaseKey}" =~ ${sensitiveKeys} ]] && return 0 || return 1
    fi
}

getPrintableValueOfKey(){
    local displayValue=
    local key="$1"
    if [ -z "$key" ]; then
        # This is actually an incorrect usage of this method but any logging will cause unexpected content in the caller
        echo -n ""
        return
    fi

    local value="$2"
    isKeySensitive "${key}" && displayValue="$SENSITIVE_KEY_VALUE" || displayValue="${value}"
    echo -n $displayValue
}

_createConsoleLog(){
    if [ -z "${JF_PRODUCT_HOME}" ]; then
        return
    fi
    local targetFile="${JF_PRODUCT_HOME}/var/log/console.log"
    mkdir -p "${JF_PRODUCT_HOME}/var/log" || true
    if [ ! -f ${targetFile} ]; then
        touch $targetFile > /dev/null 2>&1 || true
    fi
    chmod 640 $targetFile > /dev/null 2>&1 || true
}

# Output from application's logs are piped to this method. It checks a configuration variable to determine if content should be logged to 
# the common console.log file
redirectServiceLogsToFile() {

    local result="0"
    local SKIP="${FLAG_N}"
    local targetFile=

    # check if the function getSystemValue exists
    LC_ALL=C type getSystemValue > /dev/null 2>&1 || result="$?"
    if [[ "$result" != "0" ]]; then
        warn "Couldn't find the systemYamlHelper. Skipping log redirection"
        SKIP="${FLAG_Y}"
    fi

    getSystemValue "shared.logging.consoleLog.enabled" "NOT_SET"
    if [[ "${SKIP}" == "${FLAG_N}" && "${YAML_VALUE}" == "false" ]]; then
        logger "Redirection is set to false. Skipping log redirection"
        SKIP="${FLAG_Y}"
    fi

    if [[ "${SKIP}" == "${FLAG_N}" && -z "${JF_PRODUCT_HOME}" || "${JF_PRODUCT_HOME}" == "" ]]; then
        warn "JF_PRODUCT_HOME is unavailable. Skipping log redirection"
        SKIP="${FLAG_Y}"
    fi

    if [[ "${SKIP}" == "${FLAG_Y}" ]]; then
        targetFile="/dev/null"
    else
        targetFile="${JF_PRODUCT_HOME}/var/log/console.log"
        _createConsoleLog
    fi

    while read -r line; do
        printf '%s\n' "${line}" >> $targetFile || return 0 # Don't want to log anything - might clutter the screen
    done
}

## Display environment variables starting with JF_ along with its value
## Value of sensitive keys will be displayed as "******"
##
## Sample Display :
##
## ========================
## JF Environment variables
## ========================
##
## JF_SHARED_NODE_ID                   : locahost
## JF_SHARED_JOINKEY                   : ******
##
##
displayEnv() {
    local JFEnv=$(printenv | grep ^JF_ 2>/dev/null)
    local key=
    local value=

    if [ -z "${JFEnv}" ]; then
        return
    fi

    cat << ENV_START_MESSAGE

========================
JF Environment variables
========================
ENV_START_MESSAGE

    for entry in ${JFEnv}; do
        key=$(echo "${entry}" | awk -F'=' '{print $1}')
        value=$(echo "${entry}" | cut -d '=' -f2-)

        isKeySensitive "${key}" && value="******" || value=${value}
        
        printf "\n%-35s%s" "${key}" " : ${value}"
    done
    echo;
}

## Check if redirection to console log is disabled
##
## Sample usage:
##
## if $(isConsoleLogDisabled); then
##    // its disabled, forget about console.log
## else
##    // console.log is important, write all information to it
## fi
##
isConsoleLogDisabled(){
    local result=
    # check if the function getSystemValue exists
    LC_ALL=C type getSystemValue > /dev/null 2>&1
    result="$?"
    if [[ "$result" != "0" ]]; then
        return 1;
    fi

    getSystemValue "shared.logging.consoleLog.enabled" "NOT_SET" >/dev/null 2>&1

    # disable console log in docker container by default as this effects performance - INST-1238
    if [[ $(isRunningInsideAContainer 2>/dev/null) == "$FLAG_Y" && "${YAML_VALUE}" == "NOT_SET" ]] || [[ "${YAML_VALUE}" == "false" ]] ; then
        return 0;
    else
        return 1;
    fi
}

_addLogRotateConfiguration() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    # mandatory inputs
    local confFile="$1"
    local logFile="$2"

    # Method available in _ioOperations.sh
    LC_ALL=C type io_setYQPath > /dev/null 2>&1 || return 1

    io_setYQPath

    # Method available in _systemYamlHelper.sh
    LC_ALL=C type getSystemValue > /dev/null 2>&1 || return 1

    local frequency="daily"
    local archiveFolder="archived"

    local compressLogFiles=
    local delaycompressEnable=
    getSystemValue "shared.logging.rotation.compress" "true"
    if [[ "${YAML_VALUE}" == "true" ]]; then
        compressLogFiles="compress"
        delaycompressEnable="delaycompress"
    fi

    getSystemValue "shared.logging.rotation.maxFiles" "10"
    local noOfBackupFiles="${YAML_VALUE}"

    getSystemValue "shared.logging.rotation.maxSizeMb" "25"
    local sizeOfFile="${YAML_VALUE}M"

    logDebug "Adding logrotate configuration for [$logFile] to [$confFile]"

    # Add configuration to file
    local confContent=$(cat << LOGROTATECONF
$logFile {
    $frequency
    missingok
    copytruncate
    rotate $noOfBackupFiles
    $compressLogFiles
    $delaycompressEnable
    notifempty
    olddir $archiveFolder
    dateext
    extension .log
    dateformat -%Y-%m-%d-%s
    size ${sizeOfFile}
}
LOGROTATECONF
) 
    echo "${confContent}" > ${confFile} || return 1
}

_operationIsBySameUser() {
    local targetUser="$1"
    local currentUserID=$(id -u)
    local currentUserName=$(id -un)

    if [ $currentUserID == $targetUser ] || [ $currentUserName == $targetUser ]; then
        echo -n "yes"
    else   
        echo -n "no"
    fi
}

_addCronJobForLogrotate() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    
    # Abort if logrotate is not available
    [ "$(io_commandExists 'crontab')" != "yes" ] && warn "cron is not available" && return 1

    # mandatory inputs
    local productHome="$1"
    local confFile="$2"
    local cronJobOwner="$3"

    # We want to use our binary if possible. It may be more recent than the one in the OS
    local logrotateBinary="$productHome/app/third-party/logrotate/logrotate"

    if [ ! -f "$logrotateBinary" ]; then
        logrotateBinary="logrotate"
        [ "$(io_commandExists 'logrotate')" != "yes" ] && warn "logrotate is not available" && return 1
    fi
    local cmd="$logrotateBinary ${confFile} --state $productHome/var/etc/logrotate/logrotate-state" #--verbose

    id -u $cronJobOwner > /dev/null 2>&1 || { warn "User $cronJobOwner does not exist. Aborting logrotate configuration" && return 1; }
    
    # Remove the existing line
    removeLogRotation "$productHome" "$cronJobOwner" || true

    # Run logrotate daily at the 55th minute of every hour
    local cronInterval="55 * * * * $cmd"

    local standaloneMode=$(_operationIsBySameUser "$cronJobOwner")

    # If this is standalone mode, we cannot use -u - the user running this process may not have the necessary privileges
    if [ "$standaloneMode" == "no" ]; then
        (crontab -l -u $cronJobOwner 2>/dev/null; echo "$cronInterval") | crontab -u $cronJobOwner -
    else
        (crontab -l 2>/dev/null; echo "$cronInterval") | crontab -
    fi
}

## Configure logrotate for a product
## Failure conditions:
## If logrotation could not be setup for some reason
## Parameters:
## $1: The product name
## $2: The product home
## Depends on global: none
## Updates global: none
## Returns: NA

configureLogRotation() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    
    # disable console log in docker container as this effects performance - INST-1238
    if $(isConsoleLogDisabled >/dev/null 2>&1); then
        return 0;
    fi

    # mandatory inputs
    local productName="$1"
    if [ -z $productName ]; then
        warn "Incorrect usage. A product name is necessary for configuring log rotation" && return 1
    fi
    
    local productHome="$2"
    if [ -z $productHome ]; then
        warn "Incorrect usage. A product home folder is necessary for configuring log rotation" && return 1
    fi

    local logFile="${productHome}/var/log/console.log"
    if [[ $(uname) == "Darwin" ]]; then
        logger "Log rotation for [$logFile] has not been configured. Please setup manually"
        return 0
    fi
    
    local userID="$3"
    if [ -z $userID ]; then
        warn "Incorrect usage. A userID is necessary for configuring log rotation" && return 1
    fi

    local groupID=${4:-$userID}
    local logConfigOwner=${5:-$userID}

    logDebug "Configuring log rotation as user [$userID], group [$groupID], effective cron User [$logConfigOwner]"
    
    local errorMessage="Could not configure logrotate. Please configure log rotation of the file: [$logFile] manually"

    local confFile="${productHome}/var/etc/logrotate/logrotate.conf"

    # TODO move to recursive method
    createDir "${productHome}" "$userID" "$groupID" || { warn "${errorMessage}" && return 1; }
    createDir "${productHome}/var" "$userID" "$groupID" || { warn "${errorMessage}" && return 1; }
    createDir "${productHome}/var/log" "$userID" "$groupID" || { warn "${errorMessage}" && return 1; }
    createDir "${productHome}/var/log/archived" "$userID" "$groupID" || { warn "${errorMessage}" && return 1; }
    
    # TODO move to recursive method
    createDir "${productHome}/var/etc"  "$userID" "$groupID" || { warn "${errorMessage}" && return 1; }
    createDir "${productHome}/var/etc/logrotate" "$userID" "$groupID" || { warn "${errorMessage}" && return 1; }

    # conf file should be owned by the user running the script
    createFile "${confFile}" "$userID" "$groupID" || { warn "Could not create configuration file [$confFile]" return 1; }

    _addLogRotateConfiguration "${confFile}" "${logFile}" "$userID" "$groupID" || { warn "${errorMessage}" && return 1; }
    _addCronJobForLogrotate "${productHome}" "${confFile}" "${logConfigOwner}" || { warn "${errorMessage}" && return 1; }
}

_pauseExecution() {
    if [ "${VERBOSE_MODE}" == "debug" ]; then
      
        local breakPoint="$1"
        if [ ! -z "$breakPoint" ]; then
            printf "${cBlue}Breakpoint${cClear} [$breakPoint] "
            echo ""
        fi
        printf "${cBlue}Press enter once you are ready to continue${cClear}"
        read -s choice
        echo ""
    fi
}

# removeLogRotation "$productHome" "$cronJobOwner" || true
removeLogRotation() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    if [[ $(uname) == "Darwin" ]]; then
        logDebug "Not implemented for Darwin."
        return 0
    fi
    local productHome="$1"
    local cronJobOwner="$2"
    local standaloneMode=$(_operationIsBySameUser "$cronJobOwner")

    local confFile="${productHome}/var/etc/logrotate/logrotate.conf"
    
    if [ "$standaloneMode" == "no" ]; then
        crontab -l -u $cronJobOwner 2>/dev/null | grep -v "$confFile" | crontab -u $cronJobOwner -
    else
        crontab -l 2>/dev/null | grep -v "$confFile" | crontab -
    fi
}

# NOTE: This method does not check the configuration to see if redirection is necessary.
# This is intentional. If we don't redirect, tomcat logs might get redirected to a folder/file
# that does not exist, causing the service itself to not start
setupTomcatRedirection() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"

    if $(isConsoleLogDisabled >/dev/null 2>&1); then
        export CATALINA_OUT="/dev/null"
        logger "Redirection is set to false. Skipping catalina log redirection"
        return 0;
    else
        local consoleLog="${JF_PRODUCT_HOME}/var/log/console.log"
        _createConsoleLog
        export CATALINA_OUT="${consoleLog}"
    fi
}

setupScriptLogsRedirection() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    if [ -z "${JF_PRODUCT_HOME}" ]; then
        logDebug "No JF_PRODUCT_HOME. Returning"
        return
    fi
    # Create the console.log file if it is not already present
    # _createConsoleLog || true
    # # Ensure any logs (logger/logError/warn) also get redirected to the console.log
    # # Using installer.log as a temparory fix. Please change this to console.log once INST-291 is fixed
    export LOG_BEHAVIOR_ADD_REDIRECTION="${JF_PRODUCT_HOME}/var/log/console.log"
    export LOG_BEHAVIOR_ADD_META="$FLAG_Y"
}

# Returns Y if this method is run inside a container
isRunningInsideAContainer() {
    if [ -f "/.dockerenv" ]; then
        echo -n "$FLAG_Y"
    else
        echo -n "$FLAG_N"
    fi 
}

_messageBeforePrompt() {
    if [ "$DONT_PROMPT_USE_DEFAULTS" != "$FLAG_Y" ];then
        logRaw "$1"
    fi
}



POSTGRES_USER=999
NGINX_USER=104
NGINX_GROUP=107
ES_USER=1000
REDIS_USER=999
MONGO_USER=999
RABBITMQ_USER=999
LOG_FILE_PERMISSION=640
PID_FILE_PERMISSION=644

# Copy file
copyFile(){
    local source=$1
    local target=$2
    local mode=${3:-overwrite}
    local enableVerbose=${4:-"${FLAG_N}"}
    local verboseFlag=""

    if [ ! -z "${enableVerbose}" ] && [ "${enableVerbose}" == "${FLAG_Y}" ]; then
        verboseFlag="-v"
    fi

    if [[ ! ( $source && $target ) ]]; then
        warn "Source and target is mandatory to copy file"
        return 1
    fi

    if [[ -f "${target}" ]]; then
        [[ "$mode" = "overwrite" ]] && ( cp ${verboseFlag} -f "$source" "$target" || errorExit "Unable to copy file, command : cp -f ${source} ${target}") || true
    else
        cp ${verboseFlag} -f "$source" "$target" || errorExit "Unable to copy file, command : cp -f ${source} ${target}"
    fi
}

# Copy files recursively from given source directory to destination directory
# This method wil copy but will NOT overwrite
# Destination will be created if its not available
copyFilesNoOverwrite(){
    local src=$1
    local dest=$2
    local enableVerboseCopy="${3:-${FLAG_Y}}"

    if [[ -z "${src}" || -z "${dest}" ]]; then
        return
    fi

    if [ -d "${src}" ] && [ "$(ls -A ${src})" ]; then
        local relativeFilePath=""
        local targetFilePath=""

        for file in $(find ${src} -type f 2>/dev/null) ; do
            # Derive relative path and attach it to destination 
            # Example : 
            #       src=/extra_config
            #       dest=/var/opt/jfrog/artifactory/etc
            #       file=/extra_config/config.xml
            #       relativeFilePath=config.xml
            #       targetFilePath=/var/opt/jfrog/artifactory/etc/config.xml
            relativeFilePath=${file/${src}/}
            targetFilePath=${dest}${relativeFilePath}

            createDir "$(dirname "$targetFilePath")"
            copyFile "${file}" "${targetFilePath}" "no_overwrite" "${enableVerboseCopy}"
        done
    fi    
}

#    TODO : WINDOWS ?
#  Check the max open files and open processes set on the system
checkULimits () {
    local minMaxOpenFiles=${1:-32000}
    local minMaxOpenProcesses=${2:-1024}
    local setValue=${3:-true}
    local warningMsgForFiles=${4}
    local warningMsgForProcesses=${5}

    logger "Checking open files and processes limits"

    local currentMaxOpenFiles=$(ulimit -n)
    logger "Current max open files is $currentMaxOpenFiles"
    if [ ${currentMaxOpenFiles} != "unlimited" ] && [ "$currentMaxOpenFiles" -lt "$minMaxOpenFiles" ]; then
        if [ "${setValue}" ]; then
            ulimit -n "${minMaxOpenFiles}" >/dev/null 2>&1 || warn "Max number of open files $currentMaxOpenFiles is low!"
            [ -z "${warningMsgForFiles}" ] || warn "${warningMsgForFiles}"
        else
            errorExit "Max number of open files $currentMaxOpenFiles, is too low. Cannot run the application!"
        fi
    fi

    local currentMaxOpenProcesses=$(ulimit -u)
    logger "Current max open processes is $currentMaxOpenProcesses"
    if [ "$currentMaxOpenProcesses" != "unlimited" ] && [ "$currentMaxOpenProcesses" -lt "$minMaxOpenProcesses" ]; then
        if [ "${setValue}" ]; then
            ulimit -u "${minMaxOpenProcesses}" >/dev/null 2>&1 || warn "Max number of open files $currentMaxOpenFiles is low!"
            [ -z "${warningMsgForProcesses}" ] || warn "${warningMsgForProcesses}"
        else
            errorExit "Max number of open files $currentMaxOpenProcesses, is too low. Cannot run the application!"
        fi
    fi
}

createDirs() {
    local appDataDir=$1
    local serviceName=$2
    local folders="backup bootstrap data etc logs work"

    [ -z "${appDataDir}" ]  && errorExit "An application directory is mandatory to create its data structure"  || true
    [ -z "${serviceName}" ] && errorExit "A service name is mandatory to create service data structure"         || true

    for folder in ${folders}
    do
        folder=${appDataDir}/${folder}/${serviceName}
        if [ ! -d "${folder}" ]; then
            logger "Creating folder : ${folder}"
            mkdir -p "${folder}" || errorExit "Failed to create ${folder}"
        fi
    done
}


testReadWritePermissions () {
    local dir_to_check=$1
    local error=false

    [ -d ${dir_to_check} ] || errorExit "'${dir_to_check}' is not a directory"

    local test_file=${dir_to_check}/test-permissions

    # Write file
    if echo test > ${test_file} 1> /dev/null 2>&1; then
        # Write succeeded. Testing read...
        if cat ${test_file} > /dev/null; then
            rm -f ${test_file}
        else
            error=true
        fi
    else
        error=true
    fi

    if [ ${error} == true ]; then
        return 1
    else
        return 0
    fi
}

# Test directory has read/write permissions for current user
testDirectoryPermissions () {
    local dir_to_check=$1
    local error=false

    [[ "$(uname)" == "Darwin" ]] && return 0

    [ -d ${dir_to_check}  ] || errorExit "'${dir_to_check}' is not a directory"

    local u_id=$(id -u)
    local id_str="id ${u_id}"

    logger "Testing directory ${dir_to_check} has read/write permissions for user ${id_str}"

    if ! testReadWritePermissions ${dir_to_check}; then
        error=true
    fi

    if [ "${error}" == true ]; then
        local stat_data=$(stat -Lc "Directory: %n, permissions: %a, owner: %U, group: %G" ${dir_to_check})
        logger "###########################################################"
        logger "${dir_to_check} DOES NOT have proper permissions for user ${id_str}"
        logger "${stat_data}"
        logger "Mounted directory must have read/write permissions for user ${id_str}"
        logger "###########################################################"
        errorExit "Directory ${dir_to_check} has bad permissions for user ${id_str}"
    fi
    logger "Permissions for ${dir_to_check} are good"
}

# Utility method to create a directory path recursively with chown feature as
# Failure conditions:
## Exits if unable to create a directory
# Parameters:
## $1: Root directory from where the path can be created
## $2: List of recursive child directories seperated by space
## $3: user who should own the directory. Optional
## $4: group who should own the directory. Optional
# Depends on global: none
# Updates global: none
# Returns: NA
#
# Usage:
# createRecursiveDir "/opt/jfrog/product/var" "bootstrap tomcat lib" "user_name" "group_name"
createRecursiveDir(){
    local rootDir=$1
    local pathDirs=$2
    local user=$3
    local group=${4:-${user}}
    local fullPath=

    [ ! -z "${rootDir}" ] || return 0

    createDir "${rootDir}" "${user}" "${group}"

    [ ! -z "${pathDirs}" ] || return 0

    fullPath=${rootDir}

    for dir in ${pathDirs}; do
        fullPath=${fullPath}/${dir}
        createDir "${fullPath}" "${user}" "${group}"
    done
}

# Utility method to create a directory
# Failure conditions:
## Exits if unable to create a directory
# Parameters:
## $1: directory to create
## $2: user who should own the directory. Optional
## $3: group who should own the directory. Optional
# Depends on global: none
# Updates global: none
# Returns: NA

createDir(){
    local dirName="$1"
    local printMessage=no
    logSilly "Method ${FUNCNAME[0]} invoked with [$dirName]"
    [ -z "${dirName}" ] && return
    
    logDebug "Attempting to create ${dirName}"
    mkdir -p "${dirName}" || errorExit "Unable to create directory: [${dirName}]"
    local userID="$2"
    local groupID=${3:-$userID}

    # If UID/GID is passed, chown the folder
    if [ ! -z "$userID" ] && [ ! -z "$groupID" ]; then
        # Earlier, this line would have returned 1 if it failed. Now it just warns. 
        # This is intentional. Earlier, this line would NOT be reached if the folder already existed. 
        # Since it will always come to this line and the script may be running as a non-root user, this method will just warn if
        # setting permissions fails (so as to not affect any existing flows)
        io_setOwnershipNonRecursive "$dirName" "$userID" "$groupID" || warn "Could not set owner of [$dirName] to [$userID:$groupID]"
    fi
    # logging message to print created dir with user and group
    local logMessage=${4:-$printMessage}
    if [[ "${logMessage}" == "yes" ]]; then
        logger "Successfully created directory [${dirName}].  Owner: [${userID}:${groupID}]"
    fi
}

removeSoftLinkAndCreateDir () {
   local dirName="$1"
   local userID="$2"
   local groupID="$3"
   local logMessage="$4"
   removeSoftLink "${dirName}"
   createDir "${dirName}" "${userID}" "${groupID}" "${logMessage}"
}

# Utility method to remove a soft link
removeSoftLink () {
    local dirName="$1"
    if [[ -L "${dirName}" ]]; then
        targetLink=$(readlink -f "${dirName}")
        logger "Removing the symlink [${dirName}] pointing to [${targetLink}]"
        rm -f "${dirName}"
    fi
}

# Check Directory exist in the path
checkDirExists () {
    local directoryPath="$1"

    [[ -d "${directoryPath}" ]] && echo -n "true" || echo -n "false"
}


# Utility method to create a file
# Failure conditions:
# Parameters:
## $1: file to create
# Depends on global: none
# Updates global: none
# Returns: NA

createFile(){
    local fileName="$1"
    logSilly "Method ${FUNCNAME[0]} [$fileName]"
    [ -f "${fileName}" ] && return 0
    touch "${fileName}" || return 1

    local userID="$2"
    local groupID=${3:-$userID}

    # If UID/GID is passed, chown the folder
    if [ ! -z "$userID" ] && [ ! -z "$groupID" ]; then
        io_setOwnership "$fileName" "$userID" "$groupID" || return 1
    fi
}

# Check File exist in the filePath
# IMPORTANT- DON'T ADD LOGGING to this method
checkFileExists () {
    local filePath="$1"

    [[ -f "${filePath}" ]] && echo -n "true" || echo -n "false"
}

# Check for directories contains any (files or sub directories)
# IMPORTANT- DON'T ADD LOGGING to this method
checkDirContents () {
    local directoryPath="$1"
    if [[ "$(ls -1 "${directoryPath}" | wc -l)" -gt 0 ]]; then
        echo -n "true"
    else
        echo -n "false"
    fi
}

# Check contents exist in directory
# IMPORTANT- DON'T ADD LOGGING to this method
checkContentExists () {
    local source="$1"

    if [[ "$(checkDirContents "${source}")" != "true" ]]; then
        echo -n "false"
    else
        echo -n "true"
    fi
}

# Resolve the variable
# IMPORTANT- DON'T ADD LOGGING to this method
evalVariable () {
    local output="$1"
    local input="$2"

    eval "${output}"=\${"${input}"}
    eval echo \${"${output}"}
}

# Usage: if [ "$(io_commandExists 'curl')" == "yes" ]
# IMPORTANT- DON'T ADD LOGGING to this method
io_commandExists() {
    local commandToExecute="$1"
    hash "${commandToExecute}" >/dev/null 2>&1
    local rt=$?
    if [ "$rt" == 0 ]; then echo -n "yes"; else echo -n "no"; fi
}

# Usage: if [ "$(io_curlExists)" != "yes" ]
# IMPORTANT- DON'T ADD LOGGING to this method
io_curlExists() {
    io_commandExists "curl"
}

io_opensslExists() {
    io_commandExists "openssl"
}

io_hasMatch() {
    logSilly "Method ${FUNCNAME[0]}"
    local result=0
    logDebug "Executing [echo \"$1\" | grep \"$2\" >/dev/null 2>&1]"
    echo "$1" | grep "$2" >/dev/null 2>&1 || result=1
    return $result
}

# Utility method to check if the string passed (usually a connection url) corresponds to this machine itself
# Failure conditions: None
# Parameters:
## $1: string to check against
# Depends on global: none
# Updates global: IS_LOCALHOST with value "yes/no"
# Returns: NA

io_getIsLocalhost() {
    logSilly "Method ${FUNCNAME[0]}"
    IS_LOCALHOST="$FLAG_N"
    local inputString="$1"
    logDebug "Parsing [$inputString] to check if we are dealing with this machine itself"

    io_hasMatch "$inputString" "localhost" && {
        logDebug "Found localhost. Returning [$FLAG_Y]"
        IS_LOCALHOST="$FLAG_Y" && return;
    } || logDebug "Did not find match for localhost"
    
    local hostIP=$(io_getPublicHostIP)
    io_hasMatch "$inputString" "$hostIP" && {
        logDebug "Found $hostIP. Returning [$FLAG_Y]"
        IS_LOCALHOST="$FLAG_Y" && return;
    } || logDebug "Did not find match for $hostIP"
    
    local hostID=$(io_getPublicHostID)
    io_hasMatch "$inputString" "$hostID" && {
        logDebug "Found $hostID. Returning [$FLAG_Y]"
        IS_LOCALHOST="$FLAG_Y" && return;
    } || logDebug "Did not find match for $hostID"
    
    local hostName=$(io_getPublicHostName)
    io_hasMatch  "$inputString" "$hostName" && {
        logDebug "Found $hostName. Returning [$FLAG_Y]"
        IS_LOCALHOST="$FLAG_Y" && return;
    } || logDebug "Did not find match for $hostName"
    
}

# Usage: if [ "$(io_tarExists)" != "yes" ]
# IMPORTANT- DON'T ADD LOGGING to this method
io_tarExists() {
    io_commandExists "tar"
}

# IMPORTANT- DON'T ADD LOGGING to this method
io_getPublicHostIP() {
    local OS_TYPE=$(uname)
    local publicHostIP=
    if [ "${OS_TYPE}" == "Darwin" ]; then
        ipStatus=$(ifconfig en0 | grep "status" | awk '{print$2}')
        if [ "${ipStatus}" == "active" ]; then
            publicHostIP=$(ifconfig en0 | grep inet | grep -v inet6 | awk '{print $2}')
        else
            errorExit "Host IP could not be resolved!"
        fi
    elif [ "${OS_TYPE}" == "Linux" ]; then
        publicHostIP=$(hostname -i 2>/dev/null || echo "127.0.0.1")
    fi
    publicHostIP=$(echo "${publicHostIP}" | awk '{print $1}')
    echo -n "${publicHostIP}"
}

isIpV6(){
    local ip="${1}"
    if [[ ! -z "${ip}" ]] && [[ "${ip}" =~ ^.*:.*:.*$ ]]; then
        return 0;
    else
        return 1;
    fi
}

# Pass the key to be checked. The value of the key will be modified based on conditions
# Usage : _modifyValue "USER_CHOICE" "HOST_IP"
#          Before : USER_CHOICE="fe80::4001:aff:fe46:1e27"
#          After  : USER_CHOICE="[fe80::4001:aff:fe46:1e27]"
modifyValue(){
    local targetKey=${1}
    local yamlKey=${2}
    local targetValue=""

    if [[ -z "${targetKey}" ]] || [[ -z "${yamlKey}" ]]; then
        return 0;
    fi

    eval targetvalue=\${${targetKey}}

    [ -z "${targetvalue}" ] && return 0 || true

    case "${yamlKey}" in
        HOST_IP)
            # Enclose value within square brackets if its ipv6 
            if isIpV6 "${targetvalue}" && ! [[ "${targetvalue}" =~ ^\[.*\]$ ]]; then
                [[ "${targetvalue}" =~ ^\[.* ]] || targetvalue="[${targetvalue}"
                [[ "${targetvalue}" =~ .*\]$ ]] || targetvalue="${targetvalue}]"
                logger "Found an ipv6 value, enclosing it wih brackets : ${targetvalue}"
                eval ${targetKey}="${targetvalue}"
            fi
        ;;
    esac
}

# Will return the short host name (up to the first dot)
# IMPORTANT- DON'T ADD LOGGING to this method
io_getPublicHostName() {
    echo -n "$(hostname -s)"
}

# Will return the full host name (use this as much as possible)
# IMPORTANT- DON'T ADD LOGGING to this method
io_getPublicHostID() {
    echo -n "$(hostname)"
}

# Utility method to backup a file
# Failure conditions: NA
# Parameters: filePath
# Depends on global: none,
# Updates global: none
# Returns: NA
io_backupFile() {
    logSilly "Method ${FUNCNAME[0]}"
    fileName="$1"
    if [ ! -f "${filePath}" ]; then
        logDebug "No file: [${filePath}] to backup"
        return
    fi
    dateTime=$(date +"%Y-%m-%d-%H-%M-%S")
    targetFileName="${fileName}.backup.${dateTime}"
    yes | \cp -f "$fileName" "${targetFileName}"
    logger "File [${fileName}] backedup as [${targetFileName}]"
}

# Reference https://stackoverflow.com/questions/4023830/how-to-compare-two-strings-in-dot-separated-version-format-in-bash/4025065#4025065
is_number() {
    case "$BASH_VERSION" in
        3.1.*)
            PATTERN='\^\[0-9\]+\$'
            ;;
        *)
            PATTERN='^[0-9]+$'
            ;;
    esac

    [[ "$1" =~ $PATTERN ]]
}

io_compareVersions() {
    if [[ $# != 2 ]]
    then
        echo "Usage: min_version current minimum"
        return
    fi

    A="${1%%.*}"
    B="${2%%.*}"

    if [[ "$A" != "$1" && "$B" != "$2" && "$A" == "$B" ]]
    then
        io_compareVersions "${1#*.}" "${2#*.}"
    else
        if is_number "$A" && is_number "$B"
        then
            if [[ "$A" -eq "$B" ]]; then
                echo "0"
            elif [[ "$A" -gt "$B" ]]; then
	            echo "1"
	        elif [[ "$A" -lt "$B" ]]; then
	            echo "-1"
            fi
        fi
    fi
}

# Reference https://stackoverflow.com/questions/369758/how-to-trim-whitespace-from-a-bash-variable
# Strip all leading and trailing spaces
# IMPORTANT- DON'T ADD LOGGING to this method
io_trim() {
    local var="$1"
    # remove leading whitespace characters
    var="${var#"${var%%[![:space:]]*}"}"
    # remove trailing whitespace characters
    var="${var%"${var##*[![:space:]]}"}"
    echo -n "$var"
}

# temporary function will be removing it ASAP
# search for string and replace text in file
replaceText_migration_hook () {
    local regexString="$1"
    local replaceText="$2"
    local file="$3"

    if [[ "$(checkFileExists "${file}")" != "true" ]]; then
        return
    fi
    if [[ $(uname) == "Darwin" ]]; then
        sed -i '' -e "s/${regexString}/${replaceText}/" "${file}" || warn "Failed to replace the text in ${file}"
    else
        sed -i -e "s/${regexString}/${replaceText}/" "${file}" || warn "Failed to replace the text in ${file}"
    fi
}

# search for string and replace text in file
replaceText () {
    local regexString="$1"
    local replaceText="$2"
    local file="$3"

    if [[ "$(checkFileExists "${file}")" != "true" ]]; then
        return
    fi
    if [[ $(uname) == "Darwin" ]]; then
        sed -i '' -e "s#${regexString}#${replaceText}#" "${file}" || warn "Failed to replace the text in ${file}"
    else
        sed -i -e "s#${regexString}#${replaceText}#" "${file}" || warn "Failed to replace the text in ${file}"
        logDebug "Replaced [$regexString] with [$replaceText] in [$file]"
    fi
}

# search for string and prepend text in file
prependText () {
    local regexString="$1"
    local text="$2"
    local file="$3"

    if [[ "$(checkFileExists "${file}")" != "true" ]]; then
        return
    fi
    if [[ $(uname) == "Darwin" ]]; then
        sed -i '' -e '/'"${regexString}"'/i\'$'\n\\'"${text}"''$'\n' "${file}" || warn "Failed to prepend the text in ${file}"
    else
        sed -i -e '/'"${regexString}"'/i\'$'\n\\'"${text}"''$'\n' "${file}" || warn "Failed to prepend the text in ${file}"
    fi
}

# add text to beginning of the file
addText () {
    local text="$1"
    local file="$2"

    if [[ "$(checkFileExists "${file}")" != "true" ]]; then
        return
    fi
    if [[ $(uname) == "Darwin" ]]; then
        sed -i '' -e '1s/^/'"${text}"'\'$'\n/' "${file}" || warn "Failed to add the text in ${file}"
    else
        sed -i -e '1s/^/'"${text}"'\'$'\n/' "${file}" || warn "Failed to add the text in ${file}"
    fi
}

io_replaceString () {
    local value="$1"
    local firstString="$2"
    local secondString="$3"
    local separator=${4:-"/"}
    local updateValue=
    if [[ $(uname) == "Darwin" ]]; then
        updateValue=$(echo "${value}" | sed "s${separator}${firstString}${separator}${secondString}${separator}")
    else
        updateValue=$(echo "${value}" | sed "s${separator}${firstString}${separator}${secondString}${separator}")
    fi
    echo -n "${updateValue}"
}

_findYQ() {
    # logSilly "Method ${FUNCNAME[0]}" (Intentionally not logging. Does not add value)
    local parentDir="$1"
    if [ -z "$parentDir" ]; then
        return
    fi
    logDebug "Executing command [find "${parentDir}" -name third-party -type d]"
    local yq=$(find "${parentDir}" -name third-party -type d)
    for thirdPartyFolder in ${yq}; do
        if [ -d "${thirdPartyFolder}/yq" ]; then
            export YQ_PATH="${thirdPartyFolder}/yq"
            break
        fi
    done
}


io_setYQPath() {
    if [ -z "${YQ_PATH}" ] && [ ! -z "${COMPOSE_HOME}" ] && [ -d "${COMPOSE_HOME}" ]; then
        _findYQ "${COMPOSE_HOME}"
    fi
    # TODO We can remove this block after all the code is restructured.
    if [ -z "${YQ_PATH}" ] && [ ! -z "${SCRIPT_HOME}" ] && [ -d "${SCRIPT_HOME}" ]; then
        _findYQ "${SCRIPT_HOME}"
    fi

    # logSilly "Method ${FUNCNAME[0]}" (Intentionally not logging. Does not add value)  
    if [ -z "${YQ_PATH}" ] &&  [ ! -z "${JF_PRODUCT_HOME}" ] && [ -d "${JF_PRODUCT_HOME}" ]; then
        _findYQ "${JF_PRODUCT_HOME}"
    fi
}

io_getLinuxDistribution() {
    LINUX_DISTRIBUTION=

    # Make sure running on Linux
    [ $(uname -s) != "Linux" ] && return

    # Find out what Linux distribution we are on

    cat /etc/*-release | grep -i Red >/dev/null 2>&1 && LINUX_DISTRIBUTION=RedHat || true

    # OS 6.x
    cat /etc/issue.net | grep Red >/dev/null 2>&1 && LINUX_DISTRIBUTION=RedHat || true

    # OS 7.x
    cat /etc/*-release | grep -i centos >/dev/null 2>&1 && LINUX_DISTRIBUTION=CentOS && LINUX_DISTRIBUTION_VER="7" || true

    # OS 8.x
    grep -q -i "release 8" /etc/redhat-release >/dev/null 2>&1 && LINUX_DISTRIBUTION_VER="8" || true

    # OS 7.x
    grep -q -i "release 7" /etc/redhat-release >/dev/null 2>&1 && LINUX_DISTRIBUTION_VER="7" || true

    # OS 6.x
    grep -q -i "release 6" /etc/redhat-release >/dev/null 2>&1 && LINUX_DISTRIBUTION_VER="6" || true

    cat /etc/*-release | grep -i Red | grep -i 'VERSION=7' >/dev/null 2>&1 && LINUX_DISTRIBUTION=RedHat && LINUX_DISTRIBUTION_VER="7" || true

    cat /etc/*-release | grep -i debian >/dev/null 2>&1 && LINUX_DISTRIBUTION=Debian || true

    cat /etc/*-release | grep -i ubuntu >/dev/null 2>&1 && LINUX_DISTRIBUTION=Ubuntu || true

    # OS SLES
    cat /etc/*-release | grep -i SUSE >/dev/null 2>&1 && LINUX_DISTRIBUTION="SLES" && LINUX_DISTRIBUTION_VER="12-SP5" || true
}

## Utility method to check ownership of folders/files
## Failure conditions:
    ## If invoked with incorrect inputs - FATAL
    ## If file is not owned by the user & group
## Parameters:
    ## user
    ## group
    ## folder to chown    
## Globals: none
## Returns: none
## NOTE: The method does NOTHING if the OS is Mac
io_checkOwner () {
    logSilly "Method ${FUNCNAME[0]}"
    local osType=$(uname)
    
    if [ "${osType}" != "Linux" ]; then
        logDebug "Unsupported OS. Skipping check"
        return 0
    fi

    local file_to_check=$1
    local user_id_to_check=$2
    

    if [ -z "$user_id_to_check" ] || [ -z "$file_to_check" ]; then
        errorExit "Invalid invocation of method. Missing mandatory inputs"
    fi

    local group_id_to_check=${3:-$user_id_to_check}
    local check_user_name=${4:-"no"}

    logDebug "Checking permissions on [$file_to_check] for user [$user_id_to_check] & group [$group_id_to_check]"

    local stat=

    if [ "${check_user_name}" == "yes" ]; then
        stat=( $(stat -Lc "%U %G" ${file_to_check}) )
    else
        stat=( $(stat -Lc "%u %g" ${file_to_check}) )
    fi

    local user_id=${stat[0]}
    local group_id=${stat[1]}

    if [[ "${user_id}" != "${user_id_to_check}" ]] || [[ "${group_id}" != "${group_id_to_check}" ]] ; then
        logDebug "Ownership mismatch. [${file_to_check}] is not owned by [${user_id_to_check}:${group_id_to_check}]"
        return 1
    else
        return 0
    fi
}

## Utility method to change ownership of a file/folder - NON recursive
## Failure conditions:
    ## If invoked with incorrect inputs - FATAL
    ## If chown operation fails - returns 1
## Parameters: 
    ## user
    ## group
    ## file to chown    
## Globals: none
## Returns: none
## NOTE: The method does NOTHING if the OS is Mac

io_setOwnershipNonRecursive() {
    
    local osType=$(uname)
    if [ "${osType}" != "Linux" ]; then
        return
    fi

    local targetFile="$1"
    local user="$2"

    if [ -z "$user" ] || [ -z "$targetFile" ]; then
        errorExit "Invalid invocation of method. Missing mandatory inputs"
    fi

    local group=${3:-$user}
    logDebug "Method ${FUNCNAME[0]}. Executing [chown ${user}:${group} ${targetFile}]"
    chown ${user}:${group} "${targetFile}" || return 1
}

## Utility method to change ownership of a file. 
## IMPORTANT 
## If being called on a folder, should ONLY be called for fresh folders or may cause performance issues
## Failure conditions:
    ## If invoked with incorrect inputs - FATAL
    ## If chown operation fails - returns 1
## Parameters: 
    ## user
    ## group
    ## file to chown    
## Globals: none
## Returns: none
## NOTE: The method does NOTHING if the OS is Mac

io_setOwnership() {
    
    local osType=$(uname)
    if [ "${osType}" != "Linux" ]; then
        return
    fi

    local targetFile="$1"
    local user="$2"

    if [ -z "$user" ] || [ -z "$targetFile" ]; then
        errorExit "Invalid invocation of method. Missing mandatory inputs"
    fi

    local group=${3:-$user}
    logDebug "Method ${FUNCNAME[0]}. Executing [chown -R ${user}:${group} ${targetFile}]"
    chown -R ${user}:${group} "${targetFile}" || return 1
}

## Utility method to create third party folder structure necessary for Postgres
## Failure conditions:
## If creation of directory or assigning permissions fails
## Parameters: none
## Globals:
## POSTGRESQL_DATA_ROOT
## Returns: none
## NOTE: The method does NOTHING if the folder already exists
io_createPostgresDir() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    [ -z "${POSTGRESQL_DATA_ROOT}" ] && return 0

    logDebug "Property [${POSTGRESQL_DATA_ROOT}] exists. Proceeding"

    createDir "${POSTGRESQL_DATA_ROOT}/data"
    if [[ "$(uname)" != "Darwin" && $(stat -c "User:%u" ${POSTGRESQL_DATA_ROOT} | awk -F ':' '{print $2}') != ${POSTGRES_USER} ]]; then
        io_setOwnership  "${POSTGRESQL_DATA_ROOT}" "${POSTGRES_USER}" "${POSTGRES_USER}" || errorExit "Setting ownership of [${POSTGRESQL_DATA_ROOT}] to [${POSTGRES_USER}:${POSTGRES_USER}] failed"
    fi
}

## Utility method to create third party folder structure necessary for Nginx
## Failure conditions:
## If creation of directory or assigning permissions fails
## Parameters: none
## Globals:
## NGINX_DATA_ROOT
## Returns: none
## NOTE: The method does NOTHING if the folder already exists
io_createNginxDir() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    [ -z "${NGINX_DATA_ROOT}" ] && return 0

    logDebug "Property [${NGINX_DATA_ROOT}] exists. Proceeding"

    createDir "${NGINX_DATA_ROOT}"
    io_setOwnership  "${NGINX_DATA_ROOT}" "${NGINX_USER}" "${NGINX_GROUP}" || errorExit "Setting ownership of [${NGINX_DATA_ROOT}] to [${NGINX_USER}:${NGINX_GROUP}] failed"
}

## Utility method to create third party folder structure necessary for ElasticSearch
## Failure conditions:
## If creation of directory or assigning permissions fails
## Parameters: none
## Globals:
## ELASTIC_DATA_ROOT
## Returns: none
## NOTE: The method does NOTHING if the folder already exists
io_createElasticSearchDir() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    [ -z "${ELASTIC_DATA_ROOT}" ] && return 0

    logDebug "Property [${ELASTIC_DATA_ROOT}] exists. Proceeding"

    createDir "${ELASTIC_DATA_ROOT}/data"
    io_setOwnership  "${ELASTIC_DATA_ROOT}" "${ES_USER}" "${ES_USER}" || errorExit "Setting ownership of [${ELASTIC_DATA_ROOT}] to [${ES_USER}:${ES_USER}] failed"
}

## Utility method to create third party folder structure necessary for Redis
## Failure conditions:
## If creation of directory or assigning permissions fails
## Parameters: none
## Globals:
## REDIS_DATA_ROOT
## Returns: none
## NOTE: The method does NOTHING if the folder already exists
io_createRedisDir() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    [ -z "${REDIS_DATA_ROOT}" ] && return 0

    logDebug "Property [${REDIS_DATA_ROOT}] exists. Proceeding"

    createDir "${REDIS_DATA_ROOT}"
    io_setOwnership  "${REDIS_DATA_ROOT}" "${REDIS_USER}" "${REDIS_USER}" || errorExit "Setting ownership of [${REDIS_DATA_ROOT}] to [${REDIS_USER}:${REDIS_USER}] failed"
}

## Utility method to create third party folder structure necessary for Mongo
## Failure conditions:
## If creation of directory or assigning permissions fails
## Parameters: none
## Globals:
## MONGODB_DATA_ROOT
## Returns: none
## NOTE: The method does NOTHING if the folder already exists
io_createMongoDir() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    [ -z "${MONGODB_DATA_ROOT}" ] && return 0

    logDebug "Property [${MONGODB_DATA_ROOT}] exists. Proceeding"

    createDir "${MONGODB_DATA_ROOT}/logs"
    createDir "${MONGODB_DATA_ROOT}/configdb"
    createDir "${MONGODB_DATA_ROOT}/db"
    io_setOwnership  "${MONGODB_DATA_ROOT}" "${MONGO_USER}" "${MONGO_USER}" || errorExit "Setting ownership of [${MONGODB_DATA_ROOT}] to [${MONGO_USER}:${MONGO_USER}] failed"
}

## Utility method to create third party folder structure necessary for RabbitMQ
## Failure conditions:
## If creation of directory or assigning permissions fails
## Parameters: none
## Globals:
## RABBITMQ_DATA_ROOT
## Returns: none
## NOTE: The method does NOTHING if the folder already exists
io_createRabbitMQDir() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    [ -z "${RABBITMQ_DATA_ROOT}" ] && return 0

    logDebug "Property [${RABBITMQ_DATA_ROOT}] exists. Proceeding"

    createDir "${RABBITMQ_DATA_ROOT}"
    io_setOwnership  "${RABBITMQ_DATA_ROOT}" "${RABBITMQ_USER}" "${RABBITMQ_USER}" || errorExit "Setting ownership of [${RABBITMQ_DATA_ROOT}] to [${RABBITMQ_USER}:${RABBITMQ_USER}] failed"
}

# Add or replace a property in provided properties file
addOrReplaceProperty() {
    local propertyName=$1
    local propertyValue=$2
    local propertiesPath=$3
    local delimiter=${4:-"="}

    # Return if any of the inputs are empty
    [[ -z "$propertyName"   || "$propertyName"   == "" ]] && return
    [[ -z "$propertyValue"  || "$propertyValue"  == "" ]] && return
    [[ -z "$propertiesPath" || "$propertiesPath" == "" ]] && return

    grep "^${propertyName}\s*${delimiter}.*$" ${propertiesPath} > /dev/null 2>&1
    [ $? -ne 0 ] && echo -e "\n${propertyName}${delimiter}${propertyValue}" >> ${propertiesPath}
    sed -i -e "s|^${propertyName}\s*${delimiter}.*$|${propertyName}${delimiter}${propertyValue}|g;" ${propertiesPath}
}

# Remove a property in provided properties file
removeProperty() {
    local propertyName=$1
    local propertiesPath=$2
    local delimiter=${3:-":"}

    # Return if any of the inputs are empty
    [[ -z "$propertyName"   || "$propertyName"   == "" ]] && return 0
    [[ -z "$propertiesPath" || "$propertiesPath" == "" ]] && return 0

    grep "^${propertyName}\s*${delimiter}.*$" ${propertiesPath} > /dev/null 2>&1

    # If provided property exists in file remove the line
    if [ $? -eq 0 ]; then
        sed -i -e "/^${propertyName}\s*${delimiter}.*$/d" ${propertiesPath} || return 1
    fi
}

# Set property only if its not set
io_setPropertyNoOverride(){
    local propertyName=$1
    local propertyValue=$2
    local propertiesPath=$3

    # Return if any of the inputs are empty
    [[ -z "$propertyName"   || "$propertyName"   == "" ]] && return
    [[ -z "$propertyValue"  || "$propertyValue"  == "" ]] && return
    [[ -z "$propertiesPath" || "$propertiesPath" == "" ]] && return

    grep "^${propertyName}:" ${propertiesPath} > /dev/null 2>&1
    if [ $? -ne 0 ]; then
        echo -e "${propertyName}: ${propertyValue}" >> ${propertiesPath} || warn "Setting property ${propertyName}: ${propertyValue} in [ ${propertiesPath} ] failed"
    else
        logger "Skipping update of property : ${propertyName}" >&6
    fi
}

# Add a line to a file if it doesn't already exist
addLine() {
    local line_to_add=$1
    local target_file=$2
    logger "Trying to add line $1 to $2" >&6 2>&1
    cat "$target_file" | grep -F "$line_to_add" -wq >&6 2>&1
    if [ $? != 0  ]; then
        logger "Line does not exist and will be added" >&6 2>&1
        echo $line_to_add >> $target_file || errorExit "Could not update $target_file"
    fi    
}

# Utility method to check if a value (first paramter) exists in an array (2nd parameter)
# 1st parameter "value to find"
# 2nd parameter "The array to search in. Please pass a string with each value separated by space"
# Example: containsElement "y" "y Y n N"
containsElement () {
  local searchElement=$1
  local searchArray=($2)
  local found=1
  for elementInIndex in "${searchArray[@]}";do
    if [[ $elementInIndex == $searchElement ]]; then
        found=0
    fi
  done
  return $found
}

# Utility method to get user's choice
# 1st parameter "what to ask the user"
# 2nd parameter "what choices to accept, separated by spaces"
# 3rd parameter "what is the default choice (to use if the user simply presses Enter)"
# Example 'getUserChoice "Are you feeling lucky? Punk!" "y n Y N" "y"'
getUserChoice(){
    configureLogOutput
    read_timeout=${read_timeout:-0.5}
    local choice="na"
    local text_to_display=$1
    local choices=$2
    local default_choice=$3
    users_choice=

    until containsElement "$choice" "$choices"; do
        echo "";echo "";
        sleep $read_timeout #This ensures correct placement of the question.
        read -p  "$text_to_display :" choice
        : ${choice:=$default_choice}
    done
    users_choice=$choice
    echo -e "\n$text_to_display: $users_choice" >&6
    sleep $read_timeout #This ensures correct logging
}

setFilePermission () {
    local permission=$1
    local file=$2
    chmod "${permission}" "${file}" || warn "Setting permission ${permission} to file [ ${file} ] failed"
}

# Read properties file and (Add|Update) the property
propertyTransform () {
    local confFile="$1"
    local propName="${2}"
    local newPropValue="${3}"
    local check=
   
   [[ -z "$confFile"     || "$confFile"     == "" ]] && return
   [[ -z "$propName"     || "$propName"     == "" ]] && return
   [[ -z "$newPropValue" || "$newPropValue" == "" ]] && return

    while IFS='=' read -r key value || [ -n "${key}" ];
    do
        [[ -z "${key}" || "${key}" == $'\n' || "${key}" =~ \#.* ]] && continue
        key="$(io_trim "${key}")"
        if [[ "${key}" == "${propName}" ]]; then
            local existingPropValue="$(io_trim "${value}")"
            # Compare property value and update, if there is a  change
            if [[ "${existingPropValue}" != "${newPropValue}" ]]; then
                addOrReplaceProperty "${propName}" "${newPropValue}" "${confFile}" " = "
                check=false && break
            fi
            check=false && break
        else
            check=true
        fi
    done < "${confFile}"
    # Add new property
    if [[ "${check}" == "true" ]]; then
        local property=$(echo "${propName}" = "${newPropValue}")
        echo -e "\n${property}" >> "${confFile}"
    fi
}

# Transform array of objects from system yaml into properties files
transformPropertiesToFile () {
   local confFile="$1"
   local yamlKey="$2"
   local systemYamlFile="$3"
   local ignorePropName=($4)
   local status=

   [[ -z "$confFile"        || "$confFile" == "" ]] && return
   [[ -z "$yamlKey"         || "$yamlKey"  == "" ]] && return
   [[ -z "$systemYamlFile"  || "$systemYamlFile"  == "" ]] && return

   #get count of properties list in sysytem yaml
    getYamlValue "${yamlKey}[].name" "${systemYamlFile}" "false"
    local propCount="${YAML_VALUE}"
    if [[ -z "${propCount}" ]]; then
        return
    fi
    propCount=$(echo ${propCount} | tr '-' ' ')
    propCount=$(io_trim "${propCount}")
    propCount=($propCount)
    propCount=${#propCount[@]}

    for ((i = 0 ; i < "${propCount}" ; i++)); do
        # Read properties from system.yaml
        getYamlValue "${yamlKey}[$i].name" "${systemYamlFile}" "false"
        local propName="${YAML_VALUE}"
        if [[ -z "${propName}" ]]; then
            return
        fi
        getYamlValue "${yamlKey}[$i].value" "${systemYamlFile}" "false"
        local newPropValue="${YAML_VALUE}"
        if [[ -z "${newPropValue}" ]]; then
            warn "Empty value for [${yamlKey}[$i].name] in [${systemYamlFile}]"
            return
        fi
        if [[ ! "${ignorePropName[@]}" =~  "${propName}" ]]; then
            # Read properties file and (Add|Update) the property
            propertyTransform "${confFile}" "${propName}" "${newPropValue}"
        else
            status=false
        fi
    done
    if [[ "${status}" == "false" ]]; then
        logger "One of the properties [${ignorePropName[*]}] was found in the [${systemYamlFile}], Ignoring it!"
        logger "Properties [${ignorePropName[*]}] are used and set by the application and must not be set in the [${systemYamlFile}]."
    fi
}

randomPasswordGenerator () {
    local entry="$1"
    local randomPassword=
    local rt="$(io_opensslExists)"
    if [[ "$rt" == "yes" ]]; then
        randomPassword=$(echo "${entry}" | openssl rand -hex 8 2>/dev/null)
    else
        randomPassword=$(cat /dev/urandom | tr -dc 'a-zA-Z0-9' | head -c 8 2>/dev/null)
    fi
    echo "${randomPassword}"
}

# Use bash in-built to open a tcp connection to associated socket using redirections
# This expects the url to be of the format http://localhost:8082/router/api/v1/system/ping
# Can be used to make simple get calls
httpGet(){
    local url=$1
    local dns=
    local port=
    local api=
    local tag="Connection: close"

    if [[ -z "$url" || "$url" = "" ]]; then
        errorExit "URL is not passed for health check!"
    fi

    # extract dns from url
    dns=$(echo "$url" | awk -F ':' '{print $2}' )
    dns=${dns/\/\//""} # //localhost ==> localhost

    # extract port
    portWithApi=$(echo "$url" | awk -F ':' '{print $3}' )
    port=$(grep -o '[0-9]*' <<< "$portWithApi")  
    port=$(echo ${port} | awk -F ' ' '{print $1}') # 8082/router/api/v1/system/ping ==> 8082

    # extract api
    api=$(echo "${portWithApi}" | tr "${port}" " ")
    api=$(io_trim "${api}")  # 8082/router/api/v1/system/ping ==> /router/api/v1/system/ping
    ping -c 1 -W 1 "${dns}"
    if [ $? -ne 0 ]; then
        return
    fi
    exec 5<> /dev/tcp/${dns}/${port}
    cat <&5 &
    echo -en "GET ${api} HTTP/1.1\r\nHost: ${dns}:${port}\r\n${tag}\r\n\r\n" >&5
}


# Utility method to validate mandatory env variables
# Failure conditions:
## Exits if any of the variables are not found
# Parameters: a string containing space-delimited variable names
# Depends on global: none
# Updates global: none
# Returns: NA

validateMandatoryFields() {
    logDebug "Method validateMandatoryFields"
    local mandatoryKeys=($1)
    local mandatoryFields=
    for key in "${mandatoryKeys[@]}"; do
        eval resolvedKey=\${${key}}
        if [ -z "${resolvedKey}" ]; then
            mandatoryFields="${mandatoryFields} ${key}"
        fi
    done

    if [ -n "${mandatoryFields}" ];then
        errorExit "The following mandatory env variables are missing [${mandatoryFields} ]"
    fi
}

# Utility method to determine the OS 
# Failure conditions:
## Exits if OS is neither Mac nor Linux
# Parameters: none
# Depends on global: none
# Returns: NA

validateLinuxOrMac () {
    local OS_TYPE=$(uname)
    if [[ ! $OS_TYPE =~ Darwin|Linux ]]; then
        errorExit "This script can run on Mac or Linux only!"
    fi
}

# Utility method to validate that the user running the script is sudo
# Failure conditions:
## Exits if user is not sudo
# Parameters: none
# Updates global: none
# Returns: NA

validateSudo () {
    local OS_TYPE=$(uname)
    if [ "$OS_TYPE" == "Linux" ] && [ $EUID -ne 0 ]; then
        errorExit "This script must be run as root or with sudo"
    fi
}

validationCheckUMask() {
    # Check user file-creation mode mask
    echo "Checking the user file-creation mode mask (umask)" >&6 2>&1
    local valUmask=$(umask)
    if [[ "${valUmask}" =~ 0022|022|0002|002 ]]; then
        echo "The system has a default umask setup, proceeding..." >&6 2>&1
    else
        echo "To continue with Mission Control installation, allow access mode for new files and directories"
        echo "Set umask as follows:"
        echo "$ umask 022"
        echo "Note: WE RECOMMEND RESTARTING MACHINE TO MAKE SURE THE CHANGE TAKES EFFECT"
        errorExit "Installation failed on umask mode"
    fi
}

# Accept disk name by displaying available one on the machine
# Validate entered value by the user
# Entered value will be stored in global variable JFMC_STORAGE_DEVICE_NAME
_getDiskName(){
    local disks=
    local choice=
    local default_choice=

    sleep .5
    logger "Please enter the disk name which will store product data, this will be checked for minimum requirements"
    logger "Here is a list of disks available :"
    # Display disk information in human readable format
    df -h
    echo -e ""
    # Store disk names to validate against user entered value
    disks=$(df | awk -F " " '{print $1}' | sed -n '1!p')
    sleep .5

    default_choice=$(array=( $disks ) && echo ${array[0]})

    if rpmDeb_isUseDefaultsEnabled; then
        logger "Automation mode is enabled, considering first value of \"df\" output : $default_choice"
        choice="$default_choice"
    else
        choice="NOT_A_VALID_VALUE"
        while [[ ! "$disks" =~ "$choice" ]]; do
            read -p "Press enter to take default value [$default_choice] : " choice
            : ${choice:=$default_choice}
        done
    fi
    setSystemValue "installer.prereq.storageDiskName" "${choice}" "${INSTALLER_YAML}"
    INSTALLER_PREREQ_STORAGEDISKNAME="${choice}"
}

# Check if provided directory or disk has enough free space. Will checked in terms of percentage
# It accepts 3 parameters,
#       data               -   can be a directory or disk name
#       maxWarnThreshold   -   if diskspace crosses this percentage, will warn and continue
#       maxErrorThreshold  -   if diskspace crosses this percentage, will exit with a message
validateDiskSpace() {
    local data="${1:-${JF_PRODUCT_HOME}/var}"
    local maxWarnThreshold=${2:-90}  # warn if disk usage exceeds 90%
    local maxErrorThreshold=${3:-98} # exit with error if disk usage exceeds 98%
    local availableStorage=

    if [ "${SKIP_VALIDATION_DISK_SPACE}" == "true" ]; then
        return
    fi

    if [ -z "${data}" ]; then
        return
    fi

    local usedStorage="$(df -h "${data}" 2>/dev/null | tail -n +2 2>/dev/null | tr -s ' ' 2>/dev/null | tr '%' ' ' 2>/dev/null | cut -d ' ' -f 5 2>/dev/null )"

    if [[ -z "${usedStorage}" || -z "${usedStorage}" || -z "${usedStorage}" ]]; then
        return
    fi

    if [[ ${usedStorage} -ge ${maxErrorThreshold} ]]; then
        let "availableStorage = 100 - ${usedStorage}"
        errorExit "${data} is running with ${availableStorage}% free storage. Free up space or increase volume size and try again. Exiting"
    elif [[ ${usedStorage} -ge ${maxWarnThreshold} ]]; then
        let "availableStorage = 100 - ${usedStorage}"
        warn "Running with ${availableStorage}% free storage. Consider freeing up space or increasing volume size"
    fi
}

validateSystemYaml() {
    local SYSTEM_YAML_PATH=${JF_PRODUCT_HOME}/var/etc/${FILE_NAME_SYSTEM_YAML}
    local SYSTEM_YAML_TEMPLATE_PATH=${JF_PRODUCT_HOME}/app/misc/etc/${FILE_NAME_SYSTEM_YAML_TEMPLATE}
    local VALIDATE_BINARY=${JF_PRODUCT_HOME}/app/bin/diagnostics/diagnosticsUtil
    local status=
    
    if [ ! -f "${VALIDATE_BINARY}" ]; then
        logDebug "Skipping system.yaml validation since diagnosticsUtil binary file was not found in location ${VALIDATE_BINARY}"
        return
    fi

    if [ ! -f "$SYSTEM_YAML_PATH" ]; then
        logDebug "Skipping system.yaml validation since system.yaml file [$SYSTEM_YAML_PATH] was not found"
        return
    fi

    if [ ! -f "$SYSTEM_YAML_TEMPLATE_PATH" ]; then
        logDebug "Skipping system.yaml validation since template file [$SYSTEM_YAML_TEMPLATE_PATH] was not found"
        return
    fi

    # validate system.yaml against system.full-template.yaml
    ${VALIDATE_BINARY} validate yaml $SYSTEM_YAML_PATH $SYSTEM_YAML_TEMPLATE_PATH --uncomment-before-parse; status=$? || true
    if [ "${status}" == "0" ]; then
        logger "System.yaml validation succeeded"
    else
        warn "System.yaml validation failed"
        if [[ "$IGNORE_SYSTEM_YAML_VALIDATION" == "$FLAG_N" ]]; then
            errorExit "Aborting since system.yaml is invalid"
        fi
    fi
}

wrapper_validateSystemYaml() {
    local SYSTEM_YAML_PATH="${1}"
    local SYSTEM_YAML_TEMPLATE_PATH="${2}"
    local VALIDATE_BINARY="${3}"
    local statusCode=

    logDebug "Validating system.yaml"
    if [ ! -f "${VALIDATE_BINARY}" ]; then
        logDebug "Skipping system.yaml validation since diagnosticsUtil binary file was not found in location ${VALIDATE_BINARY}"
        return
    fi

    if [ ! -f "$SYSTEM_YAML_PATH" ]; then
        logDebug "Skipping system.yaml validation since system.yaml file [$SYSTEM_YAML_PATH] was not found"
        return
    fi

    if [ ! -f "$SYSTEM_YAML_TEMPLATE_PATH" ]; then
        logDebug "Skipping system.yaml validation since template file [$SYSTEM_YAML_TEMPLATE_PATH] was not found"
        return
    fi

    # validate system.yaml against system.full-template.yaml
    local lintOutput=$(${VALIDATE_BINARY} validate yaml $SYSTEM_YAML_PATH $SYSTEM_YAML_TEMPLATE_PATH --uncomment-before-parse)
    statusCode=$(${VALIDATE_BINARY} validate yaml $SYSTEM_YAML_PATH $SYSTEM_YAML_TEMPLATE_PATH --uncomment-before-parse >/dev/null; echo $?)
    if [[ "${statusCode}" == "0" ]]; then
        logger "System.yaml validation succeeded"
    else
        ${VALIDATE_BINARY} validate yaml $SYSTEM_YAML_PATH $SYSTEM_YAML_TEMPLATE_PATH --uncomment-before-parse 2>&1 || true
        warn "System.yaml validation failed"
        if [[ "$IGNORE_SYSTEM_YAML_VALIDATION" == "$FLAG_N" ]]; then
            ${VALIDATE_BINARY} validate yaml $SYSTEM_YAML_PATH $SYSTEM_YAML_TEMPLATE_PATH --uncomment-before-parse 2>&1 || true
            errorExit "Aborting since system.yaml is invalid"
        fi
    fi
}

runSystemDiagnosticsChecks() {
    local homeDir=${1}
    local VALIDATE_BINARY="${2}"
    local DIAGNOSTICSYAMLFILEPATH="${3}"
    local statusCode=
    
    if [ ! -f "${VALIDATE_BINARY}" ]; then
        logDebug "Skipping systemDiagnostics checks, since diagnosticsUtil binary file was not found in location ${VALIDATE_BINARY}"
        return
    fi
    if [ ! -f "${DIAGNOSTICSYAMLFILEPATH}" ]; then
        logDebug "Skipping systemDiagnostics checks, since diagnostics.yaml file was not found in location ${DIAGNOSTICSYAMLFILEPATH}"
        return
    fi
    
    logger "Running system diagnostics checks, logs can be found at [$SYSTEM_DIAGNOSTICS_LOG_FILE]"
    bannerSubSection "Start ${PRODUCT_NAME} Diagnostics" >> $SYSTEM_DIAGNOSTICS_LOG_FILE
    echo ""  >> $SYSTEM_DIAGNOSTICS_LOG_FILE
    JF_PRODUCT_HOME=${homeDir} ${VALIDATE_BINARY} run ${DIAGNOSTICSYAMLFILEPATH} >>$SYSTEM_DIAGNOSTICS_LOG_FILE 2>&1
    JF_PRODUCT_HOME=${homeDir} ${VALIDATE_BINARY} run ${DIAGNOSTICSYAMLFILEPATH} 2>&1 >/dev/null
    if [ $? != 0 ]; then
        warn "One or more system diagnostic checks have failed, check [$SYSTEM_DIAGNOSTICS_LOG_FILE] for additional details"
    fi
    echo "" >> $SYSTEM_DIAGNOSTICS_LOG_FILE
    bannerSubSection "END ${PRODUCT_NAME} Diagnostics" >> $SYSTEM_DIAGNOSTICS_LOG_FILE
}

validateJfrogUrl() {
    local productHome="${1}"
    getYamlValue "$SYS_KEY_SHARED_JFROGURL" "${productHome}/var/etc/${FILE_NAME_SYSTEM_YAML}" "false"
    local jfrogUrl=$YAML_VALUE
    jfrogUrl=${jfrogUrl%/}
    ${productHome}/app/bin/diagnostics/diagnosticsUtil jfrog jfrog-url "${jfrogUrl}" 2>&1 || true
    echo ""
}

validateDbconnection() {
    local productHome="${1}"
    local databaseURl=
    local databaseLabel=
    local databaseUsername=
    case "$PRODUCT_NAME" in
        $ARTIFACTORY_LABEL)
            databaseURl="jdbc:postgresql://$(wrapper_getHostIP):5432/artifactory"
            databaseLabel="postgresql"
            databaseUsername="artifactory"
        ;;
        $JFMC_LABEL)
            databaseURl="jdbc:postgresql://$(wrapper_getHostIP):5432/mission_control"
            databaseLabel="postgresql"
            databaseUsername="jfmc"
        ;;
        $INSIGHT_LABEL)
            databaseURl="jdbc:postgresql://$(wrapper_getHostIP):5432/insight"
            databaseLabel="postgresql"
            databaseUsername="insight"
        ;;
        $DISTRIBUTION_LABEL)
            databaseURl="jdbc:postgresql://$(wrapper_getHostIP):5432/distribution"
            databaseLabel="postgresql"
            databaseUsername="distribution"
        ;;
        $XRAY_LABEL)
            databaseURl="postgres://$(wrapper_getHostIP):5432/xraydb?sslmode=disable"
            databaseLabel="postgresql"
            databaseUsername="xray"
        ;;
    esac
    getYamlValue "$SYS_KEY_SHARED_DATABASE_URL" "${productHome}/var/etc/${FILE_NAME_SYSTEM_YAML}" "false"
    local databaseUrl=$YAML_VALUE
    if [[ -z "${databaseUrl}" ]]; then
        ${productHome}/app/bin/diagnostics/diagnosticsUtil jfrog database-status "${databaseURl}" "${databaseLabel}" "${databaseUsername}" "{{shared.database.password}}" 2>&1 || true
    else
        ${productHome}/app/bin/diagnostics/diagnosticsUtil jfrog database-status "{{shared.database.url}}" "{{shared.database.type:${databaseLabel}}}" "{{shared.database.username:${databaseUsername}}}" "{{shared.database.password}}" 2>&1 || true
    fi
    echo ""
}

validationCheckPrerequisites() {
    local RECOMMENDED_MIN_RAM=${RPM_DEB_RECOMMENDED_MIN_RAM:-8388608}                 # 8G Total RAM => 8*1024*1024k=8388608
    local RECOMMENDED_MIN_CPU=${RPM_DEB_RECOMMENDED_MIN_CPU:-6}                       # needs more than 6 CPU Cores

    local osType=$(uname)
    local TOTAL_RAM=
    local FREE_CPU=
    
    if [[ $osType == "Darwin" ]]; then
        TOTAL_RAM="$(sysctl hw.memsize | awk '{print$2}')"
        FREE_CPU="$(sysctl hw.physicalcpu | awk '{print$2}')"
    elif [[ $osType == "Linux" ]]; then
        TOTAL_RAM="$(grep ^MemTotal /proc/meminfo | awk '{print $2}')"
        FREE_CPU="$(grep -c ^processor /proc/cpuinfo)"
    fi
    
    local msg=""

    if [[ ${TOTAL_RAM} -lt ${RECOMMENDED_MIN_RAM} ]]; then
        let "TOTAL_RAM_TO_SHOW = ${TOTAL_RAM} / 1024 / 1024"
        let "RECOMMENDED_MIN_RAM_TO_SHOW = ${RECOMMENDED_MIN_RAM} / 1024 / 1024"
        warn "Running with ${TOTAL_RAM_TO_SHOW}GB Total RAM. Recommended value: ${RECOMMENDED_MIN_RAM_TO_SHOW}GB"
    fi;

    if [ ${FREE_CPU} -lt ${RECOMMENDED_MIN_CPU} ]; then
        warn "Running with ${FREE_CPU} CPU Cores. Recommended value: ${RECOMMENDED_MIN_CPU} Cores"
    fi;
}

validateRegex() {
    local inputString="$1"
    local regexToValidate="$2"

    if [[ "$inputString" =~ $regexToValidate ]]; then
        return 0
    fi
    return 1
}


# Utility method to check if docker and docker-compose are available
# Failure conditions:
## Exits if docker and docker-compose are unavailable
# Parameters: none
# Depends on global: none
# Updates global: none
# Returns: NA

checkDocker () {
    docker --version >&6 2>&1 || errorExit "Unable to run docker"
    docker-compose --version >&6 2>&1 || errorExit "Unable to run docker-compose"
}



wrapper_setSystemValue() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    io_setYQPath

    local keyToUpdate="$1"
    local valueToUpdate="$2"
    
    local targetFile="${INSTALLER_YAML}"
    local overwrite="${3:-$DONT_PROMPT_USE_DEFAULTS}"

    getYamlValue "$keyToUpdate" "${targetFile}" "false"
    # If a value already exists, check if we need to overwrite
    if [ ! -z "${YAML_VALUE}" ] && [ "${overwrite}" == "$FLAG_N" ]; then
        return 0
    fi
    setSystemValue "$keyToUpdate" "$valueToUpdate" "$targetFile"
}

wrapper_hook_setInstallerDefaults() {
    logSilly "Method ${FUNCNAME[0]} (not implemented)"
}

wrapper_setInstallerDefaults() {
    if [ "$DONT_PROMPT_USE_DEFAULTS" == "$FLAG_Y" ];then
        # Using echo "" > file does not work here. yq complains afterwards
        rm "$INSTALLER_YAML"
        touch "${INSTALLER_YAML}"
    fi

    local overwrite="${FLAG_N}"
    wrapper_setSystemValue "$KEY_ADD_TO_CLUSTER" "$FLAG_N" "${overwrite}"
    
    if [[ "${EXTERNAL_DATABASES}" =~ "${DATABASE_ELASTICSEARCH}" ]]; then
        wrapper_setSystemValue "$KEY_STANDALONE_ELASTICSEARCH_INSTALL" "$FLAG_Y" "${overwrite}"
    fi
    wrapper_hook_setInstallerDefaults
    if [[ "${PRODUCT_NAME}" != "${PDN_NODE_LABEL}" ]]; then
        wrapper_setSystemValue "$KEY_HOST_IP" "$(io_getPublicHostIP)" "${overwrite}"
        [[ "${PRODUCT_NAME}" == "${PDN_SERVER_LABEL}" ]] && return
        wrapper_setSystemValue "$KEY_POSTGRES_INSTALL" "$FLAG_Y" "${overwrite}"
    fi
}

wrapper_createInstallerYaml() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"

    INSTALLER_YAML=${INSTALLER_YAML:-"${SCRIPT_HOME}/${FILE_NAME_INSTALLER_YAML}"}

    if [ ! -f "$INSTALLER_YAML" ]; then
        logDebug "Creating file: [${INSTALLER_YAML}]"
        touch "${INSTALLER_YAML}"
    fi
}

wrapper_removeInstallerYaml() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"

    INSTALLER_YAML=${INSTALLER_YAML:-"${SCRIPT_HOME}/${FILE_NAME_INSTALLER_YAML}"}

    if [ -f "$INSTALLER_YAML" ]; then
        logDebug "Removing file: [${INSTALLER_YAML}]"
        rm ${INSTALLER_YAML}
    fi
}

_getRTDataDir() {
    local productHome=
    getYamlValue "$KEY_DATA_DIR_LOCATION" "${INSTALLER_YAML}" "false"
    productHome="$YAML_VALUE"
    if [ -z "${productHome}" ]; then
        return
    elif [ "$WRAPPER_SCRIPT_TYPE" == "$WRAPPER_SCRIPT_TYPE_DOCKER_COMPOSE" ]; then
        productHome="$productHome/${PROJECT_ROOT_FOLDER_RT}/var"
    fi
    echo -n "$productHome"
}

_getXrayDataDir() {
    local productHome=
    getYamlValue "$KEY_DATA_DIR_LOCATION" "${INSTALLER_YAML}" "false"
    productHome="$YAML_VALUE"
    if [ -z "${productHome}" ]; then
        return
    elif [ "$WRAPPER_SCRIPT_TYPE" == "$WRAPPER_SCRIPT_TYPE_DOCKER_COMPOSE" ]; then
        productHome="$productHome/${PROJECT_ROOT_FOLDER_XRAY}/var"
    fi
    echo -n "$productHome"
}

_getDataDir() {
    local productHome=
    getYamlValue "$KEY_DATA_DIR_LOCATION" "${INSTALLER_YAML}" "false"
    productHome="$YAML_VALUE"
    if [ -z "${productHome}" ]; then
        return
    elif [ "$WRAPPER_SCRIPT_TYPE" == "$WRAPPER_SCRIPT_TYPE_DOCKER_COMPOSE" ]; then
        productHome="$productHome/var"
    fi
    echo -n "$productHome"
}

wrapper_standalone_hook_getUserInputs() {
    logSilly "Method ${FUNCNAME[0]} (unimplemented)"
}

wrapper_ha_hook_getUserInputs() {
    logSilly "Method ${FUNCNAME[0]} (unimplemented)"
}

wrapper_hook_getUserInputs() {
    logSilly "Method ${FUNCNAME[0]} (unimplemented)"
}

wrapper_hook_setRedisPassword() {
    logSilly "Method ${FUNCNAME[0]} (unimplemented)"
}

wrapper_hook_askExtraPostgresDetails(){
    logSilly "Method ${FUNCNAME[0]} (unimplemented)"
}

reconfirmPostgresPassword(){
    unset USER_CHOICE
    while [ -z "$USER_CHOICE" ]; do
        echo -n "confirm $PROMPT_DB_PASSWORD: "
        read -s USER_CHOICE
        echo ""
        if [ -z "$USER_CHOICE" ]; then
            warn "Invalid Entry"
        fi
    done
    wrapper_setSystemValue "$SYS_KEY_SHARED_DATABASE_PASSWORD" "$USER_CHOICE" "$FLAG_Y"
    getSystemValue "$SYS_KEY_SHARED_DATABASE_PASSWORD" "NOT_SET" "false" "$INSTALLER_YAML"
    RPM_DEB_POSTGRES_PASSWORD=${YAML_VALUE}
}

wrapper_askPostgresPassword() {
    _updateFromSystemYaml "SHARED_DATABASE_PASSWORD" "KEY_DB_PASSWORD"
    if [ -z "$YAML_VALUE" ]; then
        wrapper_askUser "DB_PASSWORD"
        getSystemValue "$SYS_KEY_SHARED_DATABASE_PASSWORD" "NOT_SET" "false" "$INSTALLER_YAML"
        RPM_DEB_POSTGRES_FIRST_PASSWORD=${YAML_VALUE}
        reconfirmPostgresPassword
        if [[ "${RPM_DEB_POSTGRES_FIRST_PASSWORD}" == "${RPM_DEB_POSTGRES_PASSWORD}" ]]; then
            return
        fi
        warn "Confirmed $PROMPT_DB_PASSWORD doesn't match"
        wrapper_askPostgresPassword
    else
        logger "Found $PROMPT_DB_PASSWORD in system.yaml. Skipping prompt"
    fi
}

reconfirmElasticSearchPassword(){
    unset USER_CHOICE
    while [ -z "$USER_CHOICE" ]; do
        echo -n "confirm $PROMPT_ELASTICSEARCH_PASSWORD: "
        read -s USER_CHOICE
        echo ""
        if [ -z "$USER_CHOICE" ]; then
            warn "Invalid Entry"
        fi
    done
    wrapper_setSystemValue "$SYS_KEY_SHARED_ELASTICSEARCH_PASSWORD" "$USER_CHOICE" "$FLAG_Y"
    getSystemValue "$SYS_KEY_SHARED_ELASTICSEARCH_PASSWORD" "NOT_SET" "false" "$INSTALLER_YAML"
    RPM_DEB_ELASTICSEARCH_PASSWORD=${YAML_VALUE}
}

wrapper_askElasticSearchPassword() {
    _updateFromSystemYaml "SHARED_ELASTICSEARCH_PASSWORD" "KEY_ELASTICSEARCH_PASSWORD"
    if [ -z "$YAML_VALUE" ]; then
        wrapper_askUser "ELASTICSEARCH_PASSWORD"
        getSystemValue "$SYS_KEY_SHARED_ELASTICSEARCH_PASSWORD" "NOT_SET" "false" "$INSTALLER_YAML"
        RPM_DEB_ELASTICSEARCH_FIRST_PASSWORD=${YAML_VALUE}
        reconfirmElasticSearchPassword
        if [[ "${RPM_DEB_ELASTICSEARCH_FIRST_PASSWORD}" == "${RPM_DEB_ELASTICSEARCH_PASSWORD}" ]]; then
            return
        fi
        warn "Confirmed $PROMPT_ELASTICSEARCH_PASSWORD doesn't match"
        wrapper_askElasticSearchPassword
    else
        logger "Found $PROMPT_ELASTICSEARCH_PASSWORD in system.yaml. Skipping prompt"
    fi
}

wrapper_askElasticSearchUsername() {
    _updateFromSystemYaml "SHARED_ELASTICSEARCH_USERNAME" "KEY_ELASTICSEARCH_USERNAME"
    if [ -z "$YAML_VALUE" ]; then
        wrapper_askUser "ELASTICSEARCH_USERNAME"
        getSystemValue "$SYS_KEY_SHARED_ELASTICSEARCH_USERNAME" "NOT_SET" "false" "$INSTALLER_YAML"
        RPM_DEB_ELASTICSEARCH_USERNAME=${YAML_VALUE}
    else
        logger "Found $PROMPT_ELASTICSEARCH_USERNAME in system.yaml. Skipping prompt"
    fi
}

wrapper_getHostIP(){
    if [[ ! -z "${SYS_KEY_SHARED_NODE_IP}" && ! -z "${INSTALLER_YAML}" && -f "${INSTALLER_YAML}" ]]; then
        getSystemValue "$SYS_KEY_SHARED_NODE_IP" "NOT_SET" "false" "$INSTALLER_YAML"
        if [[ ! -z "${YAML_VALUE}" && "${YAML_VALUE}" != "NOT_SET" ]]; then
            echo -n "${YAML_VALUE}"
            return 0;
        fi
    fi

    echo -n "$(io_getPublicHostIP)"
}

getJFrogURLIfAvailable() {
    if [ ! -z "$JF_SHARED_JFROGURL" ]; then
        return
    fi
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    local productHome="$(_getDataDir)"
    if [ -z "${productHome}" ]; then
        logDebug "No key: [$KEY_DATA_DIR_LOCATION] found. Aborting jfrog URL search"
        return
    fi
    
    local systemYaml="${productHome}/etc/${FILE_NAME_SYSTEM_YAML}"
    getYamlValue "$SYS_KEY_SHARED_JFROGURL" "${systemYaml}" "false"
    if [ ! -z "${YAML_VALUE}" ]; then
        logger "$PROMPT_JFROGURL: [$SYS_KEY_SHARED_JFROGURL] found in system.yaml. Skipping prompt"
        JF_SHARED_JFROGURL="$YAML_VALUE"
        return
    fi
    logDebug "JFrog URL not found"
}

getPdnNodePdnServerUrlIfAvailable() {
    if [ ! -z "$JF_PDNNODE_PDNSERVERURL" ]; then
        return
    fi
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    local productHome="$(_getDataDir)"
    if [ -z "${productHome}" ]; then
        logDebug "No key: [$KEY_DATA_DIR_LOCATION] found. Aborting PDN Node pdnServerUrl search"
        return
    fi
    
    local systemYaml="${productHome}/etc/${FILE_NAME_SYSTEM_YAML}"
    getYamlValue "$SYS_KEY_PDNNODE_PDNSERVERURL" "${systemYaml}" "false"
    if [ ! -z "${YAML_VALUE}" ]; then
        logger "$PROMPT_PDNNODE_PDNSERVERURL: [$SYS_KEY_PDNNODE_PDNSERVERURL] found in system.yaml. Skipping prompt"
        JF_PDNNODE_PDNSERVERURL="$YAML_VALUE"
        return
    fi
    logDebug "PDN Node pdnServerUrl not found"
}

getPdnNodeSelfGrpcAddressIfAvailable() {
    if [ ! -z "$JF_PDNNODE_SELFGRPCADDRESS" ]; then
        return
    fi
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    local productHome="$(_getDataDir)"
    if [ -z "${productHome}" ]; then
        logDebug "No key: [$KEY_DATA_DIR_LOCATION] found. Aborting PDN Node SelfGrpcAddress search"
        return
    fi
    
    local systemYaml="${productHome}/etc/${FILE_NAME_SYSTEM_YAML}"
    getYamlValue "$SYS_KEY_PDNNODE_SELFGRPCADDRESS" "${systemYaml}" "false"
    if [ ! -z "${YAML_VALUE}" ]; then
        logger "$PROMPT_PDNNODE_SELFGRPCADDRESS: [$SYS_KEY_PDNNODE_SELFGRPCADDRESS] found in system.yaml. Skipping prompt"
        JF_PDNNODE_SELFGRPCADDRESS="$YAML_VALUE"
        if [ ! -z "${JF_PDNNODE_SELFGRPCADDRESS}" ]; then
            local pdnNodeGrpcPort=$(echo ${JF_PDNNODE_SELFGRPCADDRESS} | awk -F":" '{print $2}')
            [ ! -z "${pdnNodeGrpcPort}" ] && setSystemValue "${SYS_KEY_PDNNODE_PORT}" "${pdnNodeGrpcPort}" "$INSTALLER_YAML"
        fi
        return
    fi
    logDebug "PDN NODE SELFGRPCADDRESS not found"
}

getPdnNodeSelfHttpAddressIfAvailable() {
    if [ ! -z "$JF_PDNNODE_SELFHTTPADDRESS" ]; then
        return
    fi
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    local productHome="$(_getDataDir)"
    if [ -z "${productHome}" ]; then
        logDebug "No key: [$KEY_DATA_DIR_LOCATION] found. Aborting PDN Node SelfHttpAddress search"
        return
    fi
    
    local systemYaml="${productHome}/etc/${FILE_NAME_SYSTEM_YAML}"
    getYamlValue "$SYS_KEY_PDNNODE_SELFHTTPADDRESS" "${systemYaml}" "false"
    if [ ! -z "${YAML_VALUE}" ]; then
        logger "$PROMPT_PDNNODE_SELFHTTPADDRESS: [$SYS_KEY_PDNNODE_SELFHTTPADDRESS] found in system.yaml. Skipping prompt"
        JF_PDNNODE_SELFHTTPADDRESS="$YAML_VALUE"
        if [ ! -z "${JF_PDNNODE_SELFHTTPADDRESS}" ]; then
            local pdnNodeHttpPort=$(echo ${JF_PDNNODE_SELFHTTPADDRESS} | awk -F":" '{print $2}')
            [ ! -z "${pdnNodeHttpPort}" ] && setSystemValue "${SYS_KEY_PDNNODE_HTTPPORT}" "${pdnNodeHttpPort}" "$INSTALLER_YAML"
        fi
        return
    fi
    logDebug "PDN NODE SELFHTTPADDRESS not found"
}

getPdnServerSelfAddressIfAvailable() {
    if [ ! -z "$JF_PDNSERVER_SELFADDRESS" ]; then
        return
    fi
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    local productHome="$(_getDataDir)"
    if [ -z "${productHome}" ]; then
        logDebug "No key: [$KEY_DATA_DIR_LOCATION] found. Aborting PdnServer SelfAddress search"
        return
    fi
    
    local systemYaml="${productHome}/etc/${FILE_NAME_SYSTEM_YAML}"
    getYamlValue "$SYS_KEY_PDNSERVER_SELFADDRESS" "${systemYaml}" "false"
    if [ ! -z "${YAML_VALUE}" ]; then
        logger "$PROMPT_PDNSERVER_SELFADDRESS: [$SYS_KEY_PDNSERVER_SELFADDRESS] found in system.yaml. Skipping prompt"
        JF_PDNSERVER_SELFADDRESS="$YAML_VALUE"
        return
    fi
    logDebug "PDN Server SELFADDRESS not found"
}

wrapper_checkXrayArtPairing() {
    if [[ "${PRODUCT_NAME}" != "Xray" ]]; then
        return 0
    fi

    local isDisconnected=
    getSystemValue "$KEY_XRAY_ARTIFACTORY_PAIRING" "NOT_SET" "false" "$INSTALLER_YAML"
    isDisconnected=${YAML_VALUE}

    if [[ "${isDisconnected}" == "NOT_SET" || "${isDisconnected}" == "${FLAG_N}" ]]; then
        wrapper_askUser "XRAY_ARTIFACTORY_PAIRING"
        getSystemValue "$KEY_XRAY_ARTIFACTORY_PAIRING" "NOT_SET" "false" "$INSTALLER_YAML"
        isDisconnected=${YAML_VALUE}
    fi

    if [[ "${isDisconnected}" == "${FLAG_N}" ]]; then
        exit 1
    fi
}

_getDatabaseType() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    
    DATABASE_TYPE=
    local productHome=
    if [[ "${TRIAL_FLOW}" == "${FLAG_Y}" ]]; then
        productHome="$(_getRTDataDir)"
    else
        productHome="$(_getDataDir)"
    fi
    if [ -z "${productHome}" ]; then
        logDebug "No key: [$KEY_DATA_DIR_LOCATION] found. Aborting database type search"
        return
    fi
    
    local systemYaml="${productHome}/etc/${FILE_NAME_SYSTEM_YAML}"

    # Get the db type configured
    getYamlValue "$SYS_KEY_SHARED_DATABASE_TYPE" "$systemYaml" "false"
    DATABASE_TYPE="$YAML_VALUE"
}

_getJoinKeyIfAvailable() {
    if [ ! -z "$JF_SHARED_SECURITY_JOINKEY" ]; then
        return
    fi
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    local productHome="$(_getDataDir)"
    if [ -z "${productHome}" ]; then
        logDebug "No key: [$KEY_DATA_DIR_LOCATION] found. Aborting join key search"
        return
    fi
    
    # First check if a file exists
    local joinKeyFile="${productHome}/etc/security/${FILE_NAME_JOIN_KEY}"
    if [ -f "$joinKeyFile" ];then
        logger "$PROMPT_JOIN_KEY file: [$joinKeyFile] found. Skipping prompt"
        JF_SHARED_SECURITY_JOINKEY=$(cat "$joinKeyFile")
        return
    fi
    
    local systemYaml="${productHome}/etc/${FILE_NAME_SYSTEM_YAML}"
    if [ ! -f "$systemYaml" ]; then
        return
    fi
    
    getYamlValue "$SYS_KEY_SHARED_SECURITY_JOINKEY" "${systemYaml}" "false"
    if [ ! -z "${YAML_VALUE}" ]; then
        logger "$PROMPT_JOIN_KEY: [$SYS_KEY_SHARED_SECURITY_JOINKEY] found in system.yaml. Skipping prompt"
        JF_SHARED_SECURITY_JOINKEY="$YAML_VALUE"
        return
    fi
    logDebug "Join key not found"
}

_getHostIpIfAvailable() {
    if [ ! -z "$JF_SHARED_NODE_IP" ]; then
        return
    fi
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    local productHome="$(_getDataDir)"
    if [ -z "${productHome}" ]; then
        logDebug "No key: [$KEY_DATA_DIR_LOCATION] found. Aborting Host IP search"
        return
    fi
    
    local systemYaml="${productHome}/etc/${FILE_NAME_SYSTEM_YAML}"
    if [ ! -f "$systemYaml" ]; then
        return
    fi
    
    getYamlValue "$SYS_KEY_SHARED_NODE_IP" "${systemYaml}" "false"
    if [ ! -z "${YAML_VALUE}" ]; then
        logger "$PROMPT_HOST_IP: [$SYS_KEY_SHARED_NODE_IP] found in system.yaml. Skipping prompt"
        JF_SHARED_NODE_IP="$YAML_VALUE"
        return
    fi
    logDebug "Host IP not found"
}

_getMasterKeyIfAvailable() {
    if [ ! -z "$JF_SHARED_SECURITY_MASTERKEY" ]; then
        return
    fi
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    local productHome="$(_getDataDir)"
    if [ -z "${productHome}" ]; then
        logDebug "No key: [$KEY_DATA_DIR_LOCATION] found. Aborting join key search"
        return
    fi
    
    # First check if a file exists
    local masterKeyFile="${productHome}/etc/security/${FILE_NAME_MASTER_KEY}"
    if [ -f "$masterKeyFile" ];then
        logger "$PROMPT_CLUSTER_MASTER_KEY file: [$masterKeyFile] found. Skipping prompt"
        JF_SHARED_SECURITY_MASTERKEY=$(cat "$masterKeyFile")
        return
    fi
    
    local systemYaml="${productHome}/etc/${FILE_NAME_SYSTEM_YAML}"
    if [ ! -f "$systemYaml" ]; then
        return
    fi
    
    getYamlValue "$SYS_KEY_SHARED_SECURITY_MASTERKEY" "${systemYaml}" "false"
    if [ ! -z "${YAML_VALUE}" ]; then
        logger "$PROMPT_CLUSTER_MASTER_KEY: [$SYS_KEY_SHARED_SECURITY_MASTERKEY] found in system.yaml [$systemYaml]. Skipping prompt"
        JF_SHARED_SECURITY_MASTERKEY="$YAML_VALUE"
        return
    fi
    logDebug "Master key not found"
}

_askForMandatoryInputs() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    local mandatoryFields=($MANDATORY_FIELDS)
    for key in "${mandatoryFields[@]}"; do
        case ${key} in
            JF_SHARED_JFROGURL)
                getJFrogURLIfAvailable
                if [ ! -z "$JF_SHARED_JFROGURL" ]; then
                    wrapper_setSystemValue "$KEY_JFROGURL" "$JF_SHARED_JFROGURL"
                else 
                    wrapper_askUser "JFROGURL"
                    checkJfrogUrlSystemHealthCheck
                fi
            ;;
            JF_SHARED_SECURITY_JOINKEY)
                _getJoinKeyIfAvailable
                if [ ! -z "$JF_SHARED_SECURITY_JOINKEY" ]; then
                    wrapper_setSystemValue "$KEY_JOIN_KEY" "$JF_SHARED_SECURITY_JOINKEY"
                else
                    wrapper_askUser "JOIN_KEY"
                fi
            ;;
            JF_PDNNODE_PDNSERVERURL)
                getPdnNodePdnServerUrlIfAvailable
                if [ ! -z "$JF_PDNNODE_PDNSERVERURL" ]; then
                    wrapper_setSystemValue "$KEY_PDNNODE_PDNSERVERURL" "$JF_PDNNODE_PDNSERVERURL"
                else
                    wrapper_askUser "PDNNODE_PDNSERVERURL"
                fi
            ;;
            JF_PDNNODE_SELFGRPCADDRESS)
                getPdnNodeSelfGrpcAddressIfAvailable
                if [ ! -z "$JF_PDNNODE_SELFGRPCADDRESS" ]; then
                    wrapper_setSystemValue "$KEY_PDNNODE_SELFGRPCADDRESS" "$JF_PDNNODE_SELFGRPCADDRESS"
                else
                    wrapper_askUser "PDNNODE_SELFGRPCADDRESS"
                fi
            ;;
            JF_PDNNODE_SELFHTTPADDRESS)
                getPdnNodeSelfHttpAddressIfAvailable
                if [ ! -z "$JF_PDNNODE_SELFHTTPADDRESS" ]; then
                    wrapper_setSystemValue "$KEY_PDNNODE_SELFHTTPADDRESS" "$JF_PDNNODE_SELFHTTPADDRESS"
                else
                    wrapper_askUser "PDNNODE_SELFHTTPADDRESS"
                fi
            ;;
            JF_PDNSERVER_SELFADDRESS)
                getPdnServerSelfAddressIfAvailable
                if [ ! -z "$JF_PDNSERVER_SELFADDRESS" ]; then
                    wrapper_setSystemValue "$KEY_PDNSERVER_SELFADDRESS" "$JF_PDNSERVER_SELFADDRESS"
                else
                    wrapper_askUser "PDNSERVER_SELFADDRESS"
                fi
            ;;
        esac
    done
}

_updateFromSystemYaml() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"  
    local keyToFetch=$1
    local installerYamlKey=$2
    local customYaml="$3"
    unset YAML_VALUE

    if [ ! -f "$INSTALLER_YAML" ]; then
        logDebug "No installer yaml found. Skipping step"
        return
    fi

    local productHome="$(_getDataDir)"
    if [ -z "${productHome}" ]; then
        logDebug "No key: [$KEY_DATA_DIR_LOCATION] found. Aborting jfrog URL search"
        return
    fi

    local systemYaml=${customYaml:-"${productHome}/etc/${FILE_NAME_SYSTEM_YAML}"}
    if [ ! -f "$systemYaml" ]; then
        return
    fi

    eval sysYamlKey=\${SYS_KEY_${keyToFetch}}
    if [ -z "$installerYamlKey" ]; then
        eval installerYamlKey=\${KEY_${keyToFetch}}
    fi
    eval envVariable=\${JF_${keyToFetch}}

    getYamlValue "$sysYamlKey" "$systemYaml" "false"
    if [ ! -z "$YAML_VALUE" ]; then
        local tempValue="$YAML_VALUE"
        logDebug "Overiding installerYamlKey [$installerYamlKey] with value of sysYamlKey [$sysYamlKey]: [$YAML_VALUE]"
        wrapper_setSystemValue "${installerYamlKey}" "$YAML_VALUE" "$FLAG_Y"
        YAML_VALUE="$tempValue"
        return
    fi
    logDebug "No value found in system.yaml for [$keyToFetch]. sysYamlKey [$sysYamlKey]. Not overriding"
    unset YAML_VALUE
}

# This can be used to decide if a prompt needs to be displayed
# This will return 0 if provided key is already set in source yaml
_skipPrompt(){
    local srcKey="$1"
    local targetKey="$2"
    local srcYaml="$3"

    if [[ -z "${srcKey}" || -z "${srcYaml}" ]]; then
        return 1;
    fi

    _updateFromSystemYaml "$srcKey" "$targetKey" "${srcYaml}"
    if [ ! -z "$YAML_VALUE" ]; then
        logger "Found flag ${srcKey} as [${YAML_VALUE}]. Skipping prompt"
        return 0;
    else
        return 1;
    fi
}

_setDatabaseDriver(){
    local databaseType="$1"
    local databaseDriver=""
    
    if [ -z "${databaseType}" ]; then
        getSystemValue "$KEY_DATABASE_TYPE" "NOT_SET" "false" "$INSTALLER_YAML"
        [ "${YAML_VALUE}" != "NOT_SET" ] && databaseType="${YAML_VALUE}" || true
    fi

    databaseDriver=$(getDatabaseDriver "${databaseType}")
    if [ ! -z "${databaseDriver}" ]; then
        setSystemValue "${SYS_KEY_SHARED_DATABASE_DRIVER}" "$databaseDriver" "$INSTALLER_YAML"
    fi
}

_askExternalDBInputs(){
    _updateFromSystemYaml "SHARED_DATABASE_URL" "$KEY_DATABASE_URL"
    if [ ! -z "$YAML_VALUE" ]; then
        logger "Found URL [$YAML_VALUE] in system.yaml. Skipping prompt"
        return 0
    fi

    if [[ "$FLAG_MULTIPLE_DB_SUPPORT" == "$FLAG_Y" ]]; then
        _updateFromSystemYaml "SHARED_DATABASE_TYPE" "KEY_DATABASE_TYPE"
        local databaseType="$YAML_VALUE"
        [ -z "$databaseType" ] && wrapper_askUser "DATABASE_TYPE" || logger "Found $PROMPT_DATABASE_TYPE in system.yaml. Skipping prompt"
        
        if [ -z "$databaseType" ]; then
            getSystemValue "$KEY_DATABASE_TYPE" "NOT_SET" "false" "$INSTALLER_YAML"
            [ ! -z "$YAML_VALUE" ] && databaseType="${YAML_VALUE}" || true
        fi
        
        if [ "${databaseType}" == "${SYS_KEY_SHARED_DATABASE_TYPE_VALUE_DERBY}" ]; then
            logger "Database type set as [${databaseType}]. Skipping database related prompts"
            return 0
        fi

        _setDatabaseDriver "${databaseType}"
    else
        setSystemValue "${SYS_KEY_SHARED_DATABASE_TYPE}" "$SYS_KEY_SHARED_DATABASE_TYPE_VALUE_POSTGRES" "$INSTALLER_YAML"
        _setDatabaseDriver "${SYS_KEY_SHARED_DATABASE_TYPE_VALUE_POSTGRES}"
    fi

    wrapper_askUser "DATABASE_URL"
    if [ "$FLAG_MULTIPLE_PG_SCHEME" == "$FLAG_Y" ]; then
        logBold "$MESSAGE_MULTIPLE_PG_SCHEME"
        _updateFromSystemYaml "SHARED_DATABASE_PASSWORD" "KEY_DATABASE_PASSWORD"
        [ -z "$YAML_VALUE" ] && wrapper_askUser "DATABASE_PASSWORD" || logger "Found $PROMPT_DATABASE_PASSWORD in system.yaml. Skipping prompt"
    else
        _updateFromSystemYaml "SHARED_DATABASE_USERNAME" "KEY_DATABASE_USERNAME"
        [ -z "$YAML_VALUE" ] && wrapper_askUser "DATABASE_USERNAME" || logger "Found $PROMPT_DATABASE_USERNAME in system.yaml. Skipping prompt"
        _updateFromSystemYaml "SHARED_DATABASE_PASSWORD" "KEY_DATABASE_PASSWORD"
        [ -z "$YAML_VALUE" ] && wrapper_askUser "DATABASE_PASSWORD" || logger "Found $PROMPT_DATABASE_PASSWORD in system.yaml. Skipping prompt"
    fi
    
    if [[ "${FLAG_MULTIPLE_DB_SUPPORT}" == "${FLAG_Y}" ]]; then
        getSystemValue "$KEY_DATABASE_TYPE" "NOT_SET" "false" "$INSTALLER_YAML"
        [ ! -z "$YAML_VALUE" ] && databaseType="${YAML_VALUE}" || true
        if [ "${databaseType}" == "${SYS_KEY_SHARED_DATABASE_TYPE_VALUE_POSTGRES}" ]; then
            validateDbConnectionDetails
        fi
    else
        validateDbConnectionDetails
    fi
}

validateDbConnectionDetails(){
    local productHome="$(_getDataDir)"
    local systemYaml="${productHome}/etc/${FILE_NAME_SYSTEM_YAML}"
    getYamlValue "$SYS_KEY_SHARED_DATABASE_URL" "${INSTALLER_YAML}" "false"
    local databaseUrl=$YAML_VALUE
    getYamlValue "$SYS_KEY_SHARED_DATABASE_USERNAME" "${INSTALLER_YAML}" "false"
    local dbUsername=$YAML_VALUE
    getYamlValue "$SYS_KEY_SHARED_DATABASE_PASSWORD" "${INSTALLER_YAML}" "false"
    local dbPassword=$YAML_VALUE
    
    if [[ "${PRODUCT_NAME}" == "$JFMC_LABEL" ]]; then
        dbUsername="jfmc"
    fi
    if [[ "${PRODUCT_NAME}" == "$INSIGHT_LABEL" ]]; then
        dbUsername="insight"
    fi

    if [[ "$WRAPPER_SCRIPT_TYPE" == "$WRAPPER_SCRIPT_TYPE_DOCKER_COMPOSE" ]]; then
        if [[ "${PRODUCT_NAME}" == "$ARTIFACTORY_LABEL" ]]; then
            if [[ "${OS_TYPE}" == "Darwin" ]]; then
                [[ -f "${COMPOSE_HOME}/bin/diagnostics/diagnosticsUtil_darwin" ]] && ${COMPOSE_HOME}/bin/diagnostics/diagnosticsUtil_darwin jfrog database-status "${databaseUrl}" "${SYS_KEY_SHARED_DATABASE_TYPE_VALUE_POSTGRES}" "${dbUsername}" "${dbPassword}" 2>&1 || true
            else
                [[ -f "${COMPOSE_HOME}/bin/diagnostics/diagnosticsUtil_linux" ]] && ${COMPOSE_HOME}/bin/diagnostics/diagnosticsUtil_linux jfrog database-status "${databaseUrl}" "${SYS_KEY_SHARED_DATABASE_TYPE_VALUE_POSTGRES}" "${dbUsername}" "${dbPassword}" 2>&1 || true
            fi
        else
            [[ -f "${COMPOSE_HOME}/bin/diagnostics/diagnosticsUtil" ]] && ${COMPOSE_HOME}/bin/diagnostics/diagnosticsUtil jfrog database-status "${databaseUrl}" "${SYS_KEY_SHARED_DATABASE_TYPE_VALUE_POSTGRES}" "${dbUsername}" "${dbPassword}" 2>&1 || true
        fi
    else
        [[ -f "${SCRIPT_HOME}/diagnostics/diagnosticsUtil" ]] && ${SCRIPT_HOME}/diagnostics/diagnosticsUtil jfrog database-status "${databaseUrl}" "${SYS_KEY_SHARED_DATABASE_TYPE_VALUE_POSTGRES}" "${dbUsername}" "${dbPassword}" 2>&1 || true
    fi
    if [[ $? != 0 ]]; then
        warn "Could not access $POSTGRES_LABEL with provided details, provide correct database connection details in ${systemYaml}"
    fi  
}


_askPostgresDetails() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    local isPostgresExternalised="$FLAG_Y"
    local productHome="$(_getDataDir)"
    local systemYaml="${productHome}/etc/${FILE_NAME_SYSTEM_YAML}"

    if [[ ! -z "${SKIP_POSTGRES_SETUP}" && "${SKIP_POSTGRES_SETUP}" == "${FLAG_Y}" ]]; then
        return 0;
    fi
    
    #For Backward compatibility
    JF_INSTALL_POSTGRES=${JF_INSTALL_POSTGRES:-"$INSTALL_POSTGRES"}
    if [ "$JF_NODE_TYPE" == "$NODE_TYPE_STANDALONE" ]; then
        if [ ! -z "$JF_INSTALL_POSTGRES" ]; then
            JF_INSTALL_POSTGRES="$(echo "$JF_INSTALL_POSTGRES" | tr '[:upper:]' '[:lower:]')"
            logger "Found flag INSTALL_POSTGRES. Skipping prompt"
            wrapper_setSystemValue "$KEY_POSTGRES_INSTALL" "$JF_INSTALL_POSTGRES" "${FLAG_Y}"
        else
            _skipPrompt "POSTGRES_INSTALL" "" "${INSTALLER_STATE_YAML_FILE}" \
                || wrapper_askUser "POSTGRES_INSTALL"
        fi
        getYamlValue "$KEY_POSTGRES_INSTALL" "$INSTALLER_YAML" "false"
        isPostgresExternalised=$([ "$YAML_VALUE" == "$FLAG_Y" ] && echo -n "$FLAG_N" || echo -n "$FLAG_Y")
        getYamlValue "$SYS_KEY_SHARED_DATABASE_URL" "${systemYaml}" "false"
        isDataBaseUrlExist="${YAML_VALUE}"
        if [[ "$isPostgresExternalised" == "$FLAG_N" && -z "${isDataBaseUrlExist}" ]]; then
            setSystemValue "${SYS_KEY_SHARED_DATABASE_TYPE}" "$SYS_KEY_SHARED_DATABASE_TYPE_VALUE_POSTGRES" "$INSTALLER_YAML"
            _setDatabaseDriver "${SYS_KEY_SHARED_DATABASE_TYPE_VALUE_POSTGRES}"
            wrapper_askPostgresPassword
            wrapper_hook_askExtraPostgresDetails
        fi
    fi

    if [ "$isPostgresExternalised" == "$FLAG_Y" ]; then
        _askExternalDBInputs
    fi
}

wrapper_setPostgresVersion() {
    if [[ "${PRODUCT_NAME}" == "${PDN_NODE_LABEL}" ]]; then
        return
    fi
    local productHome=
    if [[ "${TRIAL_FLOW}" == "${FLAG_Y}" ]]; then
        productHome="$(_getRTDataDir)"
    else
        productHome="$(_getDataDir)"
    fi
    local systemYaml="${productHome}/etc/${FILE_NAME_SYSTEM_YAML}"
    local postgresVer=${JFROG_POSTGRES_13_9X_VERSION}
    if [[ "${TRIAL_FLOW}" == "${FLAG_Y}" ]]; then
        _updateFromSystemYaml "POSTGRES_VERSION" "" "${INSTALLER_STATE_YAML_FILE_RT}"
    else
        _updateFromSystemYaml "POSTGRES_VERSION" "" "${INSTALLER_STATE_YAML_FILE}"
    fi
    local installedPostgresVer=${YAML_VALUE}
    
    if [[ "${PRODUCT_NAME}" == "$ARTIFACTORY_LABEL" ]]; then
        _getDatabaseType
        if [[ ! -z "$DATABASE_TYPE" && "$DATABASE_TYPE" != "$SYS_KEY_SHARED_DATABASE_TYPE_VALUE_POSTGRES" ]]; then
            return
        fi
    fi
    if [[ -z "${installedPostgresVer}" || "${installedPostgresVer}" == "NOT_SET" ]]; then
        getYamlValue "$SYS_KEY_SHARED_DATABASE_URL" "${systemYaml}" "false"
        local isDataBaseUrlExist="${YAML_VALUE}"
        getYamlValue "$KEY_POSTGRES_INSTALL" "$INSTALLER_YAML" "false"
        local isPostgresExternalised=$([ "$YAML_VALUE" == "$FLAG_Y" ] && echo -n "$FLAG_N" || echo -n "$FLAG_Y")
        if [ "$isPostgresExternalised" == "$FLAG_Y" ]; then 
            return
        fi

        if [ "$isPostgresExternalised" == "$FLAG_N" ] && [ ! -z "${isDataBaseUrlExist}" ]; then
            postgresVer=${JFROG_POSTGRES_9X_VERSION}
        fi
        wrapper_setSystemValue "$KEY_POSTGRES_VERSION" "${postgresVer}" "${overwrite}"
    fi
    
    if [ "$isPostgresExternalised" == "$FLAG_N" ] && [ ! -z "${isDataBaseUrlExist}" ]; then
        case "$installedPostgresVer" in
            $JFROG_POSTGRES_9X_VERSION)
                postgresVer=${JFROG_POSTGRES_9X_VERSION}
            ;;
            $JFROG_POSTGRES_10X_VERSION)
                postgresVer=${JFROG_POSTGRES_10X_VERSION}
            ;;
            $JFROG_POSTGRES_12_3X_VERSION)
                postgresVer=${JFROG_POSTGRES_12_3X_VERSION}
            ;;
            $JFROG_POSTGRES_12_5X_VERSION)
                postgresVer=${JFROG_POSTGRES_12_5X_VERSION}
            ;;
            $JFROG_POSTGRES_13_2X_VERSION)
                postgresVer=${JFROG_POSTGRES_13_2X_VERSION}
            ;;
            $JFROG_POSTGRES_13_4X_VERSION)
                postgresVer=${JFROG_POSTGRES_13_4X_VERSION}
            ;;
        esac
        wrapper_setSystemValue "$KEY_POSTGRES_VERSION" "${postgresVer}" "${overwrite}"
    fi
}

_setRedisPassword() {
     _updateFromSystemYaml "SHARED_REDIS_PASSWORD" "KEY_REDIS_PASSWORD"
    if [ -z "$YAML_VALUE" ]; then
        local randomPassword="$(randomPasswordGenerator "${DEFAULT_REDIS_PASSWORD}")"
        wrapper_setSystemValue "$SYS_KEY_SHARED_REDIS_PASSWORD" "${randomPassword}" "${overwrite}"
        getSystemValue "$SYS_KEY_SHARED_REDIS_PASSWORD" "NOT_SET" "false" "$INSTALLER_YAML"
        REDIS_PASSWORD=${YAML_VALUE}
        [ "${REDIS_PASSWORD}" == "NOT_SET" ] && REDIS_PASSWORD="${randomPassword}" || true
    else
        logger "Found ${REDIS_LABEL} password in system.yaml"
    fi
}

_setRabbitMqPassword() {
    local productHome=
     _updateFromSystemYaml "SHARED_RABBITMQ_PASSWORD" "KEY_RABBITMQ_PASSWORD"
    if [ -z "$YAML_VALUE" ]; then
        if [[ "${TRIAL_FLOW}" == "${FLAG_Y}" ]]; then
            productHome="$(_getXrayDataDir)"
        else
            productHome="$(_getDataDir)"
        fi
        local cookieValue=
        local defaultRabbitmqPassword=JFXR_RABBITMQ_COOKIE

        if [[ "${TRIAL_FLOW}" == "${FLAG_Y}" ]]; then
            cookieFile="${JF_PRODUCT_HOME_XRAY}/.erlang.cookie"
        else
            cookieFile="${JF_PRODUCT_HOME}/.erlang.cookie"
        fi
        if [[ "$WRAPPER_SCRIPT_TYPE" == "$WRAPPER_SCRIPT_TYPE_DOCKER_COMPOSE" ]]; then
            cookieFile="${productHome}/data/rabbitmq/.erlang.cookie"
        fi
        [ -f "${cookieFile}" ] && cookieValue=$(cat ${cookieFile})
        if [ ! -z "${cookieValue}" ]; then
            defaultRabbitmqPassword="${cookieValue}"
        fi
        wrapper_setSystemValue "$SYS_KEY_SHARED_RABBITMQ_PASSWORD" "${defaultRabbitmqPassword}" "${overwrite}"
        getSystemValue "$SYS_KEY_SHARED_RABBITMQ_PASSWORD" "NOT_SET" "false" "$INSTALLER_YAML"
        RABBITMQ_PASSWORD=${YAML_VALUE}
        [ "${RABBITMQ_PASSWORD}" == "NOT_SET" ] && RABBITMQ_PASSWORD="${defaultRabbitmqPassword}" || true
    else
        logger "Found ${RABBITMQ_LABEL} password in system.yaml"
    fi
}

wrapper_hook_setRabbitmqPassword() {
    if [[ "${CLUSTER_DATABASES}" != "${DATABASE_RABBITMQ}" ]]; then
        return 0
    fi
    local rabbitMqConfFile=
    local setRabbitmqPass="${FLAG_Y}"
    local productHome=
    if [[ "${TRIAL_FLOW}" == "${FLAG_Y}" ]]; then
        productHome="$(_getXrayDataDir)"
    else
        productHome="$(_getDataDir)"
    fi
    local systemYaml="${productHome}/etc/${FILE_NAME_SYSTEM_YAML}"

    if [[ "${TRIAL_FLOW}" == "${FLAG_Y}" ]]; then
        rabbitMqConfFile="${JF_PRODUCT_HOME_XRAY}/app/bin/rabbitmq/rabbitmq.conf"
    else
        rabbitMqConfFile="${JF_PRODUCT_HOME}/app/bin/rabbitmq/rabbitmq.conf"
    fi

    if [[ "$WRAPPER_SCRIPT_TYPE" == "$WRAPPER_SCRIPT_TYPE_DOCKER_COMPOSE" ]]; then
        rabbitMqConfFile="${productHome}/../app/third-party/rabbitmq/rabbitmq.conf"
    fi
    if [[ -f "${rabbitMqConfFile}" ]]; then
        grep "^default_pass.*" ${rabbitMqConfFile} > /dev/null 2>&1
        [ $? -eq 0 ] && setRabbitmqPass="${FLAG_N}" || setRabbitmqPass="${FLAG_Y}"
    fi
    if [[ "${setRabbitmqPass}" == ${FLAG_Y} ]]; then
        getSystemValue "${SYS_KEY_SHARED_RABBITMQ_URL}" "NOT_SET" "false" "${systemYaml}"
        RABBITMQ_URL=${YAML_VALUE}
        getSystemValue "${SYS_KEY_SHARED_RABBITMQ_PASSWORD}" "NOT_SET" "false" "${systemYaml}"
        RABBITMQ_PASSWORD_YAML_VALUE=${YAML_VALUE}
        if [[ "${RABBITMQ_URL}" == "NOT_SET" && "${RABBITMQ_PASSWORD_YAML_VALUE}" == "NOT_SET" ]]; then
            _setRabbitMqPassword
            ADDPASS_TO_RABBITMQ_CONF=${FLAG_Y}
        fi
    else
        #read password from rabbitmq.conf and overwrite to installer.yaml
        local rabbitmqPassValue=$(grep "default_pass*" "${rabbitMqConfFile}")
        rabbitmqPassValue=$(echo "${rabbitmqPassValue}" | awk -F"=" '{print $2}')
        rabbitmqPassValue=$(io_trim "${rabbitmqPassValue}")
        wrapper_setSystemValue "${SYS_KEY_SHARED_RABBITMQ_PASSWORD}" "${rabbitmqPassValue}" "${INSTALLER_YAML}" "${FLAG_Y}"
    fi
    unset YAML_VALUE
}

_removeEmptyLines(){
    SEDOPTION="-i ''"
    if [[ $(uname) != "Darwin" ]]; then
        SEDOPTION="-i"
    fi
    sed $SEDOPTION -n -e '/^*$/d' -e '/^[[:space:]]*$/d' -e '/^\s*$/d' -e '/^$/d' -e '/^\s*$/!p' "$1"
}

_transformRabbitMqPasswordToConfFile() {
    local rabbitMqConfFile=
    local productHome=
    if [[ "${TRIAL_FLOW}" == "${FLAG_Y}" ]]; then
        productHome="$(_getXrayDataDir)"
    else
        productHome="$(_getDataDir)"
    fi
    if [[ "${ADDPASS_TO_RABBITMQ_CONF}" == "${FLAG_N}" ]]; then
        return
    fi
    getSystemValue "${SYS_KEY_SHARED_RABBITMQ_PASSWORD}" "NOT_SET" "false" "${INSTALLER_YAML}"
    local rabbitmqPassword="${YAML_VALUE}"
    if [ "${rabbitmqPassword}" == "NOT_SET" ]; then
        return
    fi
    if [[ "${TRIAL_FLOW}" == "${FLAG_Y}" ]]; then
        rabbitMqConfFile="${JF_PRODUCT_HOME_XRAY}/app/bin/rabbitmq/rabbitmq.conf"
    else
        rabbitMqConfFile="${JF_PRODUCT_HOME}/app/bin/rabbitmq/rabbitmq.conf"
    fi
    if [[ "$WRAPPER_SCRIPT_TYPE" == "$WRAPPER_SCRIPT_TYPE_DOCKER_COMPOSE" ]]; then
        rabbitMqConfFile="${productHome}/../app/third-party/rabbitmq/rabbitmq.conf"
    fi
    addOrReplaceProperty "default_user" "guest" "${rabbitMqConfFile}" " = "
    addOrReplaceProperty "default_pass" "${rabbitmqPassword}" "${rabbitMqConfFile}" " = "

    _removeEmptyLines "$rabbitMqConfFile"
}

_askElasticSearchDetails() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    local isElasticSearchExternalised="$FLAG_N"
    #For Backward compatibility
    JF_INSTALL_ELASTICSEARCH=${JF_INSTALL_ELASTICSEARCH:-"$INSTALL_ELASTICSEARCH"}
    if [ ! -z "$JF_INSTALL_ELASTICSEARCH" ]; then
        JF_INSTALL_ELASTICSEARCH="$(echo "$JF_INSTALL_ELASTICSEARCH" | tr '[:upper:]' '[:lower:]')"
        wrapper_setSystemValue "$KEY_STANDALONE_ELASTICSEARCH_INSTALL" "$JF_INSTALL_ELASTICSEARCH" "${FLAG_Y}"
    else
        _skipPrompt "STANDALONE_ELASTICSEARCH_INSTALL" "" "${INSTALLER_STATE_YAML_FILE}" \
            || wrapper_askUser "STANDALONE_ELASTICSEARCH_INSTALL"
    fi
    getYamlValue "$KEY_STANDALONE_ELASTICSEARCH_INSTALL" "$INSTALLER_YAML" "false"
    isElasticSearchExternalised=$([ "$YAML_VALUE" == "$FLAG_Y" ] && echo -n "$FLAG_N" || echo -n "$FLAG_Y")

    if [ "$isElasticSearchExternalised" == "$FLAG_Y" ]; then
        _updateFromSystemYaml "SHARED_ELASTICSEARCH_URL" "KEY_ELASTICSEARCH_URL"
        [ -z "$YAML_VALUE" ] && wrapper_askUser "ELASTICSEARCH_URL" || logger "Found $PROMPT_ELASTICSEARCH_URL in system.yaml. Skipping prompt"
        _updateFromSystemYaml "SHARED_ELASTICSEARCH_USERNAME" "KEY_ELASTICSEARCH_USERNAME"
        [ -z "$YAML_VALUE" ] && wrapper_askUser "ELASTICSEARCH_USERNAME" || logger "Found $PROMPT_ELASTICSEARCH_USERNAME in system.yaml. Skipping prompt"
        _updateFromSystemYaml "SHARED_ELASTICSEARCH_PASSWORD" "KEY_ELASTICSEARCH_PASSWORD"
        [ -z "$YAML_VALUE" ] && wrapper_askUser "ELASTICSEARCH_PASSWORD" || logger "Found $PROMPT_ELASTICSEARCH_PASSWORD in system.yaml. Skipping prompt"
        if [[ "$JF_NODE_TYPE" != "$NODE_TYPE_STANDALONE" ]]; then
            wrapper_setSystemValue "${SYS_KEY_SHARED_ELASTICSEARCH_CLUSTERSETUP}" "YES"
        fi
    else
       if [[ "$JF_NODE_TYPE" == "$NODE_TYPE_STANDALONE" ]]; then
            wrapper_askElasticSearchUsername
            wrapper_askElasticSearchPassword
       else
           _updateFromSystemYaml "SHARED_ELASTICSEARCH_USERNAME" "KEY_ELASTICSEARCH_USERNAME"
           [ -z "$YAML_VALUE" ] && echo -n "Provide $ELASTICSEARCH_LABEL username of the first node" && wrapper_askUser "ELASTICSEARCH_USERNAME" || logger "Found $PROMPT_ELASTICSEARCH_USERNAME in system.yaml. Skipping prompt"
           _updateFromSystemYaml "SHARED_ELASTICSEARCH_PASSWORD" "KEY_ELASTICSEARCH_PASSWORD"
           [ -z "$YAML_VALUE" ] && echo -n "Provide $ELASTICSEARCH_LABEL password of the first node" && wrapper_askUser "ELASTICSEARCH_PASSWORD" || logger "Found $PROMPT_ELASTICSEARCH_PASSWORD in system.yaml. Skipping prompt"
       fi
    fi
}

wrapper_hook_askExtraRabbitMQNodeDetails() {
    logSilly "Method ${FUNCNAME[0]} (unimplemented)"
}

_askRabbitMQNodeDetails() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    _updateFromSystemYaml "RABBITMQ_ACTIVE_NODE_NAME"
    [ -z "$YAML_VALUE" ] && wrapper_askUser "RABBITMQ_ACTIVE_NODE_NAME" || logger "Found $PROMPT_RABBITMQ_ACTIVE_NODE_NAME in system.yaml. Skipping prompt"
    wrapper_hook_askExtraRabbitMQNodeDetails
}

_askPdnNodeJoinKey() {
    _updateFromSystemYaml "PDNNODE_JOINKEY" "$KEY_PDNNODE_JOINKEY"
    if [ -z "$YAML_VALUE" ]; then
        echo ""
        echo -n "Do you want to add PDN Node joinKey?[Y/n]: "
        read USER_CHOICE
        USER_CHOICE="$(echo "$USER_CHOICE" | tr '[:upper:]' '[:lower:]')"
        while [[ -z $USER_CHOICE || ! ${USER_CHOICE} == $FLAG_N && ! ${USER_CHOICE} == $FLAG_Y ]]; do
           warn "Invalid choice. Please retry"
           echo -n "Do you want to add PDN Node joinKey?[Y/n]: "
           read USER_CHOICE
           USER_CHOICE="$(echo "$USER_CHOICE" | tr '[:upper:]' '[:lower:]')"
        done
        if [[ "${USER_CHOICE}" == "${FLAG_Y}" ]]; then
           unset USER_CHOICE
           wrapper_askUser "PDNNODE_JOINKEY"
           getSystemValue "$SYS_KEY_PDNNODE_JOINKEY" "NOT_SET" "false" "$INSTALLER_YAML"
           PDNNODE_JOINKEY=${YAML_VALUE}
        fi
    else
        logger "Found $PROMPT_PDNNODE_JOINKEY in system.yaml. Skipping prompt"
    fi
}

_askPdnNodeHttpBouncerPort() {
    _updateFromSystemYaml "PDNNODE_HTTPBOUNCERPORT" "$KEY_PDNNODE_HTTPBOUNCERPORT"
    if [ -z "$YAML_VALUE" ]; then
        echo ""
        echo -n "Do you want to change default PDN Node httpBouncerPort?[Y/n]: "
        read USER_CHOICE
        USER_CHOICE="$(echo "$USER_CHOICE" | tr '[:upper:]' '[:lower:]')"
        while [[ -z $USER_CHOICE || ! ${USER_CHOICE} == $FLAG_N && ! ${USER_CHOICE} == $FLAG_Y ]]; do
           warn "Invalid choice. Please retry"
           echo -n "Do you want to change default PDN Node httpBouncerPort?[Y/n]: "
           read USER_CHOICE
           USER_CHOICE="$(echo "$USER_CHOICE" | tr '[:upper:]' '[:lower:]')"
        done
        if [[ "${USER_CHOICE}" == "${FLAG_Y}" ]]; then
           unset USER_CHOICE
           wrapper_askUser "PDNNODE_HTTPBOUNCERPORT"
           getSystemValue "$SYS_KEY_PDNNODE_HTTPBOUNCERPORT" "NOT_SET" "false" "$INSTALLER_YAML"
           PDNNODE_HTTPBOUNCERPORT=${YAML_VALUE}
        fi
    else
        logger "Found $PROMPT_PDNNODE_HTTPBOUNCERPORT in system.yaml. Skipping prompt"
    fi
}

_askPdnServerRouterExternalPort() {
    _updateFromSystemYaml "ROUTER_ENTRYPOINTS_EXTERNALPORT" "$KEY_ROUTER_ENTRYPOINTS_EXTERNALPORT"
    if [ -z "$YAML_VALUE" ]; then
        echo ""
        echo -n "Do you want to change default Router Entrypoints ExternalPort?[Y/n]: "
        read USER_CHOICE
        USER_CHOICE="$(echo "$USER_CHOICE" | tr '[:upper:]' '[:lower:]')"
        while [[ -z $USER_CHOICE || ! ${USER_CHOICE} == $FLAG_N && ! ${USER_CHOICE} == $FLAG_Y ]]; do
           warn "Invalid choice. Please retry"
           echo -n "Do you want to change default Router Entrypoints ExternalPort?[Y/n]: "
           read USER_CHOICE
           USER_CHOICE="$(echo "$USER_CHOICE" | tr '[:upper:]' '[:lower:]')"
        done
        if [[ "${USER_CHOICE}" == "${FLAG_Y}" ]]; then
           unset USER_CHOICE
           wrapper_askUser "ROUTER_ENTRYPOINTS_EXTERNALPORT"
           getSystemValue "$SYS_KEY_ROUTER_ENTRYPOINTS_EXTERNALPORT" "NOT_SET" "false" "$INSTALLER_YAML"
           ROUTER_ENTRYPOINTS_EXTERNALPORT=${YAML_VALUE}
        fi
    else
        logger "Found $PROMPT_ROUTER_ENTRYPOINTS_EXTERNALPORT in system.yaml. Skipping prompt"
    fi
}

_askForDBInputs() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    local databases=($EXTERNAL_DATABASES)
    for key in "${databases[@]}"; do
        case ${key} in
            $DATABASE_POSTGRES)
                _getDatabaseType
                if [ "$FLAG_MULTIPLE_DB_SUPPORT" == "$FLAG_Y" ]; then
                    # If multiple database is support by the product ask for database type and questions associated for that type
                    _askPostgresDetails
                elif [ ! -z "$DATABASE_TYPE" ] && [ "$DATABASE_TYPE" != "$SYS_KEY_SHARED_DATABASE_TYPE_VALUE_POSTGRES" ]; then
                    # If the database is not postgres, skip all the associated prompts.
                    logger "Found [$SYS_KEY_SHARED_DATABASE_TYPE] as [$DATABASE_TYPE] in system.yaml. Skipping $POSTGRES_LABEL prompts"
                else
                    _askPostgresDetails
                fi
            ;;
            $DATABASE_ELASTICSEARCH)
                _askElasticSearchDetails
            ;;
        esac
    done
}

_askForClusteredDBInputs() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    local databases=($CLUSTER_DATABASES)
    for key in "${databases[@]}"; do
        case ${key} in
            $DATABASE_RABBITMQ)
                _askRabbitMQNodeDetails
            ;;
        esac
    done
}

_getHAModeIfAvailable(){
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"

    if [ ! -z "$IS_HA" ]; then
        IS_HA="$(echo "$IS_HA" | tr '[:upper:]' '[:lower:]')"
        wrapper_setSystemValue "$KEY_ADD_TO_CLUSTER" "$IS_HA" "$FLAG_Y"
    fi
    
    if [ ! -f "$INSTALLER_YAML" ]; then
        logDebug "No installer yaml found. Skipping step"
        return
    fi

    local productHome="$(_getDataDir)"
    if [ -z "${productHome}" ]; then
        logDebug "No key: [$KEY_DATA_DIR_LOCATION] found. Aborting jfrog URL search"
        return
    fi

    local systemYaml="${productHome}/etc/${FILE_NAME_SYSTEM_YAML}"
    if [ ! -f "$systemYaml" ]; then
        return
    fi

    # Derive if this is a node based on the system.yaml value for Artifactory
    getYamlValue "${SYS_KEY_SHARED_NODE_HAENABLED}" "$systemYaml" "false"
    if [ ! -z "$YAML_VALUE" ] && [ "$YAML_VALUE" == "false" ]; then
        logger "Found ${SYS_KEY_SHARED_NODE_HAENABLED} [$YAML_VALUE]. Setting as HA"
        wrapper_setSystemValue "$KEY_ADD_TO_CLUSTER" "$FLAG_Y" "$FLAG_Y" 
        IS_HA="$FLAG_Y"
    fi
    
    # Derive if this is a node based on the system.yaml value for Xray
    getYamlValue "$SYS_KEY_RABBITMQ_ACTIVE_NODE_NAME" "$systemYaml" "false"
    if [ ! -z "$YAML_VALUE" ] && [ "$YAML_VALUE" != "None" ]; then
        logger "Found shared.rabbitMQ.active.node.name [$YAML_VALUE]. Setting as HA"
        wrapper_setSystemValue "$KEY_ADD_TO_CLUSTER" "$FLAG_Y" "$FLAG_Y"
        IS_HA="$FLAG_Y"
    fi

    # Derive if this is a node based on the system.yaml value for jfmc
    getYamlValue "$SYS_KEY_SHARED_ELASTICSEARCH_CLUSTERSETUP" "$systemYaml" "false"
    if [ ! -z "$YAML_VALUE" ] && [ "$YAML_VALUE" == "$SYS_KEY_SHARED_ELASTICSEARCH_CLUSTERSETUP_VALUE" ]; then
        logger "Found shared.elasticsearch.clusterSetup [$YAML_VALUE]. Setting as HA"
        wrapper_setSystemValue "$KEY_ADD_TO_CLUSTER" "$FLAG_Y" "$FLAG_Y"
        IS_HA="$FLAG_Y"
    fi
}

wrapper_getUserInputs() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"

    wrapper_createInstallerYaml
    wrapper_setInstallerDefaults

    wrapper_hook_start_getUserInputs

    _askForMandatoryInputs
    if [[ "${PRODUCT_NAME}" == "${PDN_NODE_LABEL}" ]]; then
        return
    fi
    _getHostIpIfAvailable
    if [ ! -z "$JF_SHARED_NODE_IP" ]; then
        wrapper_setSystemValue "$KEY_HOST_IP" "$JF_SHARED_NODE_IP"
    else 
        wrapper_askUser "HOST_IP"
    fi

    if [ "$DONT_PROMPT_USE_DEFAULTS" == "$FLAG_Y" ] || [ "$IS_UPGRADE" == "$FLAG_Y" ]; then
        return;        
    fi
    if [[ "${TRIAL_FLOW}" != "$FLAG_Y" ]]; then
        _updateFromSystemYaml "ADD_TO_CLUSTER" "" "${INSTALLER_STATE_YAML_FILE}"
        if [ -z "$YAML_VALUE" ]; then
            _getHAModeIfAvailable
            [ -z "$IS_HA" ] && wrapper_askUser "ADD_TO_CLUSTER" || true
            getYamlValue "$KEY_ADD_TO_CLUSTER" "$INSTALLER_YAML" "false"
        else
            logger "Found Cluster information as [$YAML_VALUE]. Skipping prompt"
        fi

        JF_NODE_TYPE=$([ "$YAML_VALUE" == "$FLAG_N" ] && echo -n "$NODE_TYPE_STANDALONE" || echo -n "$NODE_TYPE_CLUSTER_NODE")

        if [ "$JF_NODE_TYPE" == "$NODE_TYPE_STANDALONE" ]; then
            wrapper_standalone_hook_getUserInputs
        else
            wrapper_setSystemValue "$KEY_POSTGRES_INSTALL" "$FLAG_N"    

            _getMasterKeyIfAvailable
            if [ ! -z "$JF_SHARED_SECURITY_MASTERKEY" ]; then
                wrapper_setSystemValue "$KEY_CLUSTER_MASTER_KEY" "$JF_SHARED_SECURITY_MASTERKEY"
            else
                wrapper_askUser "CLUSTER_MASTER_KEY"
            fi        

            _askForClusteredDBInputs

            wrapper_ha_hook_getUserInputs
        fi
    else
        setSystemValue "${SYS_KEY_SHARED_DATABASE_TYPE}" "$SYS_KEY_SHARED_DATABASE_TYPE_VALUE_POSTGRES" "$INSTALLER_YAML"
        _setDatabaseDriver "${SYS_KEY_SHARED_DATABASE_TYPE_VALUE_POSTGRES}"
    fi

    _askForDBInputs

    wrapper_hook_getUserInputs

    wrapper_hook_setRedisPassword

    wrapper_hook_setRabbitmqPassword   
}

# This method should NOT have logs since it returns a string
_isAValidChoice() {
    local userChoice="$1"
    if [ -z "$2" ]; then
        echo -n "$FLAG_Y"
    fi
    local validChoices=($2)
    for key in "${validChoices[@]}"; do
        if [ "$key" == "$userChoice" ]; then
            echo -n "$FLAG_Y"
        fi
    done
    echo -n "$FLAG_N"
}

## Utility method to get user inputs during installations
## Parameters: 
##  message (will be displayed to the user)
##  yamlKey (will be used to read the default value)
##  yamlFile (will be used to read the default value)
##  validValues (if passed, the user will be forced to choose from these inputs)
## Globals:
## DONT_PROMPT_USE_DEFAULTS: if set to "yes", the user will not be prompted and the default value will be set
## USER_CHOICE: will contain the user's choice
## Returns: none
sys_askUserYaml(){
    local message="$1"
    local yamlKey="$2"
    local yamlFile="$3"
    local validValuesAsString="$4"

    # validate that this method has everything it needs to proceed
    if [ -z "${message}" ] || [ -z "${yamlKey}" ] || [ -z "${yamlFile}" ] || [ ! -f "${yamlFile}" ]; then
        warn "Invalid invocation of method."
        return 1
    fi

    # read the yaml file
    getYamlValue "${yamlKey}" "${yamlFile}" "false"
    local defaultValue="${YAML_VALUE}"

    sys_askUser "$message" "$defaultValue" "$validValuesAsString"

    if [ "$USER_CHOICE" != "$FLAG_NOT_APPLICABLE" ]; then
        setSystemValue "$yamlKey" "$USER_CHOICE" "$yamlFile"
    fi
}

## Utility method to get user inputs during installations
## Parameters: 
##  yamlKey (will be used to read the default value)
## Globals:
## DONT_PROMPT_USE_DEFAULTS: if set to "yes", the user will not be prompted and the default value will be set
## USER_CHOICE: will contain the user's choice
## Returns: none
wrapper_askUser(){
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    local yamlKey="$1"
    local yamlFile="$INSTALLER_YAML"

    # validate that this method has everything it needs to proceed
    if [ -z "${yamlKey}" ]; then
        warn "Invalid invocation of method."
        return 1
    fi

    eval key=\${KEY_${yamlKey}}
    if [ -z "$key" ]; then
        warn "Invalid invocation of method."
        return 1
    fi

    # read the yaml file
    getYamlValue "${key}" "${yamlFile}" "$FLAG_N"
    local defaultValue="${YAML_VALUE}"

    _getMethodOutputOrVariableValue "MESSAGE_${yamlKey}"
    if [ ! -z "$EFFECTIVE_MESSAGE" ]; then
        _messageBeforePrompt "$EFFECTIVE_MESSAGE"
    else
        #If there is no message before a prompt, add a new line explicitly. The message takes care of it usually
        echo ""
    fi
    
    _getMethodOutputOrVariableValue "PROMPT_${yamlKey}"
    local prompt="$EFFECTIVE_MESSAGE"

    if [ -z "$prompt" ]; then
        warn "Invalid key. No prompt found"
        return 1
    fi
    sys_askUser "$prompt" "$defaultValue" "$yamlKey"
    
    modifyValue "USER_CHOICE" "${yamlKey}"

    if [ "$USER_CHOICE" != "$FLAG_NOT_APPLICABLE" ]; then
        setSystemValue "${key}" "$USER_CHOICE" "$yamlFile"
    fi
}

_getPromptWithDefaults() {
    local validValuesAsString="$1"
    local defaulfValue="$2"

    if [ ! -z "$validValuesAsString" ] && [ -z "$defaultValue" ]; then
       echo -n " [$(echo $validValuesAsString | sed -e "s# #/#g" )]" # replaces spaces with /
    elif [ -z "$validValuesAsString" ] && [ ! -z "$defaultValue" ]; then
        echo -n " (Default: $defaultValue)"
    elif [ ! -z "$validValuesAsString" ]; then
        local validValues=($validValuesAsString)
        local textToDisplay=
        for key in "${validValues[@]}"; do
            if [ "$defaultValue" == "$key" ];then
                key="$(echo "$key" | tr '[:lower:]' '[:upper:]')"
            fi
            textToDisplay="$textToDisplay $key"
        done
        echo -n " [$(echo $textToDisplay | sed -e "s# #/#g" )]" # replaces spaces with /
    fi
    
}

## Utility method to get user inputs
## Parameters: 
##  message (will be displayed to the user)
##  defaultValue (will be the default value selectable by pressing enter)
##  validValues (if passed, the user will be forced to choose from these inputs)
## Globals:
## DONT_PROMPT_USE_DEFAULTS: if set to "yes", the user will not be prompted and the default value will be set
## USER_CHOICE: will contain the user's choice
## Returns: none
sys_askUser(){
    local message="$1"
    local defaultValue="$2"
    local yamlKey="$3"

    _getMethodOutputOrVariableValue "VALID_VALUES_${yamlKey}"
    local validValuesAsString="$EFFECTIVE_MESSAGE"
    
    _getMethodOutputOrVariableValue "REGEX_${yamlKey}"
    local regexToValidate="$EFFECTIVE_MESSAGE"
    
    _getMethodOutputOrVariableValue "ERROR_MESSAGE_${yamlKey}"
    local regexValidationMessage="$EFFECTIVE_MESSAGE"

    # validate that this method has everything it needs to proceed
    if [ -z "${message}" ]; then
        warn "Invalid invocation of method."
        return 1
    fi

    eval isASensitiveKey=\${IS_SENSITIVE_${yamlKey}}
    eval isOptional=\${IS_OPTIONAL_${yamlKey}}

    if [ ! -z "$defaultValue" ]; then
        if [ "${DONT_PROMPT_USE_DEFAULTS}" == "$FLAG_Y" ]; then
            # If the global flag is set to never prompt the user, just use the default value and return
            USER_CHOICE="${defaultValue}"
            return 0
        fi
        if [ ! -z "$isASensitiveKey" ]; then
            message="$message (****)"
        fi
    fi
    if [ -z "$isASensitiveKey" ]; then
        message="$message$(_getPromptWithDefaults "$validValuesAsString" "$defaultValue")"
    fi

    # If not, prompt the user
    echo -n "$message: "
    USER_CHOICE=
    while [ -z "$USER_CHOICE" ]; do
        if [ ! -z "$isASensitiveKey" ]; then
            read -s USER_CHOICE
            echo "" # new line after senstitive key is entered
        else
            read USER_CHOICE
        fi

        : ${USER_CHOICE:="$defaultValue"}
        # IF the value is a flag, lower case it
        if [ ! -z "$validValuesAsString" ]; then
            USER_CHOICE="$(echo "$USER_CHOICE" | tr '[:upper:]' '[:lower:]')"
            if [ $(_isAValidChoice "$USER_CHOICE" "$validValuesAsString") == "$FLAG_N" ]; then
                warn "Invalid choice. Please retry"
                echo -n "$message: "
                unset USER_CHOICE
            fi
        elif [ ! -z "$regexToValidate" ]; then
                validateRegex "$USER_CHOICE" "$regexToValidate" || {
                logDebug "The input failed validations [$regexToValidate]"
                warn "$regexValidationMessage"
                echo -n "$message: "
                unset USER_CHOICE
            }
        elif [ -z "$USER_CHOICE" ] && [ "$isOptional" == "$FLAG_Y" ]; then
            USER_CHOICE="$FLAG_NOT_APPLICABLE"
        fi
    done
    if [ ! -z "$isASensitiveKey" ]; then
        echo "" # new line after senstitive key is entered
    fi
}

checkJfrogUrlSystemHealthCheck () {
    local apiCall="router/api/v1/system/ping"
    local jfrogUrlTimeout=

    local productHome="$(_getDataDir)"
    local systemYaml="${productHome}/etc/${FILE_NAME_SYSTEM_YAML}"
    
    getYamlValue "$SYS_KEY_SHARED_JFROGURL" "$INSTALLER_YAML" "false"
    local jfrogUrl=$YAML_VALUE
    
    local osType=$(uname -m)
    if [[ "${osType}" == "arm64" ||  "${osType}" == "aarch64" ]]; then
        diagnosticBinary=${COMPOSE_HOME}/bin/diagnostics/diagnosticsUtil_arm64
    else
        diagnosticBinary=${COMPOSE_HOME}/bin/diagnostics/diagnosticsUtil
    fi

    if [ ! -z "${jfrogUrl}" ]; then
        jfrogUrl=${jfrogUrl%/}
        if [[ "$WRAPPER_SCRIPT_TYPE" == "$WRAPPER_SCRIPT_TYPE_DOCKER_COMPOSE" ]]; then
            [[ -f "${diagnosticBinary}" ]] && ${diagnosticBinary} jfrog jfrog-url "${jfrogUrl}"  2>&1
        else
            [[ -f "${SCRIPT_HOME}/diagnostics/diagnosticsUtil" ]] && ${SCRIPT_HOME}/diagnostics/diagnosticsUtil jfrog jfrog-url "${jfrogUrl}" 2>&1
        fi
        if [[ $? == 0 ]]; then
            echo ""
            return
        else
            echo ""
        fi
        warn "Could not access the JFrog platform with the provided JFrog URL (${jfrogUrl})"
        echo -e "You can choose to continue anyway, or change the url address"
        echo -n "Do you want to continue [Y/n]: "
        read USER_CHOICE
        echo ""
        USER_CHOICE="$(echo "$USER_CHOICE" | tr '[:upper:]' '[:lower:]')"
        while [[ -z $USER_CHOICE || ! ${USER_CHOICE} == $FLAG_N && ! ${USER_CHOICE} == $FLAG_Y ]]; do
           warn "Invalid choice. Please retry"
           echo -n "Do you want to continue [Y/n]: "
           read USER_CHOICE
           USER_CHOICE="$(echo "$USER_CHOICE" | tr '[:upper:]' '[:lower:]')"
        done
        if [[ "${USER_CHOICE}" == "${FLAG_N}" ]]; then
           unset USER_CHOICE
           wrapper_askUser "JFROGURL"
           checkJfrogUrlSystemHealthCheck
	    fi
    fi
}

askPostgresUpgrade() {
    if [[ "${SKIP_POSTGRES_SETUP}" == "${FLAG_Y}" ]]; then
        warn "Proceeding with $PRODUCT_NAME upgrade with $POSTGRES_LABEL ${POSTGRES_OLD}"
        return 0;
    fi
    warn "$PRODUCT_NAME is currently using $POSTGRES_LABEL ${POSTGRES_OLD}"
    echo -e "It is highly recommended to upgrade your $POSTGRES_LABEL DB."
    echo -e "The Docker Compose template with $POSTGRES_LABEL ${POSTGRES_VERSION}v is available here [$COMPOSE_TEMPLATES/docker-compose-postgres.yaml]"
    echo -e "For more information on $POSTGRES_LABEL upgrades, see https://www.postgresql.org/docs/${POSTGRES_MAJOR_VER}/upgrading.html"
    echo -e "Continuing will upgrade $PRODUCT_NAME with the existing $POSTGRES_LABEL ${POSTGRES_OLD}."
    echo -n "Do you wish to exit the upgrade script and to upgrade your $POSTGRES_LABEL to ${POSTGRES_VERSION}v? [Y/n]: "
    read USER_CHOICE
    echo ""
    USER_CHOICE="$(echo "$USER_CHOICE" | tr '[:upper:]' '[:lower:]')"
    while [[ -z $USER_CHOICE || ! ${USER_CHOICE} == $FLAG_N && ! ${USER_CHOICE} == $FLAG_Y ]]; do
        warn "Invalid choice. Please retry"
        echo -n "Do you want to abort the upgrade script and upgrade your $POSTGRES_LABEL to a ${POSTGRES_VERSION}v? [Y/n]: "
        read USER_CHOICE
        USER_CHOICE="$(echo "$USER_CHOICE" | tr '[:upper:]' '[:lower:]')"
    done
    if [[ "${USER_CHOICE}" == "${FLAG_N}" ]]; then
        unset USER_CHOICE
        warn "Proceeding with $PRODUCT_NAME upgrade with $POSTGRES_LABEL ${POSTGRES_OLD}"
    fi
    if [[ "${USER_CHOICE}" == "${FLAG_Y}" ]]; then
        unset USER_CHOICE
        exit 0
    fi
}


WRAPPER_SCRIPT_TYPE="$WRAPPER_SCRIPT_TYPE_DOCKER_COMPOSE"

# Utility method to print if user types -h
# Failure conditions: none
# Parameters: none
# Depends on global: DEFAULT_ROOT_DATA_DIR
# Updates global: none
# Returns: NA

docker_usage() { 
    cat << END_USAGE
$0 - This script prepares directories on the local host for mounting into application containers

Usage: $0 <options>

Supported options
-h|--help       : Show this usage

Examples
* Start : sudo $0

END_USAGE
    exit 1
}

showDockerExamples() {
cat << END_USAGE
\033[1mExamples:\033[0m
cd ${COMPOSE_HOME}

$(docker_hook_productSpecificComposeHelp)
$(docker_postgresSpecificComposeHelp)
start:               docker-compose -p ${JF_PROJECT_NAME} up -d
stop:                docker-compose -p ${JF_PROJECT_NAME} down

\033[1mNOTE:\033[0m The compose file uses several environment variables from the .env file. Remember to run from within the [${COMPOSE_HOME}] folder

END_USAGE
}

docker_hook_copyComposeFile(){
    logSilly "Method ${FUNCNAME[0]}"
}

docker_setupModeSpecificVar() {
    # Set JF_ROOT_DATA_DIR
    if [ -z "${JF_ROOT_DATA_DIR}" ]; then
        JF_ROOT_DATA_DIR=${DEFAULT_ROOT_DATA_DIR}
    fi

    if [ -z "${SERVER_URL}" ] && [ ! -z "${JF_SHARED_JFROGURL}" ]; then
        SERVER_URL="${JF_SHARED_JFROGURL}"
    fi

    docker_getSystemValue "$KEY_STANDALONE_ELASTICSEARCH_INSTALL" "IS_ELASTICSEARCH_REQUIRED" "$INSTALLER_YAML"
    IS_ELASTICSEARCH_REQUIRED=${YAML_VALUE:-"$IS_ELASTICSEARCH_REQUIRED"}
    logDebug "Method ${FUNCNAME[0]}. IS_ELASTICSEARCH_REQUIRED is now [$IS_ELASTICSEARCH_REQUIRED]"

    SYSTEM_YAML_FILE="${JF_ROOT_DATA_DIR}/var/etc/${FILE_NAME_SYSTEM_YAML}"
    JF_SYSTEM_YAML="${SYSTEM_YAML_FILE}"
    docker_setInstallerStateFile "${JF_ROOT_DATA_DIR}"

    COMPOSE_TEMPLATES="$COMPOSE_HOME/templates"
    COMPOSE_FILE="${COMPOSE_HOME}/docker-compose.yaml"

    if [ -z "${PROJECT_ROOT_FOLDER}" ]; then
        PROJECT_ROOT_FOLDER=${JF_ROOT_DATA_DIR##*/}
    fi
    logDebug "PROJECT_ROOT_FOLDER is [${PROJECT_ROOT_FOLDER}]"

}

# Process command line options. See docker_usage for supported options
docker_processOptions() {
    #Determine the data folder to use for artifactory
    DEFAULT_ROOT_DATA_DIR="${JF_ROOT_DATA_DIR}"
    JF_NODE_TYPE="$NODE_TYPE_STANDALONE"

    while [[ $# > 0 ]]; do
        case "$1" in
            -v | --verbose)
                VERBOSE_MODE=true
                logger "Running installer in verbose mode"
                shift
            ;;
            -h | --help)
                docker_usage
                exit 0
            ;;
            *)
                logError "Option is not supported!"
                docker_usage
            ;;
        esac
    done
}



# Utility method to check if an application is already running
# Failure conditions:
## Exits if compose file is not available
# Parameters: none
# Depends on global: COMPOSE_HOME, COMPOSE_FILE, PROJECT_NAME
# Updates global: none
# Returns: 0 if running. 1 if not running

docker_isDeployed () {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    pushd $COMPOSE_HOME > /dev/null
    [ -f ${COMPOSE_FILE} ] || errorExit "Cannot check if system is deployed. ${COMPOSE_FILE} is missing"
    logger "Checking if the application is running. Executing command [docker-compose -p ${JF_PROJECT_NAME} ps -q]"
    docker-compose -p ${JF_PROJECT_NAME} ps -q > /dev/null || exitCode=$? && true
    local is_deployed="${exitCode}"
    popd > /dev/null
    if [ -n "$is_deployed" ]; then
        logDebug "Application is deployed: [true]"
        return 0
    else
        logDebug "Application is deployed: [false]"
        return 1
    fi
}

docker_hook_productSpecificComposeHelp(){
    logSilly "Method ${FUNCNAME[0]}"
}

# Utility method to display a successMessage after a successful dockercompose start
# Failure conditions:NA 
# Parameters: none
# Depends on global: JF_ROOT_DATA_DIR, EXTRA_MSG, 
# Updates global: none
# Returns: NA

docker_showSuccessMessage() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    if [[ "${PRODUCT_NAME}" == "${PDN_NODE_LABEL}" ]]; then
        docker_getSystemValue "$SYS_KEY_PDNNODE_PDNSERVERURL" "$FLAG_NOT_APPLICABLE" "$SYSTEM_YAML_FILE"
        SERVER_URL=$(echo "${YAML_VALUE}" | awk -F":" '{print $1}')
        SERVER_URL="${SERVER_URL}:<ARTIFACTORY_ROUTER_EXTERNAL_PORT>"
    fi
    local contentToPrint=$(cat << CONTENT_TO_PRINT
Installation directory: [$JF_ROOT_DATA_DIR] contains data and configurations.

Use docker-compose commands to start the application. Once the application has started, it can be accessed at [${SERVER_URL}]

$(showDockerExamples)

CONTENT_TO_PRINT
)
    banner "Docker setup complete" "${contentToPrint}"
}

docker_runComposeUp(){
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    pushd $COMPOSE_HOME > /dev/null
    logger "Running command [docker-compose -p ${JF_PROJECT_NAME} -f ${COMPOSE_FILE} up -d --remove-orphans]"
    docker-compose -p ${JF_PROJECT_NAME} -f ${COMPOSE_FILE} up -d --remove-orphans || errorExit "Failed to start application services"
    popd > /dev/null
}

# Utility method to start a single container
# Failure conditions: if start fails
# Parameters: 
    # Name of the service (NOTE: *not* container name)
# Depends on global: JF_PROJECT_NAME, COMPOSE_FILE
# Updates global: none
# Returns: NA

docker_startContainer() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    pushd $COMPOSE_HOME > /dev/null
        docker-compose -f ${COMPOSE_FILE} -p ${JF_PROJECT_NAME} up -d "$1" ||  errorExit "Failed to start $1"
    popd > /dev/null
}

# Utility method to copy a file to a container
# Failure conditions: if copying fails
# Parameters: 
    # Name of the file
    # Name of the container
    # Destination folder
# Depends on global: none
# Updates global: none
# Returns: NA

docker_copyFileToContainer() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    local fileToCopy="$1"
    local destinationContainer="$2"
    local destinationFolder="$3"
    # Copy script to container
    [ -f "$fileToCopy" ] || errorExit "Could not copy file. [$fileToCopy] is missing"

    local n=1
    local max=3
    local delay=2
    local SUCCESS=false

    while [[ $n -lt $max && "$SUCCESS" == "false" ]] ; do
        docker cp "${fileToCopy}" ${destinationContainer}:${destinationFolder} && SUCCESS=true || SUCCESS=false
        ((n++))
        [[ $n -lt $max && "$SUCCESS" == "false" ]] && logger "Retrying copy of ${fileToCopy} to ${destinationContainer}" || true
        sleep $delay;
    done
    if [[ "$SUCCESS" == "true" ]]; then
        logger "Successfully copied [$fileToCopy] to [${destinationContainer}]"
    else
        errorExit "Failed to copy [$fileToCopy] to [${destinationContainer}]"
    fi
}

docker_addToEnvFile(){
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    if [[ -z "$1" || "$1" == "" ]]; then
        return
    fi

    local osType=$(uname)

    local key_to_add=$1
    local value_to_add=$2
    
    local env_path="$ENV"
    
    export $key_to_add="${value_to_add}"
    
    local entryExists="$FLAG_Y"
    grep "$key_to_add=" "$env_path" >/dev/null 2>&1 || entryExists="$FLAG_N"

    if [ "$entryExists" == "$FLAG_Y" ]; then
        logDebug "replacing [$key_to_add] in $env_path"
        replaceText "$key_to_add=.*" "$key_to_add=$value_to_add" "${env_path}"
    fi
}

_getYamlValueAndUpdateEnv() {
    local sourceKey="$1"
    local targetEnv="$2"
    local sourceYaml="$3"

    logDebug "Method ${FUNCNAME[0]} sourceKey:[$sourceKey], targetEnv:[$targetEnv]"

    docker_getSystemValue "$sourceKey" "$FLAG_NOT_APPLICABLE" "$sourceYaml"

    if [ ! -z "$YAML_VALUE" ]; then
        eval $targetEnv=${YAML_VALUE}
        docker_addToEnvFile "$targetEnv" "${YAML_VALUE}"
        logDebug "The key [$targetEnv] in .env has been updated from system.yaml [$sourceKey] with value [$(getPrintableValueOfKey "$sourceKey" "$YAML_VALUE")]"
    fi
}

_commonYamlProperties() {
    _getYamlValueAndUpdateEnv "shared.jfrogUrl" "JF_SHARED_JFROGURL" "$1"
    _getYamlValueAndUpdateEnv "shared.node.id" "JF_SHARED_NODE_ID" "$1"
    _getYamlValueAndUpdateEnv "shared.node.ip" "JF_SHARED_NODE_IP" "$1"
    _getYamlValueAndUpdateEnv "shared.security.joinKey" "JF_SHARED_SECURITY_JOINKEY" "$1"
    _getYamlValueAndUpdateEnv "shared.security.masterKey" "JF_SHARED_SECURITY_MASTERKEY" "$1"
    _getYamlValueAndUpdateEnv "router.entrypoints.externalPort" "JF_ROUTER_ENTRYPOINTS_EXTERNALPORT" "$1"
    _getYamlValueAndUpdateEnv "shared.logging.rotation.maxFiles" "NGINX_LOG_ROTATE_COUNT" "$1"
    _getYamlValueAndUpdateEnv "shared.logging.rotation.maxSizeMb" "NGINX_LOG_ROTATE_SIZE" "$1"
}

# This method should be overridden in products
docker_hook_updateFromYaml() {
    logSilly "Method ${FUNCNAME[0]}"
}

# This method has been introduced to ensure that any pre-existing configuration is copied over 
# from the system yaml to this file
docker_updateFromYaml () {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"

    SYSTEM_YAML_FILE="${JF_ROOT_DATA_DIR}/var/etc/${FILE_NAME_SYSTEM_YAML}"

    # update from the existing system yaml file if one is available
    if [ ! -z "$SYSTEM_YAML_FILE" ] && [ -f "$SYSTEM_YAML_FILE" ]; then
        _commonYamlProperties "$SYSTEM_YAML_FILE"
        docker_hook_updateFromYaml "$SYSTEM_YAML_FILE"
    fi
}

docker_doValidations() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    logger "Validating System requirements"
    validateLinuxOrMac
    validateSudo
    checkDocker
    validationCheckPrerequisites
    docker_validateDiskSpace
    logDebug "All validations successful"
}

docker_validateDiskSpace() {
    if [ ! -z "${JF_ROOT_DATA_DIR}" ]; then
        if [ -d "${JF_ROOT_DATA_DIR}/var" ]; then
            validateDiskSpace "${JF_ROOT_DATA_DIR}/var"
        fi
    fi
}
# hook implemented in each individual script if necessary
docker_hook_setDirInfo () {
    logSilly "Method ${FUNCNAME[0]}"
}

docker_setDirInfo() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    logDebug "Root Directory being used is: ${JF_ROOT_DATA_DIR}"
    #Ensures that the user's chosen root directory is set as an env variable overriding the default
    export JF_ROOT_DATA_DIR="${JF_ROOT_DATA_DIR}"
    #update the env file with the changed data folder location
    THIRDPARTY_DATA_ROOT="/var/data" #This variable is used by each bootstrap (can be ignored)
    docker_hook_setDirInfo #Implemented in each script
}

docker_setRootDirPermissions() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    local OS_TYPE=$(uname)
    local message="Aborting Installation. The folder [${JF_ROOT_DATA_DIR}] should be owned by [${DOCKER_USER}:${DOCKER_USER}]. Please fix by running the command: [chown ${DOCKER_USER}:${DOCKER_USER} ${JF_ROOT_DATA_DIR}]"

    # Fix directories ownerships
    io_setOwnershipNonRecursive "${JF_ROOT_DATA_DIR}" "${DOCKER_USER}" "${DOCKER_USER}" || errorExit "$message"

    # Give wide permissions on Mac (to support the non-root Artifactory and Nginx containers)
    if [ "${OS_TYPE}" == "Darwin" ]; then
        logDebug "Setting 777 permissions on [${JF_ROOT_DATA_DIR}]"
        chmod -R 777 "${JF_ROOT_DATA_DIR}" || errorExit "Setting 777 permissions on [${JF_ROOT_DATA_DIR}] failed"
    fi
}

docker_createThirdPartyDir() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    logger "Creating third party directories (if necessary)"

    io_createPostgresDir || errorExit "Aborting installation. Unable to create third-party folder necessary for PostgreSQL"
    io_createNginxDir || errorExit "Aborting installation. Unable to create third-party folder necessary for Nginx"
    io_createElasticSearchDir || errorExit "Aborting installation. Unable to create third-party folder necessary for Elastic Search"
    io_createRedisDir || errorExit "Aborting installation. Unable to create third-party folder necessary for Redis"
    io_createMongoDir || errorExit "Aborting installation. Unable to create third-party folder necessary for Mongo"
    io_createRabbitMQDir || errorExit "Aborting installation. Unable to create third-party folder necessary for RabbitMQ"
}

docker_hook_createDirectories() {
    logSilly "Method ${FUNCNAME[0]}"
}

docker_createDirectories() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    # Check if this is the first creation of the directories
    if [ ! -d "${JF_ROOT_DATA_DIR}" ]; then
        logDebug "First setup. Setting FIRST_SETUP=true"
        FIRST_SETUP=true #This will be used to set 777 permissions on Macs
    fi

    createDir "${JF_ROOT_DATA_DIR}/var" "${DOCKER_USER}" "${DOCKER_USER}"
    createDir "${JF_ROOT_DATA_DIR}/var/etc" "${DOCKER_USER}" "${DOCKER_USER}"
    createDir "${JF_ROOT_DATA_DIR}/var/data" "${DOCKER_USER}" "${DOCKER_USER}"
    createDir "${JF_ROOT_DATA_DIR}/var/log" "${DOCKER_USER}" "${DOCKER_USER}"
    createDir "${JF_ROOT_DATA_DIR}/var/etc/security" "${DOCKER_USER}" "${DOCKER_USER}"

    docker_hook_createDirectories
    
    logDebug "value of [FIRST_SETUP] is [$FIRST_SETUP]"
    if [ "$FIRST_SETUP" == true ]; then
        io_setOwnership "${JF_ROOT_DATA_DIR}" "${DOCKER_USER}" "${DOCKER_USER}" || errorExit "Setting ownership of [${JF_ROOT_DATA_DIR}] to [${DOCKER_USER}:${DOCKER_USER}] failed"
    fi
}

docker_readKeyFile () {
    FILE_CONTENT=""
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    
    local fileName=$1
    local sleepTimer=5
    local counter=1
    local maxTries=5

    local defaultFile="${JF_ROOT_DATA_DIR}/var/etc/security/${fileName}"
    echo ""
    echo -n "Waiting for [$defaultFile] to be created"
    
    #wait for the file to get created
    while [ ${counter} -lt ${maxTries} ]; do
        let "sleepTimer=$sleepTimer*2"
        if [ ${VERBOSE_MODE} != "true" ];then
            echo -n "."
        else
            logDebug "sleepTimer: [$sleepTimer], counter: [$counter]"
        fi
        sleep ${sleepTimer}
        if [ -f "${defaultFile}" ];then
            let "counter=$maxTries"
        else
            let "counter+=1"
        fi
        
    done
    echo ""
    if [ -f "${defaultFile}" ];then
        logDebug "${defaultFile} found"
        FILE_CONTENT=$(cat "${defaultFile}" || errorExit "Could not read ${defaultFile}" )
    else
        logDebug "${defaultFile} NOT found"
        FILE_CONTENT="NOT_SET"
    fi
    
}

docker_setSystemValue() {
    io_setYQPath

    local keyToUpdate="$1"
    local valueToUpdate="$2"

    local targetFile=${3:-$SYSTEM_YAML_FILE}
    local overwrite=${4:-"$FLAG_N"}
    logDebug "Method ${FUNCNAME[0]}. Setting [$keyToUpdate] to [$targetFile]"

    # If the key being updated already exists in the system.yaml, DONT update it: system.yaml is considered the source of truth
    if [ "${targetFile}" == "$SYSTEM_YAML_FILE" ]; then
        overwrite="$FLAG_N"
    fi

    getYamlValue "$keyToUpdate" "${targetFile}" "$FLAG_N"
    # If a value already exists, check if we need to overwrite
    if [ ! -z "${YAML_VALUE}" ] && [ "${overwrite}" == "$FLAG_N" ]; then
        return 0
    fi
    setSystemValue "$keyToUpdate" "$valueToUpdate" "$targetFile"
}

docker_removeSystemValue() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    io_setYQPath
    removeYamlValue "$1" "$2"
}

docker_getSystemValue() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    io_setYQPath
    local keyToFetch="$1"
    local fileToUpdateFrom="$3"
    local default=
    local envVar="$2"
    getYamlValue "$keyToFetch" "${fileToUpdateFrom}" "$FLAG_N"
    logDebug "Method ${FUNCNAME[0]}. key:[$keyToFetch]"
    if [ -z "${YAML_VALUE}" ] && [ ! -z "$envVar" ] && [ "$envVar" != "$FLAG_NOT_APPLICABLE" ]; then
        eval YAML_VALUE=\${${envVar}}
    fi
}

#this is a hook. can be implemented in each individual script
docker_hook_setupThirdParty () {
    logSilly "Method ${FUNCNAME[0]}"
}


# A hook that is meant to be implemented by each product to create any configuration specific to it
docker_hook_postSystemYamlCreation () {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
}

docker_installToSystemYaml() {
  logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"  
  local keyToFetch=$1
  local envVariable="$2"
  local systemYamlKey="$3"
  local defaultValue="$4"
  local targetYaml="${5:-${SYSTEM_YAML_FILE}}"

  if [ ! -f "$INSTALLER_YAML" ]; then
    logDebug "No installer yaml found. Skipping step"
    return
  fi
  docker_getSystemValue "$keyToFetch" "$envVariable" "$INSTALLER_YAML"
  local valueToUpdate=${YAML_VALUE:-"$defaultValue"}
  if [ -z "$valueToUpdate" ]; then
    return 1
  fi
  docker_setSystemValue "$systemYamlKey" "$valueToUpdate" "$targetYaml"
  return 0
}

docker_createSystemYaml() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    
    if [[ "${PRODUCT_NAME}" != "${PDN_NODE_LABEL}" ]]; then
        JF_SHARED_NODE_IP=${JF_SHARED_NODE_IP:-"$(io_getPublicHostIP)"}
        docker_setSystemValue "shared.node.ip" "$JF_SHARED_NODE_IP" "${SYSTEM_YAML_FILE}"
        
        if [ "$IS_DEVELOPMENT_MODE" == "$FLAG_Y" ]; then
            logBold "In Development mode. Adding additional config"
            # During development and testing, we deploy more than one product in the same mc
            docker_setSystemValue "shared.node.id" "$(io_getPublicHostID)-${JF_PROJECT_NAME}" "${SYSTEM_YAML_FILE}"
        else
            docker_setSystemValue "shared.node.id" "$(io_getPublicHostID)" "${SYSTEM_YAML_FILE}"
        fi
        docker_setSystemValue "shared.node.name" "$(io_getPublicHostName)" "${SYSTEM_YAML_FILE}"
    fi

    if [ ! -z "${JF_SHARED_JFROGURL}" ]; then
        docker_setSystemValue "shared.jfrogUrl" "${JF_SHARED_JFROGURL}" "${SYSTEM_YAML_FILE}"
    fi

    if [ ! -z "${JF_PDNNODE_PDNSERVERURL}" ]; then
        docker_setSystemValue "${SYS_KEY_PDNNODE_PDNSERVERURL}" "${JF_PDNNODE_PDNSERVERURL}" "${SYSTEM_YAML_FILE}"
    fi

    if [ ! -z "${JF_PDNNODE_SELFGRPCADDRESS}" ]; then
        docker_setSystemValue "${SYS_KEY_PDNNODE_SELFGRPCADDRESS}" "${JF_PDNNODE_SELFGRPCADDRESS}" "${SYSTEM_YAML_FILE}"
    fi

    if [ ! -z "${JF_PDNNODE_SELFHTTPADDRESS}" ]; then
        docker_setSystemValue "${SYS_KEY_PDNNODE_SELFHTTPADDRESS}" "${JF_PDNNODE_SELFHTTPADDRESS}" "${SYSTEM_YAML_FILE}"
    fi

    if [ ! -z "${JF_SHARED_SECURITY_JOINKEY}" ]; then
        docker_setSystemValue "shared.security.joinKey" "${JF_SHARED_SECURITY_JOINKEY}" "${SYSTEM_YAML_FILE}"
    fi
     
    # Mark wss flavour in system yaml and  add whitesource url
    if [ ! -z "$THIRD_PARTY_SCRIPT" ]; then
        docker_setSystemValue "${SYS_KEY_SERVER_WSS_BASE_URL_KEY}" "${WSS_SERVER_BASE_URL}" "${SYSTEM_YAML_FILE}"
        docker_setSystemValue "${SYS_KEY_SERVER_INTEGRATION_ENABLE_KEY}" "true" "${SYSTEM_YAML_FILE}"
    fi

    if [ ! -z "${JF_ROUTER_ENTRYPOINTS_EXTERNALPORT}" ]; then
        if [[ "${PRODUCT_NAME}" != "${PDN_SERVER_LABEL}" ]]; then
            docker_setSystemValue "router.entrypoints.externalPort" "${JF_ROUTER_ENTRYPOINTS_EXTERNALPORT}" "${SYSTEM_YAML_FILE}"
        fi
    fi
    docker_installToSystemYaml "$KEY_DATABASE_TYPE"     "$FLAG_NOT_APPLICABLE" "${SYS_KEY_SHARED_DATABASE_TYPE}"     || logSilly "Key: KEY_DATABASE_TYPE not applicable"
    docker_installToSystemYaml "$KEY_DATABASE_DRIVER"   "$FLAG_NOT_APPLICABLE" "${SYS_KEY_SHARED_DATABASE_DRIVER}"   || logSilly "Key: KEY_DATABASE_DRIVER not applicable"    
    docker_installToSystemYaml "$KEY_DATABASE_URL"      "$FLAG_NOT_APPLICABLE" "${SYS_KEY_SHARED_DATABASE_URL}"      || logSilly "Key: KEY_DATABASE_URL not applicable"
    docker_installToSystemYaml "$KEY_DATABASE_USERNAME" "$FLAG_NOT_APPLICABLE" "${SYS_KEY_SHARED_DATABASE_USERNAME}" || logSilly "Key: KEY_DATABASE_USERNAME not applicable"
    docker_installToSystemYaml "$KEY_DATABASE_PASSWORD" "$FLAG_NOT_APPLICABLE" "${SYS_KEY_SHARED_DATABASE_PASSWORD}" || logSilly "Key: KEY_DATABASE_PASSWORD not applicable"
    docker_installToSystemYaml "$KEY_ELASTICSEARCH_USERNAME" "$FLAG_NOT_APPLICABLE" "${SYS_KEY_SHARED_ELASTICSEARCH_USERNAME}" || logSilly "Key: KEY_ELASTICSEARCH_PASSWORD not applicable"
    docker_installToSystemYaml "$KEY_ELASTICSEARCH_PASSWORD" "$FLAG_NOT_APPLICABLE" "${SYS_KEY_SHARED_ELASTICSEARCH_PASSWORD}" || logSilly "Key: KEY_ELASTICSEARCH_PASSWORD not applicable"
    docker_installToSystemYaml "$KEY_RABBITMQ_ACTIVE_NODE_NAME" "$FLAG_NOT_APPLICABLE" "$SYS_KEY_RABBITMQ_ACTIVE_NODE_NAME" || logSilly "Key: KEY_RABBITMQ_ACTIVE_NODE_NAME not applicable"
    docker_installToSystemYaml "$KEY_RABBITMQ_ACTIVE_NODE_IP" "$FLAG_NOT_APPLICABLE" "$SYS_KEY_RABBITMQ_ACTIVE_NODE_IP" || logSilly "Key: KEY_RABBITMQ_ACTIVE_NODE_IP not applicable"
    docker_installToSystemYaml "$KEY_REDIS_PASSWORD" "$FLAG_NOT_APPLICABLE" "$SYS_KEY_SHARED_REDIS_PASSWORD" || logSilly "Key: KEY_REDIS_PASSWORD not applicable"
    docker_installToSystemYaml "$KEY_RABBITMQ_PASSWORD" "$FLAG_NOT_APPLICABLE" "$SYS_KEY_SHARED_RABBITMQ_PASSWORD" || logSilly "Key: KEY_RABBITMQ_PASSWORD not applicable"
    docker_installToSystemYaml "$KEY_PDNNODE_PDNSERVERURL" "$FLAG_NOT_APPLICABLE" "$SYS_KEY_PDNNODE_PDNSERVERURL" || logSilly "Key: KEY_PDNNODE_PDNSERVERURL not applicable"
    docker_installToSystemYaml "$KEY_PDNNODE_JOINKEY" "$FLAG_NOT_APPLICABLE" "$SYS_KEY_PDNNODE_JOINKEY" || logSilly "Key: KEY_PDNNODE_JOINKEY not applicable"
    docker_installToSystemYaml "$KEY_PDNNODE_SELFGRPCADDRESS" "$FLAG_NOT_APPLICABLE" "$SYS_KEY_PDNNODE_SELFGRPCADDRESS" || logSilly "Key: KEY_PDNNODE_SELFGRPCADDRESS not applicable"
    docker_installToSystemYaml "$KEY_PDNNODE_SELFHTTPADDRESS" "$FLAG_NOT_APPLICABLE" "$SYS_KEY_PDNNODE_SELFHTTPADDRESS" || logSilly "Key: KEY_PDNNODE_SELFHTTPADDRESS not applicable"
    docker_installToSystemYaml "$KEY_PDNNODE_PORT" "$FLAG_NOT_APPLICABLE" "$SYS_KEY_PDNNODE_PORT" || logSilly "Key: KEY_PDNNODE_PORT not applicable"
    docker_installToSystemYaml "$KEY_PDNNODE_HTTPPORT" "$FLAG_NOT_APPLICABLE" "$SYS_KEY_PDNNODE_HTTPPORT" || logSilly "Key: KEY_PDNNODE_HTTPPORT not applicable"
    docker_installToSystemYaml "$KEY_PDNNODE_HTTPBOUNCERPORT" "$FLAG_NOT_APPLICABLE" "$SYS_KEY_PDNNODE_HTTPBOUNCERPORT" || logSilly "Key: KEY_PDNNODE_HTTPBOUNCERPORT not applicable"
    docker_installToSystemYaml "$KEY_PDNSERVER_SELFADDRESS" "$FLAG_NOT_APPLICABLE" "$SYS_KEY_PDNSERVER_SELFADDRESS" || logSilly "Key: KEY_PDNSERVER_SELFADDRESS not applicable"
    docker_installToSystemYaml "$KEY_ROUTER_ENTRYPOINTS_EXTERNALPORT" "$FLAG_NOT_APPLICABLE" "$SYS_KEY_ROUTER_ENTRYPOINTS_EXTERNALPORT" || logSilly "Key: KEY_ROUTER_ENTRYPOINTS_EXTERNALPORT not applicable"

    docker_getSystemValue "${KEY_CLUSTER_MASTER_KEY}" "${FLAG_NOT_APPLICABLE}" "${INSTALLER_YAML}"
    local masterKeyValue="${YAML_VALUE}"
    local masterKeyFile="$JF_ROOT_DATA_DIR/var/etc/security/master.key"
    if [ ! -f "${masterKeyFile}" ] && [ ! -z "${masterKeyValue}" ]; then
        createRecursiveDir "$JF_ROOT_DATA_DIR" "var/etc/security" "${DOCKER_USER}" "${DOCKER_USER}"
        echo -n "${masterKeyValue}" > "${masterKeyFile}" || warn "Could not create ${masterKeyFile}"
        io_setOwnership "${masterKeyFile}" "${DOCKER_USER}" "${DOCKER_USER}" || warn "Setting ownership of [${masterKeyFile}] to [${DOCKER_USER}:${DOCKER_USER}] failed" 
    fi
    docker_addToEnvFile "ROOT_DATA_DIR" "$ROOT_DATA_DIR"
    docker_hook_postSystemYamlCreation
    io_setOwnership "${SYSTEM_YAML_FILE}" "$DOCKER_USER" "$DOCKER_USER"
}

docker_createInstallerStateYaml(){
    local stateIndicatorValue=""

    [ ! -z "${INSTALLER_STATE_YAML_FILE}" ] || return 0
    setInstallerStateKeys
    for stateIndicatorKey in ${INSTALLER_STATE_KEYS}; do
        eval stateIndicatorValue=\$${stateIndicatorKey}
        [ ! -z "${stateIndicatorValue}" ] || continue
        docker_installToSystemYaml "$stateIndicatorValue" "$FLAG_NOT_APPLICABLE" "$stateIndicatorValue" "NOT_SET" "${INSTALLER_STATE_YAML_FILE}" \
            || logSilly "Key: $stateIndicatorKey not applicable"
    done

    io_setOwnership "${INSTALLER_STATE_YAML_FILE}" "$DOCKER_USER" "$DOCKER_USER"
}

docker_setUpPostgresCompose() {
    SOURCE_FILE="${COMPOSE_TEMPLATES}/docker-compose-postgres.yaml"
    JFROG_POSTGRES_COMPOSE_FILE="${COMPOSE_HOME}/docker-compose-postgres.yaml"
    JFROG_POSTGRES_FILENAME="docker-compose-postgres.yaml"
    local postgresVer=$(echo "${JFROG_POSTGRES_13_9X_VERSION}" | tr '-' '.' )
    
    docker_getSystemValue "$KEY_POSTGRES_VERSION" "$FLAG_NOT_APPLICABLE" "${INSTALLER_YAML}"
    local installedPostgresVer=${YAML_VALUE}
    # set postgres source and target file names
    case "$installedPostgresVer" in
        $JFROG_POSTGRES_9X_VERSION)
            POSTGRES_OLD=$(echo "${JFROG_POSTGRES_9X_VERSION}" | tr '-' '.' )
            SOURCE_FILE="${COMPOSE_TEMPLATES}/docker-compose-postgres-${JFROG_POSTGRES_9X_VERSION}.yaml"
            JFROG_POSTGRES_COMPOSE_FILE="${COMPOSE_HOME}/docker-compose-postgres-${JFROG_POSTGRES_9X_VERSION}.yaml"
            JFROG_POSTGRES_FILENAME="docker-compose-postgres-${JFROG_POSTGRES_9X_VERSION}.yaml"
            askPostgresUpgrade
        ;;
        $JFROG_POSTGRES_10X_VERSION)
            POSTGRES_OLD=$(echo "${JFROG_POSTGRES_10X_VERSION}" | tr '-' '.' )
            SOURCE_FILE="${COMPOSE_TEMPLATES}/docker-compose-postgres-${JFROG_POSTGRES_10X_VERSION}.yaml"
            JFROG_POSTGRES_COMPOSE_FILE="${COMPOSE_HOME}/docker-compose-postgres-${JFROG_POSTGRES_10X_VERSION}.yaml"
            JFROG_POSTGRES_FILENAME="docker-compose-postgres-${JFROG_POSTGRES_10X_VERSION}.yaml"
            askPostgresUpgrade
        ;;
        $JFROG_POSTGRES_12_3X_VERSION)
            POSTGRES_OLD=$(echo "${JFROG_POSTGRES_12_3X_VERSION}" | tr '-' '.' )
            SOURCE_FILE="${COMPOSE_TEMPLATES}/docker-compose-postgres-${JFROG_POSTGRES_12_3X_VERSION}.yaml"
            JFROG_POSTGRES_COMPOSE_FILE="${COMPOSE_HOME}/docker-compose-postgres-${JFROG_POSTGRES_12_3X_VERSION}.yaml"
            JFROG_POSTGRES_FILENAME="docker-compose-postgres-${JFROG_POSTGRES_12_3X_VERSION}.yaml"
            askPostgresUpgrade
        ;;
        $JFROG_POSTGRES_12_5X_VERSION)
            POSTGRES_OLD=$(echo "${JFROG_POSTGRES_12_5X_VERSION}" | tr '-' '.' )
            SOURCE_FILE="${COMPOSE_TEMPLATES}/docker-compose-postgres-${JFROG_POSTGRES_12_5X_VERSION}.yaml"
            JFROG_POSTGRES_COMPOSE_FILE="${COMPOSE_HOME}/docker-compose-postgres-${JFROG_POSTGRES_12_5X_VERSION}.yaml"
            JFROG_POSTGRES_FILENAME="docker-compose-postgres-${JFROG_POSTGRES_12_5X_VERSION}.yaml"
            askPostgresUpgrade
        ;;
        $JFROG_POSTGRES_13_2X_VERSION)
            POSTGRES_OLD=$(echo "${JFROG_POSTGRES_13_2X_VERSION}" | tr '-' '.' )
            SOURCE_FILE="${COMPOSE_TEMPLATES}/docker-compose-postgres-${JFROG_POSTGRES_13_2X_VERSION}.yaml"
            JFROG_POSTGRES_COMPOSE_FILE="${COMPOSE_HOME}/docker-compose-postgres-${JFROG_POSTGRES_13_2X_VERSION}.yaml"
            JFROG_POSTGRES_FILENAME="docker-compose-postgres-${JFROG_POSTGRES_13_2X_VERSION}.yaml"
            askPostgresUpgrade
        ;;
        $JFROG_POSTGRES_13_4X_VERSION)
            POSTGRES_OLD=$(echo "${JFROG_POSTGRES_13_4X_VERSION}" | tr '-' '.' )
            SOURCE_FILE="${COMPOSE_TEMPLATES}/docker-compose-postgres-${JFROG_POSTGRES_13_4X_VERSION}.yaml"
            JFROG_POSTGRES_COMPOSE_FILE="${COMPOSE_HOME}/docker-compose-postgres-${JFROG_POSTGRES_13_4X_VERSION}.yaml"
            JFROG_POSTGRES_FILENAME="docker-compose-postgres-${JFROG_POSTGRES_13_4X_VERSION}.yaml"
            askPostgresUpgrade
    esac

    _getDatabaseType
    if [[ "$IS_POSTGRES_REQUIRED" == "$FLAG_Y" ]]; then
        if [[ ! -z "$DATABASE_TYPE" ]] && [[ "$DATABASE_TYPE" != "$SYS_KEY_SHARED_DATABASE_TYPE_VALUE_POSTGRES" ]]; then
            return
        fi
        cp -f "${SOURCE_FILE}" "${JFROG_POSTGRES_COMPOSE_FILE}" || warn "Could not copy [${SOURCE_FILE}] to [${JFROG_POSTGRES_COMPOSE_FILE}]"
        docker_getSystemValue "$SYS_KEY_SHARED_DATABASE_PASSWORD" "$FLAG_NOT_APPLICABLE" "$SYSTEM_YAML_FILE"
        POSTGRES_PASSWORD=${YAML_VALUE}
        if [[ ! -z "${POSTGRES_PASSWORD}" ]]; then
            replaceText_migration_hook "${REPLACE_POSTGRES_PASSWORD}" "${POSTGRES_PASSWORD}" "${JFROG_POSTGRES_COMPOSE_FILE}"
        fi

        # remove volume /etc/localtime in postgres-compose yaml, if running on mac (RT)
        local OS_TYPE=$(uname)
        if [[ "${OS_TYPE}" == "Darwin" && "${PRODUCT_NAME}" == "$ARTIFACTORY_LABEL" ]]; then
            removeYamlValue "services.postgres.volumes[1]" ${JFROG_POSTGRES_COMPOSE_FILE}
        fi
        # invoke to seed postgres db and credentials
        docker_StartPostgres
        # remove postgres password environment in compose file
        if [[ "${installedPostgresVer}" == "${JFROG_POSTGRES_9X_VERSION}" ]]; then
            removeYamlValue "services.postgres.environment[2]" ${JFROG_POSTGRES_COMPOSE_FILE}
        else
            replaceText_migration_hook "POSTGRES_PASSWORD=${POSTGRES_PASSWORD}" "POSTGRES_PASSWORD=${REPLACE_POSTGRES_PASSWORD}" "${JFROG_POSTGRES_COMPOSE_FILE}"
        fi
    fi
}

docker_postgresSpecificComposeHelp() {
    if [[ "${PRODUCT_NAME}" == "${PDN_NODE_LABEL}" || "${PRODUCT_NAME}" == "${PDN_SERVER_LABEL}" ]]; then
        return
    fi
    getYamlValue "$KEY_POSTGRES_INSTALL" "$INSTALLER_YAML" "false"
    local isPostgresExternalised=$([ "$YAML_VALUE" == "$FLAG_Y" ] && echo -n "$FLAG_N" || echo -n "$FLAG_Y")
    if [[ "$isPostgresExternalised" == "$FLAG_Y" ]]; then
        return
    fi
cat << END_USAGE
start postgresql:    docker-compose -p ${JF_PROJECT_NAME}-postgres -f ${JFROG_POSTGRES_FILENAME} up -d
stop  postgresql:    docker-compose -p ${JF_PROJECT_NAME}-postgres -f ${JFROG_POSTGRES_FILENAME} down
END_USAGE
}

_backupFile() {
    local sourceFile=$1
    local destinationFile=$2
    
    if [ -f "${sourceFile}" ]; then

        yes | \cp  -f "${sourceFile}" "${destinationFile}" || warn "Could not copy [${sourceFile}] to [${destinationFile}]"
        
        io_setOwnership "${destinationFile}" "${DOCKER_USER}" "${DOCKER_USER}" || warn "Setting ownership of [${destinationFile}] to [${DOCKER_USER}:${DOCKER_USER}] failed"
    fi
}

docker_cleanup () {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    wrapper_removeInstallerYaml
    _backupFile "${SCRIPT_HOME}/config.log" "${JF_ROOT_DATA_DIR}/var/log/config.log"
    _backupFile "${SCRIPT_HOME}/bin/migration.log" "${JF_ROOT_DATA_DIR}/var/log/migration.log"
    yes | \cp  -f "${SCRIPT_HOME}"/templates/system*.yaml  "${JF_ROOT_DATA_DIR}/var/etc/"

    local destinationFile="${JF_ROOT_DATA_DIR}/var/etc/system.basic-template.yaml"
    io_setOwnership "$destinationFile" "${DOCKER_USER}" "${DOCKER_USER}" || warn "Setting ownership of [${destinationFile}] to [${DOCKER_USER}:${DOCKER_USER}] failed"
    
    destinationFile="${JF_ROOT_DATA_DIR}/var/etc/system.full-template.yaml"
    io_setOwnership "$destinationFile" "${DOCKER_USER}" "${DOCKER_USER}" || warn "Setting ownership of [${destinationFile}] to [${DOCKER_USER}:${DOCKER_USER}] failed"
    _backupFile "${SCRIPT_HOME}/systemDiagnostics.log" "${JF_ROOT_DATA_DIR}/var/log/systemDiagnostics.log"
}


docker_isDatabaseExternalised() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    local typeKey="$1"
    local typeValue="$2"
    local urlKey="$3"

    IS_EXTERNAL_DB="$FLAG_N"

    # Get the db type configured
    docker_getSystemValue "$typeKey" "$FLAG_NOT_APPLICABLE" "$SYSTEM_YAML_FILE"

    # If it does not match what we expect, abort
    if [ "$typeValue" != "$YAML_VALUE" ]; then
        logDebug "typeMismatch. Aborting docker_isDatabaseExternalised as unnecessary"
        return
    fi

    # Since DB entry is present in the yaml, check the url
    docker_getSystemValue "$urlKey" "$FLAG_NOT_APPLICABLE" "$SYSTEM_YAML_FILE"
    local connectionURL=$YAML_VALUE

    # IF no connectionURL is present, abort
    if [ -z "$connectionURL" ]; then
        logDebug "no connection URL. Aborting docker_isDatabaseExternalised as unnecessary"
        return
    fi

    IS_LOCALHOST="$FLAG_N";

    # Check if the database connection url has the container name
    logDebug "searching for [//$dockerComposeKey:] in [$connectionURL]"
    io_hasMatch "$connectionURL" "//$dockerComposeKey:" && {
        logDebug "Found container name"
        IS_LOCALHOST="$FLAG_Y";
    } || logDebug "Did not find match for container name [$dockerComposeKey]"

    if [ "$IS_LOCALHOST" == "$FLAG_Y" ]; then
        logDebug "Is not external DB"
        return
    fi

    # Check if the database connection url points to this machine itself
    io_getIsLocalhost "$connectionURL"
    if [ "$IS_LOCALHOST" == "$FLAG_Y" ]; then
        logDebug "Is not external DB"
        return
    fi
    
    IS_EXTERNAL_DB="$FLAG_Y"
}

docker_postMigration_hook() {
    logSilly "Method ${FUNCNAME[0]}"
}

docker_triggerMigration () {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    if [[ "${PRODUCT_NAME}" == "$JFMC_LABEL" ]]; then
        export WRAPPER_SCRIPT_TYPE="DOCKERCOMPOSE"
    fi
    export JFROG_HOME="${JF_ROOT_DATA_DIR}"
    if [ "$MIGRATION_SUPPORTED" != "$FLAG_Y" ]; then
        logDebug "Skipping migration"
    elif [ -f "${SCRIPT_HOME}/bin/migrate.sh" ]; then
        logger "Triggering migration script. This will migrate if needed and may take some time."
        logger "Migration logs will be available at [${SCRIPT_HOME}/bin/migration.log]. The file will be archived at [${JF_ROOT_DATA_DIR}/var/log] after installation"
        export JF_USER="${DOCKER_USER}"
        if [ "$VERBOSE_MODE" == "debug" ]; then
            ${SCRIPT_HOME}/bin/migrate.sh || errorExit "Aborting configuration since migration has failed"
        else
            ${SCRIPT_HOME}/bin/migrate.sh >/dev/null || errorExit "Aborting configuration since migration has failed"
        fi
    else 
        logDebug "Skipping migration because file is missing"
    fi  

    docker_postMigration_hook
}

docker_createLogFile() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    redirectLogsToFile #creates variable DESTINATION_LOG_FILE
    LOG_FILE="${SCRIPT_HOME}/config.log"
    exec 6>>$LOG_FILE
    exec &> >(tee -a "$LOG_FILE")
    setFilePermission "$LOG_FILE_PERMISSION" "$LOG_FILE"
}

# Utility method to copy the logrotate binary to the appropriate folder
_addLogRotateBinary() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    local productHome="$1"
    
    # Only Linux supported at the moment
    if [ $(uname) != "Linux" ]; then
        return
    fi

    # Make sure the binary exists in the installer
    local sourceLogRotateBinary="${COMPOSE_HOME}/third-party/logrotate/logrotate"
    [[ $(uname -m) == "arm64" ]] && sourceLogRotateBinary="${COMPOSE_HOME}/third-party/logrotate/logrotate_arm64" || true

    # TODO We can remove this block after all the code is restructured.
    if [ ! -f "$sourceLogRotateBinary" ]; then
        if [[ $(uname -m) == "arm64" ]]; then
            sourceLogRotateBinary="${SCRIPT_HOME}/third-party/logrotate/logrotate_arm64"
        else
            sourceLogRotateBinary="${SCRIPT_HOME}/third-party/logrotate/logrotate_amd64"
        fi
    fi
    [ -f "$sourceLogRotateBinary" ] || { logDebug "Could not find the file: [$sourceLogRotateBinary]"; }

    # Create the target directory & move the logrotate binary to it
    mkdir -p "$productHome/app/third-party/logrotate"
    cp "$sourceLogRotateBinary" "$productHome/app/third-party/logrotate/logrotate" || return 1

    # At this point the file should be owned by root which is good since the cron job is also owned by root
}

docker_StartPostgres() {
    logger "Attempting to seed $POSTGRES_LABEL. This may take some time."
    pushd $COMPOSE_HOME > /dev/null
    # Start postgresql container
    docker-compose -f ${JFROG_POSTGRES_COMPOSE_FILE} -p ${JF_PROJECT_NAME}-postgres up -d >&6 2>&1 ||  errorExit "Failed to start $POSTGRES_LABEL. Please check the config.log file for additional details"
    if [[ "${PRODUCT_NAME}" == "$JFMC_LABEL" ]]; then
        seedPostgresDataJfmc
    fi
    popd > /dev/null
    logger "Successfully seeded $POSTGRES_LABEL"
}

seedPostgresDataJfmc() {
    local n=1
    local max=3
    local delay=2
    local SUCCESS=false
    # Copy postgres setup script
    [ -f "${THIRDPARTY_HOME}/postgresql/createPostgresUsers.sh" ] || errorExit "Could not find the following file : ${THIRDPARTY_HOME}/postgresql/createPostgresUsers.sh ,this is used to create postgres database, users and schema"
    while [[ $n -lt $max && "$SUCCESS" == "false" ]] ; do
        docker cp ${THIRDPARTY_HOME}/postgresql/createPostgresUsers.sh ${JF_PROJECT_NAME}_postgres:/  && SUCCESS=true || SUCCESS=false
        ((n++))
        [[ $n -lt $max && "$SUCCESS" == "false" ]] && logger "Retrying copy of ${THIRDPARTY_HOME}/postgresql/createPostgresUsers.sh to $POSTGRES_LABEL container" || true
        sleep $delay;
    done
    if [[ "$SUCCESS" == "true" ]]; then
        logDebug "Successfully copied $POSTGRES_LABEL setup script"
      else
        errorExit "Failed to copy $POSTGRES_LABEL setup script"
    fi
    
    # Run postgres setup script inside postgresql container as postgres user
    docker_getSystemValue "$KEY_POSTGRES_VERSION" "$FLAG_NOT_APPLICABLE" "$INSTALLER_YAML"
    local installedPostgresVer=${YAML_VALUE}
    if [[ "${installedPostgresVer}" == "${JFROG_POSTGRES_9X_VERSION}" ]]; then
      docker exec -t ${JF_PROJECT_NAME}_postgres bash -c "export DB_PASSWORD=${POSTGRES_PASSWORD} && chmod +x /createPostgresUsers.sh && gosu postgres /createPostgresUsers.sh"  || errorExit "Failed to create $POSTGRES_LABEL default users"
    else
      docker exec -t ${JF_PROJECT_NAME}_postgres bash -c "export DB_PASSWORD=${POSTGRES_PASSWORD} && chmod +x /createPostgresUsers.sh && su-exec postgres /createPostgresUsers.sh"  || errorExit "Failed to create $POSTGRES_LABEL default users"
    fi

}

docker_configureLogRotation() {
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    _addLogRotateBinary "$JF_ROOT_DATA_DIR" || true # Intentional. We want to still try and setup logrotate
    configureLogRotation "${PROJECT_ROOT_FOLDER}" "$JF_ROOT_DATA_DIR" "$DOCKER_USER" "$DOCKER_USER" "$(id -un)" || true
}

# This hook can be used to query for any product & docker-specific questions
docker_hook_setInstallerDefaults() {
    logSilly "Method ${FUNCNAME[0]}"
}

# This hook can be used to query for any docker-specific questions
wrapper_hook_setInstallerDefaults() {
  logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
  local dataRootFolder=${PROJECT_ROOT_FOLDER:-"$JF_PROJECT_NAME"}
  wrapper_setSystemValue "$KEY_DATA_DIR_LOCATION" "$HOME/.jfrog/$dataRootFolder" "${FLAG_N}"
  docker_hook_setInstallerDefaults
}

wrapper_hook_start_getUserInputs(){
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"
    if [ ! -z "$JF_ROOT_DATA_DIR" ]; then
        logger "$PROMPT_DATA_DIR_LOCATION found in .env. Skipping prompt"
        wrapper_setSystemValue "$KEY_DATA_DIR_LOCATION" "$JF_ROOT_DATA_DIR" "${FLAG_Y}"
    else
        wrapper_askUser "DATA_DIR_LOCATION"
    fi
    docker_getSystemValue "$KEY_DATA_DIR_LOCATION" "JF_ROOT_DATA_DIR" "$INSTALLER_YAML"
    JF_ROOT_DATA_DIR=${YAML_VALUE:-"$JF_ROOT_DATA_DIR"}
    export JF_ROOT_DATA_DIR="$JF_ROOT_DATA_DIR"
    logDebug "Method: wrapper_hook_start_getUserInputs. JF_ROOT_DATA_DIR is now [$JF_ROOT_DATA_DIR]"
    # validate system yaml
    SYSTEM_DIAGNOSTICS_LOG_FILE="${COMPOSE_HOME}/systemDiagnostics.log"
    touch "${SYSTEM_DIAGNOSTICS_LOG_FILE}"
    local OS_TYPE=$(uname)
    local osType=$(uname -m)
    local diagnosticBinary=
    if [[ "${PRODUCT_NAME}" == "$ARTIFACTORY_LABEL" ]]; then
        if [[ "${OS_TYPE}" == "Darwin" ]]; then
            wrapper_validateSystemYaml "${JF_ROOT_DATA_DIR}/var/etc/${FILE_NAME_SYSTEM_YAML}" "${COMPOSE_HOME}/templates/${FILE_NAME_SYSTEM_YAML_TEMPLATE}" "${COMPOSE_HOME}/bin/diagnostics/diagnosticsUtil_darwin"
            runSystemDiagnosticsChecks "${JF_ROOT_DATA_DIR}" "${COMPOSE_HOME}/bin/diagnostics/diagnosticsUtil_darwin" "${COMPOSE_HOME}/bin/diagnostics/diagnostics.yaml"
        elif [[ "${osType}" == "arm64" ||  "${osType}" == "aarch64" ]]; then
            wrapper_validateSystemYaml "${JF_ROOT_DATA_DIR}/var/etc/${FILE_NAME_SYSTEM_YAML}" "${COMPOSE_HOME}/templates/${FILE_NAME_SYSTEM_YAML_TEMPLATE}" "${COMPOSE_HOME}/bin/diagnostics/diagnosticsUtil_linux_arm64"
            runSystemDiagnosticsChecks "${JF_ROOT_DATA_DIR}" "${COMPOSE_HOME}/bin/diagnostics/diagnosticsUtil_linux_arm64" "${COMPOSE_HOME}/bin/diagnostics/diagnostics.yaml"
        else
            wrapper_validateSystemYaml "${JF_ROOT_DATA_DIR}/var/etc/${FILE_NAME_SYSTEM_YAML}" "${COMPOSE_HOME}/templates/${FILE_NAME_SYSTEM_YAML_TEMPLATE}" "${COMPOSE_HOME}/bin/diagnostics/diagnosticsUtil_linux"
            runSystemDiagnosticsChecks "${JF_ROOT_DATA_DIR}" "${COMPOSE_HOME}/bin/diagnostics/diagnosticsUtil_linux" "${COMPOSE_HOME}/bin/diagnostics/diagnostics.yaml"
        fi
    else
        if [[ "${OS_TYPE}" != "Darwin" ]]; then
            if [[ "${osType}" == "arm64" ||  "${osType}" == "aarch64" ]]; then
                diagnosticBinary=${COMPOSE_HOME}/bin/diagnostics/diagnosticsUtil_arm64
            else
                diagnosticBinary=${COMPOSE_HOME}/bin/diagnostics/diagnosticsUtil
            fi
            wrapper_validateSystemYaml "${JF_ROOT_DATA_DIR}/var/etc/${FILE_NAME_SYSTEM_YAML}" "${COMPOSE_HOME}/templates/${FILE_NAME_SYSTEM_YAML_TEMPLATE}" "${diagnosticBinary}"
            runSystemDiagnosticsChecks "${JF_ROOT_DATA_DIR}" "${diagnosticBinary}" "${COMPOSE_HOME}/bin/diagnostics/diagnostics.yaml"
        fi
    fi
    docker_triggerMigration
    _pauseExecution "docker_triggerMigration"
}

wrapper_hook_askExtraRabbitMQNodeDetails(){
    _updateFromSystemYaml "RABBITMQ_ACTIVE_NODE_IP" 
    [ -z "$YAML_VALUE" ] && wrapper_askUser "RABBITMQ_ACTIVE_NODE_IP" || logger "Found $RABBITMQ_LABEL active node IP from system.yaml. Skipping prompt"
}

docker_hook_getUserInputs_post() {
    logSilly "Method ${FUNCNAME[0]}"
}

docker_ha_hook_getUserInputs(){
    logSilly "Method ${FUNCNAME[0]}"
}

wrapper_ha_hook_getUserInputs(){
    docker_ha_hook_getUserInputs
}

docker_hook_preUserInputs(){
    logSilly "Method ${FUNCNAME[0]}"    
}

docker_getUserInputs(){
    logDebug "Method ${FUNCNAME[0]} Caller:$(caller)"

    io_setYQPath

    wrapper_getUserInputs

    # At this point the installer.yaml will be available. Use it to set runtime variables

    docker_getSystemValue "$KEY_DATA_DIR_LOCATION" "JF_ROOT_DATA_DIR" "$INSTALLER_YAML"
    JF_ROOT_DATA_DIR=${YAML_VALUE:-"$JF_ROOT_DATA_DIR"}
    export JF_ROOT_DATA_DIR="$JF_ROOT_DATA_DIR"
    ROOT_DATA_DIR="$JF_ROOT_DATA_DIR"
    export ROOT_DATA_DIR="$ROOT_DATA_DIR"
    logDebug "Method: docker_getUserInputs. JF_ROOT_DATA_DIR is now [$JF_ROOT_DATA_DIR]"

    docker_getSystemValue "$KEY_ADD_TO_CLUSTER" "$FLAG_NOT_APPLICABLE" "$INSTALLER_YAML"
    if [ -z "$YAML_VALUE" ]; then
        JF_NODE_TYPE="$NODE_TYPE_STANDALONE"
    elif [ "$YAML_VALUE" == "$FLAG_N" ]; then
        JF_NODE_TYPE="$NODE_TYPE_STANDALONE"
    else
        JF_NODE_TYPE="$NODE_TYPE_CLUSTER_NODE"
    fi
    logDebug "Method: docker_getUserInputs. JF_NODE_TYPE is now [$JF_NODE_TYPE]"

    docker_getSystemValue "$KEY_JFROGURL" "JF_SHARED_JFROGURL" "$INSTALLER_YAML"
    JF_SHARED_JFROGURL=${YAML_VALUE:-"$JF_SHARED_JFROGURL"}
    logDebug "Method: docker_getUserInputs. JF_SHARED_JFROGURL is now [$JF_SHARED_JFROGURL]"

    docker_getSystemValue "$KEY_PDNNODE_PDNSERVERURL" "JF_PDNNODE_PDNSERVERURL" "$INSTALLER_YAML"
    JF_PDNNODE_PDNSERVERURL=${YAML_VALUE:-"$JF_PDNNODE_PDNSERVERURL"}
    logDebug "Method: docker_getUserInputs. JF_PDNNODE_PDNSERVERURL is now [$JF_PDNNODE_PDNSERVERURL]"

    docker_getSystemValue "$KEY_JOIN_KEY" "JF_SHARED_SECURITY_JOINKEY" "$INSTALLER_YAML"
    JF_SHARED_SECURITY_JOINKEY=${YAML_VALUE:-"$JF_SHARED_SECURITY_JOINKEY"}
    logDebug "Method: docker_getUserInputs. JF_SHARED_SECURITY_JOINKEY is now [$JF_SHARED_SECURITY_JOINKEY]"

    docker_getSystemValue "$KEY_POSTGRES_INSTALL" "IS_POSTGRES_REQUIRED" "$INSTALLER_YAML"
    IS_POSTGRES_REQUIRED=${YAML_VALUE:-"$IS_POSTGRES_REQUIRED"}
    logDebug "Method: docker_getUserInputs. IS_POSTGRES_REQUIRED is now [$IS_POSTGRES_REQUIRED]"

    docker_getSystemValue "$KEY_HOST_IP" "JF_SHARED_NODE_IP" "$INSTALLER_YAML"
    JF_SHARED_NODE_IP=${YAML_VALUE:-"$JF_SHARED_NODE_IP"}
    logDebug "Method: docker_getUserInputs. JF_SHARED_NODE_IP is now [$JF_SHARED_NODE_IP]"
    
    docker_hook_getUserInputs_post
}

_invoke3rdPartyHook() {
    local mode="$1"
    if [ -z "$THIRD_PARTY_SCRIPT" ]; then
        logDebug "No third party integration configured. Skipping invocation of 3rd party hook"
        return
    fi
    if [ ! -f "$THIRD_PARTY_SCRIPT" ]; then
        errorExit "Aborting configuration since third party integration configured but file [$THIRD_PARTY_SCRIPT] was not found"
    fi
    logDebug "Invoking [$THIRD_PARTY_SCRIPT]  with mode [$mode]"
    $THIRD_PARTY_SCRIPT "$mode" || errorExit "Aborting configuration since third party integration script failed with errors"
}

docker_setInstallerStateFile(){
    local rootDir="$1"
    [ ! -z "${rootDir}" ] && INSTALLER_STATE_YAML_FILE="${rootDir}/var/etc/${FILE_NAME_INSTALLER_STATE_YAML}" || true
}

docker_setMaxOpenfiles() {
    if [[ "${PRODUCT_NAME}" == "${XRAY_LABEL}" ]]; then
        local noFileVal=$(ulimit -n)
        local minNoFileMax=100000
        if [[ "$noFileVal" != "unlimited" ]] && [[ $noFileVal -lt $minNoFileMax ]]; then
            if [[ -f "/etc/security/limits.conf" ]]; then
                addLine "root hard nofile 100000" "/etc/security/limits.conf"
                addLine "root soft nofile 100000" "/etc/security/limits.conf"
            fi
            [[ -f "/etc/pam.d/common-session" ]] && addLine "session required pam_limits.so" "/etc/pam.d/common-session"
        fi
    fi
}

docker_main() {
    LOG_BEHAVIOR_ADD_NEW_LINE="$FLAG_Y"
    setPostgresVersion
    # For backward compatibility. JF_ROOT_DATA_DIR is being renamed to ROOT_DATA_DIR
    JF_ROOT_DATA_DIR=${JF_ROOT_DATA_DIR:-"$ROOT_DATA_DIR"}
    export JF_ROOT_DATA_DIR="$JF_ROOT_DATA_DIR"
    INSTALLER_YAML=${INSTALLER_YAML:-"${SCRIPT_HOME}/${FILE_NAME_INSTALLER_YAML}"}

    docker_setInstallerStateFile "${JF_ROOT_DATA_DIR}"

    io_setYQPath
    
    docker_createLogFile
    _pauseExecution "docker_createLogFile"
    
    bannerStart "Beginning JFrog ${PRODUCT_NAME} setup"
    
    docker_setMaxOpenfiles

    #Perform validations
    docker_doValidations
    _pauseExecution "docker_doValidations"
    
    #Parse the flags passed in and handle them
    docker_processOptions $*
    _pauseExecution "docker_processOptions"

    docker_hook_preUserInputs
    _pauseExecution "docker_hook_preUserInputs"

    docker_getUserInputs
    _pauseExecution "docker_getUserInputs"

    _invoke3rdPartyHook "input"

    docker_updateFromYaml
    _pauseExecution "docker_updateFromYaml"

    # At this point, we know what the user wants. Set up variables
    docker_setupModeSpecificVar    

    #Determine the folders to create    
    docker_setDirInfo
    _pauseExecution "docker_setDirInfo"

    #Create all the directories necessary
    docker_createDirectories
    _pauseExecution "docker_createDirectories"

    #setup permissions on the root folder
    docker_setRootDirPermissions
    _pauseExecution "docker_setRootDirPermissions"

    #create Third party directories
    docker_createThirdPartyDir
    _pauseExecution "docker_createThirdPartyDir"

    wrapper_setPostgresVersion
    _pauseExecution "wrapper_setPostgresVersion"
    
    docker_createSystemYaml
    _pauseExecution "docker_createSystemYaml"

    docker_createInstallerStateYaml
    _pauseExecution "docker_createInstallerStateYaml"

    docker_hook_copyComposeFile

    #Bring up and bootstrap any third-party databases. 
    #NOTE: It is important that this step happens after a system.yaml has been created. It reads the file.
    docker_hook_setupThirdParty
    _pauseExecution "docker_hook_setupThirdParty"

    _invoke3rdPartyHook "execute"

    docker_configureLogRotation
    _pauseExecution "docker_configureLogRotation"

    _invoke3rdPartyHook "compose"
    
    #only start the application if system.yaml is available
    docker_showSuccessMessage
    _pauseExecution "docker_showSuccessMessage"

    #backup the env file
    docker_cleanup

    bannerStart "Done"

    exit 0
}
## Docker-compose specific functions - End

