# Meh

[![Build Status](https://travis-ci.org/nelson54/javaminion.svg?branch=master)](https://travis-ci.org/nelson54/javaminion)
[![Sputnik](https://sputnik.ci/conf/badge)](https://sputnik.ci/app#builds/nelson54/javaminion)

## Refactor plans:
* game
    * game.cards
    * game.commands
    * game.players
    * game.service
* match
* user

## Todo
- [ ] Don't buy cards immediately, zoom and show text.
- [ ] Resign (UI, server code is finished).
- [ ] Show opponents discard pile.

### Movement Log
- [ ] Gains hand
- [ ] Gains discard
- [ ] Gains deck
- [ ] Discard

### Animations
- [x] Allow to replay up until a point
- [ ] Move Card
- [ ] Clean up
- [ ] Draw Hand

### Error messages
- [ ] Add Angular service for showing error messages
- [ ] Login Failed
- [ ] Sign Up
- [ ] Incorrect Phase
- [ ] Insufficient Funds
- [ ] Insufficient Buys
- [ ] Already played
- [ ] Not Implemented
- [ ] Unable to create game exception

## Backlog
- [ ] Add custom card selection.
- [ ] Show contents of decks after game.
- [ ] Add i18n/messages (https://g00glen00b.be/spring-internationalization-i18n/)
- [ ] Implement discard preferences for AI or reverse buy preferences
- [ ] Replay - Add copy button to play a game from a state against AI players.
- [ ] Don't allow players to watch games while IN_PROGRESS.
- [ ] Add card lookup [https://github.com/cypressf/dominion/blob/master/cards.json]
- [ ] Move from Jenkins to GitLab CI

## Done
- [x] Prevent from playing cards not currently owned.
- [x] Tie Breaks.
- [x] Separate running AI code from matchService.
- [x] Show number of cards in opponents hand.
- [x] Adding HTTPS with certbot.
- [x] Remodel is cancellable but goes into a broken state.
- [x] Removing keys.
- [x] Add Random card set
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
- [x] Remodel is cancellable but goes into a broken state.
- [x] Removing keys.

### Adding Basic user stats
- [x] Wins
- [x] Losses
- [x] Rank
- [x] Card Preferences

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

