FROM jenkins/ssh-agent
LABEL name="Jenkins-Agent"

USER root
RUN apt update -y

RUN apt install git -y

RUN apt install openjdk-17-jdk -y
RUN curl -fsSL https://deb.nodesource.com/setup_18.x | bash - && apt install nodejs -y

ENV JENKINS_AGENT_SSH_PUBKEY="ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQCbmVpOMZh7BUeyp2m5kXQ8R9w/Jfh/8Mcp98IIjtnzRBfMnOr1chTgi/vqen1PyneGmyt2AhGMjF6hKAZM3KplkLNfVhFNrpvqtAiZ1ClDAjqrp1y6LjABSGNbPsfecOJ7ULekwj0ep4rvPvCEe58qzjRhLo58zne7fNEAHdrXPukIGIEJ6sBYjJUOcl/NV0c+bws+3baDSrkUR0OE4/tRchHoi0eHcA7UixPrEMdog/uU4gQJqYf6g2csbOIGx2LeUi75U59P4AHl+diKKOAa82ZGdv8NZurp17meyhtIAR1awh8l7amZNLLNQl0M22+P2klUUDMk4hUQMGKtA7bovBBFvRmTKaULQpwQTFfBYhKE0tmLbfHv9dPltQTAed6Y5nnQyFlt5rdO3pONnhn4DFjyDo6paV9arT3UzOvaECDDKFFFcWHqiTaqR93rTAKkCqI3YYsa3eQxwtCHpqtM66v8PqjDSKilycRlz/ysYZqhe+IY/eCCm257av/8hJs= teamf@vmpx06"