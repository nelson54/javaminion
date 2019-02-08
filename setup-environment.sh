#!/bin/bash

app="/root/javaminion"

apt update -y
apt remove docker docker-engine docker.io containerd runc -y

ufw enable
ufw allow 80/tcp
ufw allow 80/udp

curl https://raw.githubusercontent.com/nelson54/snippets/master/.git_profile > /root/.bashrc
curl https://raw.githubusercontent.com/nelson54/snippets/master/.vimrc > /root/.vimrc
curl https://raw.githubusercontent.com/nelson54/snippets/master/.tmux.config > /root/.tmux.config

apt install nginx htop tmux vim curl git apt-transport-https ca-certificates curl gnupg-agent software-properties-common -y

curl -fsSL https://download.docker.com/linux/ubuntu/gpg | apt-key add -

apt-key fingerprint 0EBFCD88

add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"

apt update -y

apt install docker-ce docker-ce-cli containerd.io -y

cd $app

mkdir $app/logs

cp config/nginx.conf /etc/nginx/nginx.conf

systemctl nginx restart

docker-compose build
