# Mise en place du repo
sudo apt install -y \ ca-certificates \ curl \ gnupg \ lsb-release
sudo mkdir -m 0755 -p /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

# Installation de docker
sudo apt install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

# Instalation de docker-compose
sudo apt install -y docker-compose

# Pouvoir lancer les commandes docker sans à avoir à mettre sudo
sudo groupadd docker
sudo usermod -aG docker $USER
newgrp docker

sudo apt update -y && sudo apt upgrade -y

