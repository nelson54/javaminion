# Meh

[![Build Status](https://travis-ci.org/nelson54/javaminion.svg?branch=master)](https://travis-ci.org/nelson54/javaminion)
[![Sputnik](https://sputnik.ci/conf/badge)](https://sputnik.ci/app#builds/nelson54/javaminion)

## Todo
- [x] JWT Authentication 
- [ ] Matchmaking
    - [x] Create game page
    - [x] Display open games
    - [ ] Join game
    - [x] Join AI game
    - [ ] Show Completed games
    - [ ] Show wins and losses
- [x] Better logging for Discard     
- [x] Thief reveals non-treasure cards
- [x] Cellar should remove the cards from your hand after you mark them for being discarded
- [ ] Animations
- [ ] Moat
    - [ ] Add onAttackEvent
    - [ ] Add Attack Bypass to turn
    - [ ] Add onCardEntersHand Event
- [ ] Implement discard preferences for AI    
- [x] End turn if no additional actions in pool or cards in hand after playing ComplexActionCard's

#For port forwarding to jenkins server:
```ssh -L 8080:142.93.14.62:8080 -L 8081:142.93.14.62:8081 -L 8082:142.93.14.62:8082 root@142.93.14.62```

## Starting Jenkins
```bash
docker run --name jenkins -p 8080:8080 -p 50000:50000 -v $HOME/jenkins_home:/var/jenkins_home jenkins/jenkins:lts && \
docker run --name nexus -d -p 8081:8081 sonatype/nexus3 && \ 
docker run --name nagios4 -p 8082:80 jasonrivers/nagios:latest

```

```bash
cd javaminion

./setup-environment.sh

docker-compose build

docker-compose up

```