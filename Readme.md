# Meh

[![Build Status](https://travis-ci.org/nelson54/javaminion.svg?branch=master)](https://travis-ci.org/nelson54/javaminion)
[![Sputnik](https://sputnik.ci/conf/badge)](https://sputnik.ci/app#builds/nelson54/javaminion)

## Todo
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
- [x] Add game log to show which card Bureaucrat puts on deck.
- [x] Adding finish timestamp
- [x] Fixed ELO from being recalculated whenever you view a completed game page.

## Cards 
- [ ] Library **Untested**
- [ ] Adventurer **Untested**

- [x] Bureaucrat **Working**
- [x] Cellar **Working**
- [x] Chapel **Working**
- [x] Moat **Working**
- [x] Chancellor **Working**
- [x] Woodcutter **Working**
- [x] Village **Working**
- [x] Workshop **Working**
- [x] Gardens **Working**
- [x] Militia **Working**
- [x] Moneylender **Working**
- [x] Feast **Working**
- [x] Spy **Working**
- [x] Thief **Working**
- [x] Remodel **Working**
- [x] Smithy **Working**
- [x] Throne Room **Working**
- [x] Council Room **Working** 
- [x] Festival **Working**
- [x] Laboratory **Working**
- [x] Market **Working**
- [x] Mine **Working**
- [x] Witch **Working**


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