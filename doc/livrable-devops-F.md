# Livrable DevOps team F

## 1. Procédure pour construire / reconstruire la chaine DevOps

En cas de problème sur la VM, il faut vérifier l'état des différentes images Docker. 
Pour cela, il faut se connecter à la VM et lancer la commande suivante :

```bash
ssh teamf@vmpx06.polytech.unice.fr
docker ps -a
```

En fonction de l'état, il faut suivre les instruction du fichier : [README.md](./../config-docker-vm/README.md)

## 2. Status du livrable ur l'aspect DevOps
    
### 2.1. Fonctionnalités implémentées

#### Jenkins

L'utilisation de ce service va nous permettre deux choses : 
- Lancer les tests unitaires et les tests d'intégration. 
- Lancer la création d'un package .jar et le déployer sur Artifactory.


#### Artifactory

#### SonarQube

Ce service va être lancer par Jenkins lors de la phase de test. Il va permettre de vérifier la qualité du code.


### 2.2. How to access to the DevOps services

Une fois connecter au VPN de l'Université, il est possible d'accéder aux services DevOps en suivant les liens ci-dessous :

[//]: # (table of links)
<table>
<thead>
    <tr style="font-size: 2em">
        <th><strong>Logo</strong></th>
        <th><strong>Service</strong></th>
        <th><strong>Link</strong></th>
        <th><strong>Username</strong></th>
        <th><strong>Password</strong></th>
    </tr>
</thead>
<tbody style="text-align: center">
    <tr>
        <td><img src="https://www.jenkins.io/images/logos/jenkins/jenkins.svg" width="50" alt="Jenkins' Logo"></td>
        <td><strong>Jenkins</strong></td>
        <td><a>http://vmpx06.polytech.unice.fr:8000</a></td>
        <td>DevOps</td>
        <td>DevOps</td>
    </tr>
    <tr>
        <td><img src="https://wiki.eclipse.org/images/8/88/Sonarqube.png" width="200" alt="SonarQube's Logo"></td>
        <td><strong>SonarQube</strong></td>
        <td><a>http://vmpx06.polytech.unice.fr:8001</a></td>
        <td>admin</td>
        <td>DevOps</td>
    </tr>
    <tr>
        <td><img src="https://access.redhat.com/hydra/cwe/rest/v1.0/public/products/66406/logo" width="150" alt="Artifactory's Logo"></td>
        <td><strong>Artifactory</strong></td>
        <td><a>http://vmpx06.polytech.unice.fr:8002</a></td>
        <td>admin</td>
        <td>DevOpsvmpx06</td>
    </tr>
</tbody>
</table>

### 2.3. Expliquation des choix techniques