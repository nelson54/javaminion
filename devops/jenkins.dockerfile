FROM jenkins/jenkins:lts


RUN chmod 600 ~/.ssh -R
RUN echo "deb http://ppa.launchpad.net/ansible/ansible/ubuntu trusty main" >> /etc/apt/sources.list
RUN apt update
RUN apt install software-properties-common
RUN apt-add-repository ppa:ansible/ansible -y
RUN apt update
RUN apt install ansible vim

