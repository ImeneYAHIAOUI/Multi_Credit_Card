# Livrable DevOps team F

## 1. Procédure pour construire / reconstruire la chaine DevOps

En cas de problème sur la VM, il faut vérifier l'état des différentes images Docker. 
Pour cela, il faut se connecter à la VM et lancer la commande suivante :

```bash
ssh teamf@vmpx06.polytech.unice.fr
docker ps -a
```

En fonction de l'état, il faut suivre les instructions du fichier : [README.md](./../config-docker-vm/README.md)

## 2. Status du livrable ur l'aspect DevOps
    
### 2.1. Fonctionnalités implémentées

#### Docker

Pour faciliter l'isolation et la portabilité des applications, permettre un déploiement rapide et facile, faciliter la gestion des versions, améliorer la sécurité et faciliter la collaboration en équipe.

#### Jenkins

Pour permettre d'automatiser le processus d'intégration et de déploiement, faciliter l'intégration avec d'autres outils DevOps, offrir une grande flexibilité de personnalisation, améliorer la qualité du code, et faciliter la collaboration en équipe.

#### Artifactory

Pour permettre de stocker et de gérer les artefacts, offrir une réplication et une distribution avancée, fournir des fonctionnalités de sécurité avancées, et permettre de gérer les versions des artefacts.


#### SonarQube

Ce service va être lancé par Jenkins lors de la phase de test. Il va permettre de vérifier la qualité du code.
Pour fournir une analyse approfondie et continue du code, aider à gérer la dette technique, faciliter la collaboration en équipe et permettre de détecter les problèmes de qualité du code dès qu'ils se produisent.


### 2.2. Comment accéder aux services DevOps

Une fois connecté au VPN de l'Université, il est possible d'accéder aux services DevOps en suivant les liens ci-dessous :

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
        <td>DevOps#vmpx06</td>
    </tr>
</tbody>
</table>

## 3. Expliquation des choix techniques

### 3.1. Docker

Nous avons configuré notre application pour qu'elle tourne dans des conteneurs séparés à l’aide d’un fichier [docker-compose.yml](./../docker-compose.yml) pour configurer la communication entre les différents services sur un réseau Docker isolé de la machine hôte. 

Docker nous a également servi pour configurer et ordonner différents services open-source que nous avons récupérés depuis Docker Hub, ce qui a facilité grandement l'installation de Jenkins, SonarQube et Artifactory. 
Cette approche nous a permis de résoudre les problèmes de manière isolée en ne touchant que les conteneurs, plutôt que d'impacter toute la machine hôte sur laquelle les services tournent. 
Ces différents conteneurs sont créés à partir d'un fichier [docker-compose.yml](./../config-docker-vm/docker-compose.yml).

De plus, Docker nous permet de publier des images facilement via Docker Hub. 
Par ce biais, les images Docker construites de notre projet sont ensuite déployées et peuvent être récupérées par tout le monde via une commande docker search.

### 3.2. Jenkins

Nous avons configuré Jenkins pour qu’il détecte automatiquement des changements sur notre projet centralisé sur GitHub, peu importe la branche sur laquelle il y a eu le changement via des webhooks. 
La configuration de notre machine Hôte hébergeant nos services DevOps bloquant les webhooks de GitHub avec son firewall, nous avons donc mis en place une application web qui se nomme Smee.io permettant de créer un tunnel sécurisé entre GitHub et Jenkins. 
Grâce à Smee.io, nos webhooks ont pu contourner les règles du firewall de la machine hôte. 
Nous faisons tourner Smee.io en continu en arrière-plan de notre machine hôte, nous avons en conséquence un uptime de 100% pour la détection des commits de GitHub, ce qui nous permet de lancer notre stratégie de CI/CD à tout moment.

Sur Jenkins nous avons mis en place deux agents pour déléguer ses tâches. 
Pour que Jenkins communique avec ses agents, nous utilisons des connexions SSH pour créer des tunnels privés et sécurisés entre deux parties. 
L'un d'entre eux est une instance de conteneur Docker basée sur une image d’un Docker SSH Agent. 
Elle s’occupe de toutes les tâches liées à des langages de programmation qui dans notre cas sont Java, Maven, NodeJs et npm.

L'autre agent est la machine hôte sur laquelle le service conteneurisé Jenkins tourne. 
Il est fortement déconseillé dans la majorité des cas de connecter en SSH un conteneur Docker à sa machine hôte pour des raisons de sécurité. 
Avec le partage du noyau du système d'exploitation hôte, vous pourriez potentiellement accéder à des fichiers et des ressources système sensibles sur la machine hôte. 
Cela peut également donner à l'attaquant un accès illimité à la machine hôte et à toutes les données qu'elle contient si un attaquant parvient à compromettre votre conteneur Docker. 
Pourtant, nous devions exécuter Docker au sein de Jenkins qui se trouve lui-même dans un Docker. 
Ce principe est connu sous le nom “Docker-in-Docker” (DinD) et est à éviter, principalement pour une question de performance et de ressources. 
Nous avons donc opté pour utiliser la connexion SSH où Docker est déjà configuré.

Nous avons ensuite configuré une Multi-branch déclarative Pipeline qui récupère le code via GitHub avec un Token d’authentification qui est stocké dans le Credential Manager de Jenkins. 
Dans notre projet, nous avons un fichier : [Jenkinsfile](./../Jenkinsfile) qui permet de décrire les étapes qu’on veut que Jenkins réalise. 
Nous avons mis en place une stratégie de CI/CD qui se déroule en 6 étapes : 

  1. Build
  2. Test
  3. Tests end-to-end
  4. Code analysis (si on est sur la branche master)
  5. Package (si on est sur la branche master)
  6. Deploy (si on est sur la branche master)

### 3.3. SonarQube

Nous utilisons SonarQube Scanner dans le but d'avoir des statistiques et un retour sur la qualité du code que nous développons. 
Nous avons généré un token d'authentification que l’on stocke dans le Credentials Manager de Jenkins lui permettant d’appeler SonarQube automatiquement et de manière autonome pour commencer l’analyse. 
Nous avons configuré Maven dans le [pom.xml du backend](./../backend/pom.xml) et le [pom.xml de la CLI](./../cli/pom.xml) pour qu’il puisse aussi générer des tests de coverage. 

Lorsque SonarQube Scanner est lancé, il nous donne des métriques telles que le nombre de bugs, les différents codes smells, le coverage et les duplications de code. 
Ces métriques nous permettent d’améliorer notre code. 
Nous aurions pu mettre en place une Quality Gate pour stopper la [pipeline](./../Jenkinsfile) en cours sur Jenkins, mais nous l’utilisons comme aide à la qualité de code et non comme un prérequis pour valider un commit, donc nous ne l’avons pas mis en place.

### 3.4. Artifactory

Nous utilisons Artifactory pour centraliser tous nos artefacts, et comme pour SonarQube, nous générons un token qu’on stocke aussi dans Jenkins. 

Dans notre [pipeline](./../Jenkinsfile), nous appelons artifactory lors de la phase de Package. 
En fonction de la branche, nous publions sur le Repository Snapshot ou Release. 

Nous ne réalisons pas encore les artefacts publiés sur Artifactory pour créer des images Docker, car l’agent qui s’en occupe a déjà les mêmes artefacts localement, mais il nous suffirait d’une requête cURL avec une authentification pour les récupérer.
