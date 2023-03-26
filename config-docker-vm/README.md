# Configuration and installation of DevOps tools on a VM

## Prerequisites

- Update the VM with the latest updates

```
sudo apt update -y && sudo apt upgrade -y
```

## Installation of mandatory applications

### Installation of git and clone Repository

1. Check if **git** is installed on the machine

```
git --version
```

2. If not installed, install **git**

```
sudo apt install git
```

3. Clone the **git** repository

```
git clone https://github.com/pns-isa-devops/isa-devops-22-23-team-f-23
```

### Installation of Docker and Docker-compose

- Install **Docker** and **Docker-compose** with the script

```
sh ~/isa-devops-22-23-team-f-23/config-docker-vm/instal-docker.sh
```

### Installation of npm and smee

1. Install **npm**:

```
sudo apt install npm -y
```

2. Install **smee**:

```
sudo npm install --global smee-client
```

## Launch DevOps services

1. Listen to **GitHub's webhook**: *Has to be run in a separate terminal*

```
smee --url https://smee.io/kdAtZfJcRLvTu18 --path /github-webhook/ --port 8000
```

2. Launch **Jenkins** and **SonarQube**: *from the **config-docker-vm** directory*

```
docker-compose up -d
```

3. Launch **Artifactory**: *from the **config-docker-vm/Aritfactory** directory*

```
docker-compose up -d
```

4. Access to **Jenkins**, **SonarQube** and **Artifactory**:

[//]: # (table of links)
<table>
<thead>
    <tr style="font-size: 2em">
        <th><strong>Logo</strong></th>
        <th><strong>Service</strong></th>
        <th><strong>Link</strong></th>
    </tr>
</thead>
<tbody style="text-align: center">
    <tr>
        <td><img src="https://www.jenkins.io/images/logos/jenkins/jenkins.svg" width="50" alt="Jenkins' Logo"></td>
        <td><strong>Jenkins</strong></td>
        <td><a>http://vmpx06.polytech.unice.fr:8000</a></td>
    </tr>
    <tr>
        <td><img src="https://wiki.eclipse.org/images/8/88/Sonarqube.png" width="200" alt="SonarQube's Logo"></td>
        <td><strong>SonarQube</strong></td>
        <td><a>http://vmpx06.polytech.unice.fr:8001</a></td>
    </tr>
    <tr>
        <td><img src="https://access.redhat.com/hydra/cwe/rest/v1.0/public/products/66406/logo" width="150" alt="Artifactory's Logo"></td>
        <td><strong>Artifactory</strong></td>
        <td><a>http://vmpx06.polytech.unice.fr:8002</a></td>
    </tr>
</tbody>
</table>

## Configuration of Services

*possibility to change any configuration but must also be changed in the Jenkinsfile adequately*

### Jenkins

1. Install **Jenkins** recommended plugins
2. Create a new **Jenkins** admin user 
3. Add **GitHub** Credentials: *Manage Jenkins > Manage Credentials*
   - **GitHub**
4. Create and Configure a new **Jenkins** job
5. Download **Jenkins** plugins
   - **Blue Ocean** *(optional)*
   - **Docker** *(any related plugins)*
   - **Git** *(any related plugins)*
   - **GitHub** *(any related plugins)*
   - **Maven Integration**
   - **NodeJS**
   - **SonarQube Scanner**
   - **Artifactory**
6. Reboot **Jenkins** to apply the changes
7. Configure **Tool Configuration**: *Manage Jenkins > Global Tool Configuration*
   1. **JDK**
      - Name: **JDK17**
      - JAVA_HOME: **/usr/lib/jvm/java-17-openjdk-amd64**
      - Install automatically: **un-checked**
   2. **SonarQube Scanner**
        - Name: **SQS4.8**
        - Install automatically: **checked**
        - Version: **4.8.0**
   3. **Maven**
      - Name: **Maven 3.9.0**
      - Install automatically: **checked**
      - Version: **3.9.0**
   4. **NodeJS**
        - Name: **NodeJS18**
        - Install automatically: **checked**
        - Version: **18.14.2**
   5. **Save** the configuration
8. Add **Sonarqube** and **Artifactory** Credentials: *Manage Jenkins > Manage Credentials*
   - **SonarQube**
   - **Artifactory**
9. Configure **System**: *Manage Jenkins > Configure System*
   1. **SonarQube servers**
       - Name: **DevOpsSonarQube**
       - Server URL: **http://sonarqube:9000**
       - Server authentication token: **SonarQube**
   2. **JFrog**
         - Instance ID: **DevOpsArtifactory**
         - JFrog Platform URL: **http://artifactory:8002/artifactory**
         - Artifactory Credentials: **Artifactory**
    3. **Save** the configuration
10. Setup **Agent**: *Manage Jenkins > Manage Nodes and Clouds > New Node*
     - Name: **Docker Agent**
     - Remote File System Root: **/home/jenkins/agent**
     - Launch Method: **Launch agents via SSH**
     - Host: **jenkins-agent**
     - Credentials: **add private ssh key**
     - **Save** the configuration
     - Add .ssh/know_hosts to the **Jenkins** container's home directory

### SonarQube

### Artifactory