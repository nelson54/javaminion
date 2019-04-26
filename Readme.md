# Meh

[![Build Status](https://travis-ci.org/nelson54/javaminion.svg?branch=master)](https://travis-ci.org/nelson54/javaminion)
[![Sputnik](https://sputnik.ci/conf/badge)](https://sputnik.ci/app#builds/nelson54/javaminion)

## Todo
- [ ] Remodel is cancellable but goes into a broken state.
- [ ] Removing keys.
- [ ] Tie Breaks.
- [ ] Show number of cards in opponents hand.
- [ ] Show opponents discard pile.

## Backlog
- [ ] Show contents of deck after game.
- [ ] Add i18n/messages (https://g00glen00b.be/spring-internationalization-i18n/)
- [ ] Implement discard preferences for AI or reverse buy preferences
- [ ] Replay - Animations
- [ ] Replay - Add copy button to play a game from a state against AI players.
- [ ] Don't allow players to watch games while IN_PROGRESS.
- [ ] Add card lookup [https://github.com/cypressf/dominion/blob/master/cards.json]

## Done
- [x] Allow spectating
- [x] Adding Elo
- [x] Join game
- [x] Shuffle turn order
- [x] Create game page
- [x] Display open games
- [x] Join AI game
- [x] Show Completed games
- [x] JWT Authentication
- [x] End Games
- [x] Store winners in database
- [x] Show score for finished games
- [x] End turn if no additional actions in pool or cards in hand after playing ComplexActionCard
- [x] Update and save PM2 configuration every time a build is done
- [x] Prevent from rebuilding database every time
- [x] Test leaving and coming back to games
- [x] Add domain name
- [x] Add game log to show which card Bureaucrat puts on deck
- [x] Adding finish timestamp
- [x] Fixed ELO from being recalculated whenever you view a completed game page
- [x] Setup configurable logback.xml

## Cards 
- [ ] Library **Untested**
- [ ] Adventurer **Untested**

- [x] Bureaucrat **Testing**
- [x] Cellar **Testing**
- [x] Chapel **Testing**
- [x] Moat **Testing**
- [x] Chancellor **Testing**
- [x] Woodcutter **Testing**
- [x] Village **Testing**
- [x] Workshop **Testing**
- [x] Gardens **Testing**
- [x] Militia **Testing**
- [x] Moneylender **Testing**
- [x] Feast **Testing**
- [x] Spy **Testing**
- [x] Thief **Testing**
- [x] Remodel **Testing**
- [x] Smithy **Testing**
- [x] Throne Room **Testing**
- [x] Council Room **Testing** 
- [x] Festival **Testing**
- [x] Laboratory **Testing**
- [x] Market **Testing**
- [x] Mine **Testing**
- [x] Witch **Testing**


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
