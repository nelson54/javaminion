FROM jenkins/jenkins:lts
USER root
RUN echo "deb http://ppa.launchpad.net/ansible/ansible/ubuntu trusty main" >> /etc/apt/sources.list
RUN apt-key adv --keyserver keyserver.ubuntu.com --recv-keys 93C4A3FD7BB9C367
RUN apt update

RUN curl -sL https://deb.nodesource.com/setup_11.x | bash -

RUN apt install ansible vim htop -y


USER jenkins