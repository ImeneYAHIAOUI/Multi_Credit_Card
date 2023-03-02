# Configuration et installation de Docker sur une VM

## Prérequis
Avoir mis a jour la VM avec les dernières mises à jour

```sudo apt update && sudo apt upgrade```

## Installation de git
Dans un premier temps, il faut vérifier que git est installé sur la machine.

```git --version ```

Si ce n'est pas le cas, il faut l'installer.

```sudo apt install git```

Une fois installé, il faut cloner le repository git.

```git clone https://github.com/pns-isa-devops/isa-devops-22-23-team-f-23```

## Installation des applications nécessaires : Docker et Docker-compose

Une fois le projet clonné, il faut installer Docker et Docker-compose.
Pour ce faire, il suffit de se déplacer dans le projet et de lancer le script : 

```cd ~/isa-devops-22-23-team-f-23/config-docker-vm/```

```sh ./instal-docker.sh```



