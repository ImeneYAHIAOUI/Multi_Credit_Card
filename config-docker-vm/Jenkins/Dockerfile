FROM jenkins/jenkins:lts
LABEL name="Jenkins Java&Node LTS"

USER root
RUN apt update -y

RUN apt install openjdk-17-jdk -y
RUN curl -fsSL https://deb.nodesource.com/setup_18.x | bash - && apt install nodejs -y

USER jenkins