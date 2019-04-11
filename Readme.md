# Meh

[![Build Status](https://travis-ci.org/nelson54/javaminion.svg?branch=master)](https://travis-ci.org/nelson54/javaminion)
[![Sputnik](https://sputnik.ci/conf/badge)](https://sputnik.ci/app#builds/nelson54/javaminion)

## Todo
- [ ] Add i18n (https://g00glen00b.be/spring-internationalization-i18n/)
- [x] Calculate winning score.
- [x] JWT Authentication 
- [ ] Matchmaking
    - [x] Create game page
    - [x] Display open games
    - [ ] Join game
    - [x] Join AI game
    - [x] Show Completed games
    - [ ] Show wins and losses
- [ ] Animations
- [x] Moat
    - [x] Add Attack Bypass to turn
- [ ] Implement discard preferences for AI or reverse buy preferences   
- [x] End turn if no additional actions in pool or cards in hand after playing ComplexActionCard's
- [x] Update and save PM2 configuration every time a build is done.
- [x] Prevent from rebuilding database every time
- [x] Test leaving and coming back to games.

Cards Working
- [x] Cellar **Working**
- [x] Chapel **Working**
- [x] Moat **Working**
- [x] Chancellor **Working**
- [x] Woodcutter **Working**
- [x] Village **Working**
- [x] Workshop **Working**
- [ ] Bureaucrat **Untested**
- [x] Gardens **Working**
- [x] Militia **Working**
- [x] Moneylender **Working**
- [x] Feast **Working**
- [ ] Spy **Untested**
- [x] Thief **Working**
- [x] Remodel **Working**
- [x] Smithy **Working**
- [x] Throne Room **Untested**
- [ ] Council Room **Untested** 
- [x] Festival **Working**
- [x] Laboratory **Working**
- [ ] Library **Untested**
- [x] Market **Working**
- [x] Mine **Working**
- [x] Witch **Working**
- [ ] Adventurer **Untested**

#For port forwarding to jenkins server:
```bash
ssh -L 8080:142.93.14.62:8080 -L 8081:142.93.14.62:8081 -L 8082:142.93.14.62:8082 root@142.93.14.62
```

```bash
cd javaminion

./setup-environment.sh

docker-compose build

docker-compose up

```