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




# Transform given key to an environment variable name
#   shared.database.username => JF_SHARED_DATABASE_USERNAME
transformKeyToEnv(){
    local key=$1
    local prefix=${2:-"JF_"}

    [ ! -z "$key" ] || return

    # replace dot and underscore with underscore
    key=${key//./_}
    key=${key//-/_}

    # convert all lowercase alphabets to upper case
    key=$(echo "${key}" | tr '[:lower:]' '[:upper:]' 2>/dev/null)

    # Add prefix if its not already added
    [[ "$key" =~ ^${prefix}.* ]] || key="${prefix}${key}"
    echo $key
}


#
# Yaml helper which can perform actions on a yaml file . A key will be searched in the order of files provided in JF_SYSTEM_YAML
# The highest priority goes to environment variables
#   If a key is parent.child, the equivalent environment variable is PARENT_CHILD

resolutionlogger(){
    local key=${1}
    local yamlValue=${2}
    local valueSource=${3}
    
    if [ -z "${key}" ] || [ -z "${yamlValue}" ] || [ -z "${valueSource}" ]; then
        return
    fi

    isKeySensitive "${key}" && yamlValue="$SENSITIVE_KEY_VALUE" || yamlValue=${yamlValue}
    logger "resolved ${key} (${yamlValue}) from ${valueSource}"
}

#######################################
# Set path to yq binary
# Globals:
#   YQ_PATH
#   YQ
# Arguments:
#   None
# Returns:
#   None
#######################################
initYQ(){

    if [[ -z "${YQ_PATH}" ]]; then
        io_setYQPath
    fi

    [[ ! -z "${YQ_PATH}" ]] || errorExit "YQ_PATH is not set, script requires a \$YQ_PATH/yq to work with system.yaml"

    local os_type=$(uname)
    local os_arch=$(uname -m)
    local os_yq_name=
    [ "${os_type}" = "Darwin" ] && osYqName=yq_darwin || osYqName=yq_linux

    if [[ "${os_type}" == "Darwin" ]]; then
        osYqName=yq_darwin
    elif [[ "${os_arch}" == "arm64" || "${os_arch}" == "aarch64" ]]; then
        osYqName=yq_linux_arm64
    else
        osYqName=yq_linux
    fi

    if [ -f "${YQ_PATH}"/"${osYqName}" ]; then
        YQ="${YQ_PATH}"/"${osYqName}"
    elif [ -f "${YQ_PATH}"/yq ]; then
        YQ="${YQ_PATH}"/yq
    else
        errorExit "yq is not found in provided YQ_PATH ($YQ_PATH)"
    fi

    [ -x "${YQ}" ] || chmod +x "${YQ}" >/dev/null 2>&1 || warn "Unable to change permission of yaml parser : ${YQ}"

    # check if yq is working
    if ! "${YQ}" -V >/dev/null 2>&1; then
        warn "Unable to execute yaml parser \"${YQ} -V\", the executable might be corrupted or might not be compatible on the current operating system. (yq is used by installer to work with system.yaml)"
    fi
}

#######################################
# Append dot to key path if not exists - for yq 4x
#######################################
appendDotToKeyPath(){
    local key=$1
    if [[ ${key:0:1} == "." ]] ; then 
      echo $key; 
    else 
      echo "."$key; 
    fi
}


#######################################
# Get value from a yaml file
# Globals:
#   YAML_VALUE
#   YQ
# Arguments:
#   key           - key to be searched
#   yamlFile      - target yaml file
#   logResolution - logs a message about the file which had this key set
# Returns:
#   None
#######################################
getYamlValue(){
    initYQ
    local key=$1
    local yamlFile=$2
    local logResolution=${3:-true}

    [ -z "${key}"      ] && errorExit "a key is mandatory to retrieve value from yaml"  || true
    [ -z "${yamlFile}" ] && errorExit "yaml file is mandatory to retrieve value"        || true

    # set yaml value to undefined if yaml file is not found
    if [[ ! -f "${yamlFile}" || ! -s "${yamlFile}" ]]; then
        YAML_VALUE=
        return
    fi
    key=$(appendDotToKeyPath "$key")
    if "${YQ}" e "${key}" "${yamlFile}" >/dev/null 2>&1; then
        YAML_VALUE=$("${YQ}" e "${key}" "${yamlFile}"  2>/dev/null)
    else
        YAML_VALUE=
        return
    fi

    if [[ "${YAML_VALUE}" == "null" ]]; then
        YAML_VALUE=
    fi

    if [[ ${logResolution} == true && ! -z "${YAML_VALUE}" ]]; then
        resolutionlogger "${key}" "${YAML_VALUE}" "${yamlFile}"
    fi
}


#######################################
# Remove key from a yaml file
# Globals:
#   YQ
# Arguments:
#   key           - key to be removed
#   yamlFile      - target yaml file
# Returns:
#   None
#######################################
removeYamlValue(){
    initYQ
    local key=$1
    local yamlFile=${2:-${JF_SYSTEM_YAML}}

    [ -z "${key}" ] && errorExit "a key is mandatory to remove key from yaml" || true

    if [[ ! -f "${yamlFile}" || ! -s "${yamlFile}" ]]; then
        return
    fi
    key=$(appendDotToKeyPath "$key")
    "${YQ}" eval -i "del(${key})" "${yamlFile}" 2>/dev/null
}

#######################################
# Perform a yaml merge
# Globals:
#   YQ
# Arguments:
#   initialYamlFile           - Original file
#   additionalYamlFile        - File to be appended with the original file
# Returns:
#   None
#######################################
yamlMerge(){
    initYQ
    local initialYamlFile="${1}"
    local additionalYamlFile="${2}"
    ${YQ} eval-all -i "select(fileIndex == 0) * select(filename == \"${additionalYamlFile}\")" "${initialYamlFile}" "${additionalYamlFile}" > /dev/null || errorExit "Failed to merge ${additionalYamlFile} to  ${initialYamlFile}"
}

#######################################
# Get equivalent environment variable of yaml key
#   If a key is parent.child, the equivalent environment variable is PARENT_CHILD
# Globals:
#   YAML_VALUE
# Arguments:
#   key           - key to be searched
#   logResolution - logs a message if key was set as an environment variable
# Returns:
#   None
#######################################
getENVValue(){
    local key=$1
    local logResolution=${2:-true}

    [ -z "${key}" ] && errorExit "a key is mandatory to retrieve value from environment" || true

    key=$(transformKeyToEnv "$key")

    YAML_VALUE="${!key}"

    if [[ ${logResolution} == true && ! -z "${YAML_VALUE}" ]]; then
        resolutionlogger "${key}" "${YAML_VALUE}" "environment variable"
    fi
}

#######################################
# Get value from a system yaml file
#   Set JF_SYSTEM_YAML with the list of files to be parsed through
# Globals:
#   YAML_VALUE
#   YQ
# Arguments:
#   key           - key to be searched
#   default       - a default value to set if none of the files have this key
#   logResolution - logs a message about the file which had this key set
# Returns:
#   None
#######################################
getSystemValue(){
    initYQ
    local key=$1
    local default=${2:-""}
    local logResolution=${3:-true}

    if [ ! -z "${4}" ]; then
        local systemYamlList=${4}
    else
        systemYamlList=${JF_SYSTEM_YAML:-"${JF_PRODUCT_HOME}/var/etc/system.yaml"}
    fi

    [ -z "${key}" ] && errorExit "a key is mandatory to retrieve value from yaml"  || true

    YAML_VALUE=

    # check if this key is set as an environment variable
    getENVValue "${key}" "${logResolution}"
    [ ! -z "${YAML_VALUE}" ] && return || true

    # check if the key is set in any of the yaml files
    for yamlFile in ${systemYamlList}
    do
        getYamlValue "${key}" "${yamlFile}" "${logResolution}"
        [ ! -z "${YAML_VALUE}" ] && return || true
    done

    if [ -z "${YAML_VALUE}" ]; then
        if [ ! -z "${default}" ]; then
            YAML_VALUE="${default}"
        else
            errorExit "${key} is not set in : ${systemYamlList}"
        fi
    fi
}

#######################################
# Get value from a system yaml file
#   Optionally set JF_SYSTEM_YAML with the target system.yaml to be updated
# Globals:
#   YAML_VALUE
#   YQ
# Arguments:
#   key           - key to be modified
#   value         - value to be set
#   yamlFile      - target system yaml file to be modified
# Returns:
#   None
#######################################
setSystemValue(){
    initYQ
    local path=$1
    local value=$2
    local ymlFile=${3:-${JF_SYSTEM_YAML}}
    local option=${4:-""}
    local displayValue=

    [ -z "${path}"    ] && errorExit "a path is mandatory to modify system yaml"                || true
    [ -z "${value}"   ] && errorExit "a value is mandatory to update a path in system yaml"     || true
    [ -z "${ymlFile}" ] && errorExit "a yaml file is mandatory to update a path in system yaml" || true

    isKeySensitive "${path}" && displayValue="$SENSITIVE_KEY_VALUE" || displayValue=${value}

    logDebug "setting ${path} with ${displayValue} in ${ymlFile}"
    path=$(appendDotToKeyPath "$path")
    if [[ ! -f "${ymlFile}" || ! -s "${ymlFile}" ]]; then
        if [[ $value == *"\""* ]]; then
            value=$value ${YQ} e -n ${option} "${path} = strenv(value)" > "${ymlFile}" || errorExit "failed to set create ${path} with ${displayValue} in ${yamlFile}, make sure its parent directory is already create and $USER user has permission to create this file under it,\ncommand used : ${YQ} e -n ${option} \"${path} = \"${value}\"\" > \"${ymlFile}\""
        else
            value=$value ${YQ} e -n ${option} "${path} = env(value)" > "${ymlFile}" || errorExit "failed to set create ${path} with ${displayValue} in ${yamlFile}, make sure its parent directory is already create and $USER user has permission to create this file under it,\ncommand used : ${YQ} e -n ${option} \"${path} = \"${value}\"\" > \"${ymlFile}\""
        fi
    else
        if [[ $value == *"\""* ]]; then
            value=$value ${YQ} eval -i ${option} "${path} = strenv(value)" "${ymlFile}" || errorExit "failed to set create ${path} with ${displayValue} in ${yamlFile}, \ncommand used : ${YQ} eval -i ${option} \"${path} = \"${value}\"\" \"${ymlFile}\""
        else
            value=$value ${YQ} eval -i ${option} "${path} = env(value)" "${ymlFile}" || errorExit "failed to set create ${path} with ${displayValue} in ${yamlFile}, \ncommand used : ${YQ} eval -i ${option} \"${path} = \"${value}\"\" \"${ymlFile}\""
        fi
    fi
}

setSystemValueNoOverride(){
    local key=$1
    local value=$2
    local yamlFile=$3
    local logMsg=${4:-false}

    # Return if any of the inputs are empty
    [[ -z "$key"      || "$key"      == "" ]] && return
    [[ -z "$value"    || "$value"    == "" ]] && return
    [[ -z "$yamlFile" || "$yamlFile" == "" ]] && return

    getSystemValue "${key}" "NOT_SET" "${logMsg}" "${yamlFile}"

    if [ ${YAML_VALUE} == "NOT_SET" ]; then
        setSystemValue "${key}" "${value}" "${yamlFile}"
    else
        [ "${logMsg}" == "true" ] && logger "Skipping update of property : ${key}" || true
    fi
}

#######################################
# Resolve any system keys in a file to its respective values from system.yaml
# Syntax of key should be of the following format ${parent.child},
# A default value can be set by using || as field separator
# Example : ${artifactory.port}
#           ${node.ip||127.0.0.1}
# Globals:
#   YAML_VALUE
# Arguments:
#   target  - location to target file with keys (example /opt/jfrog/artifactory/server.xml)
# Returns:
#   None
#######################################
resolveSystemKeys(){
    local target=$1
    local keyList=
    local nestedResolutionCount=${2:-1}
    local maxNestedResolutionCount=5
    local squareBracketsKey="_SQUARE_BRACKETS_"
    local squareBracketsReplaceValue='\[\]'
    local value=
    local defaultValue=
    local emptyKeyWord=${EMPTY_KEY_WORD:-_EMPTY_}
    local keyOrig=
    local displayValue=

    [ -z "${target}" ] && errorExit "a target file is mandatory to resolve keys from system.yaml"  || true
    [ -f "${target}" ] || errorExit "target file (${target}) not found"

    # regex supports the following examples,
    #   test.name
    #   test_name
    #   artifacts.[0].name
    #   test.test.test
    #   parent.child||sample_default_value
    local keyRegex="[a-z]*[A-Z]*[0-9]*[_\-]*\[*\]*\**\.*|*\/*"

    # support upto 32 levels of special characters - test.test.test-test.test.test_test...test
    for i in $(seq 1 5); do keyRegex="${keyRegex}${keyRegex}"; done

    keyList=$(cat "${target}" | grep -o "\${${keyRegex}}")

    if [ -z "${keyList}" ]; then
        warn "No keys to resolve in ${target}"
    fi

    for key in ${keyList}; do
        defaultValue=
        if [ ! -z "${key}" ]; then
            keyOrig="${key}"
            # convert ${key} => key
            key=${key#"\${"}
            key=${key%"}"}

            # Check if a defaut value is defined
            # parent.child||sample_default
            #   defaultValue=sample_default
            #   key=parent.child
            if [[ "${key}" = *"||"* ]]; then
                defaultValue=${key#*||}
                key=${key%||*}
            fi

            getSystemValue "${key}" "${defaultValue}"
            value="${YAML_VALUE}"

            if [[ "${value}" == "${emptyKeyWord}" ]]; then
                value=""
            fi

            isKeySensitive "${key}" && displayValue="$SENSITIVE_KEY_VALUE" || displayValue=${value}

            if [[ "${value}" == "${defaultValue}" ]]; then
                logger "resolved ${keyOrig} to default value : ${displayValue}"
            fi

            # Handle resolution of multiline string value; sed does not handle newline charaters in value
            value=$(echo "${value}" | tr -d "\n")

            if [[ "${keyOrig}" == *"relaxedPathChars"* ]] || [[ "${keyOrig}" == *"relaxedQueryChars"* ]]; then
                value=$(escapeSpecialChars "${value}")
            fi

            if [[ $(uname) == "Darwin" ]]; then
                sed -i '' -e "s,\\${keyOrig//,/\\,},${value//,/\\,},g" "${target}" || errorExit "resolving ${keyOrig} to ${displayValue} failed, command used : sed -i '' -e \"s,\\${keyOrig},${displayValue},g\" ${target}"
            else
                sed -i -e "s,\\${keyOrig//,/\\,},${value//,/\\,},g" "${target}" || errorExit "resolving ${keyOrig} to ${displayValue} failed, command used : sed -i -e \"s,\\${keyOrig},${displayValue},g\" ${target}"
            fi
        fi
    done

    keyList=$(cat "${target}" | grep -o "\${${keyRegex}}")

    if [ ! -z "${keyList}" ]; then
        if [[ ${nestedResolutionCount} -lt 5 ]]; then
            nestedResolutionCount=$(($nestedResolutionCount + 1))
            resolveSystemKeys "${target}" ${nestedResolutionCount}
        else
            local errMsg="The following list of variables ${keyList} could not be resolved after 5 tries in ${target}, this could happen \
if there were some invalid characters configured in ${JF_PRODUCT_HOME}/var/etc/system.yaml or \
if there was some kind of corruption in ${target} template"
             errorExit "${errMsg}"
        fi
    fi

    if cat "${target}" | grep "${squareBracketsKey}" >/dev/null 2>&1; then
        if [[ $(uname) == "Darwin" ]]; then
            sed -i '' -e "s,${squareBracketsKey},${squareBracketsReplaceValue},g" "${target}" || true
        else
            sed -i -e "s,${squareBracketsKey},${squareBracketsReplaceValue},g" "${target}" || true
        fi
    fi
}

escapeSpecialChars(){
    local value="$1"
    if [[ "${value}" =~ .*[\^\&\;\[\]|\{\}].* ]]; then
        [[ "$value" =~ .*\^.* ]] && value="$(sed -e 's/\^/\\\^/' <<<"$value")" || true
        [[ "$value" =~ .*\&.* ]] && value="$(sed -e 's/\&/\\\&/' <<<"$value")" || true
        [[ "$value" =~ .*\[.* ]] && value="$(sed -e 's/\[/\\\[/' <<<"$value")" || true
        [[ "$value" =~ .*\].* ]] && value="$(sed -e 's/\]/\\\]/' <<<"$value")" || true
        [[ "$value" =~ .*|.*  ]] && value="$(sed -e 's/|/\\|/' <<<"$value")"   || true
        [[ "$value" =~ .*\{.* ]] && value="$(sed -e 's/{/\\{/' <<<"$value")"   || true
        [[ "$value" =~ .*\{.* ]] && value="$(sed -e 's/}/\\}/' <<<"$value")"   || true
        [[ "$value" =~ .*\;.* ]] && value="$(sed -e 's/\;/\\\;/' <<<"$value")" || true
    fi
    regex="^\".*\"$"
    if [[ "$value" =~ $regex ]]; then
        value=$(sed -e 's/^"//' -e 's/"$//' <<<"$value")
    fi
    echo "${value}"
}

