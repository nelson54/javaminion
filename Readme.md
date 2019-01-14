# Meh

[![Build Status](https://travis-ci.org/nelson54/javaminion.svg?branch=master)](https://travis-ci.org/nelson54/javaminion)
[![Sputnik](https://sputnik.ci/conf/badge)](https://sputnik.ci/app#builds/nelson54/javaminion)

## Todo
- [ ] Authentication.
- [ ] Matchmaking.
    - [ ] Create game page
    - [ ] Display open games
    - [ ] Join game
    - [ ] Create game
- [x] Thief reveals non-treasure cards.
- [x] Cellar should remove the cards from your hand after you mark them for being discarded.
- [ ] Remodel and Mine are both broken.
- [ ] Animations.
- [x] End turn if no additional actions in pool or cards in hand after playing ComplexActionCard's


```bash
# Clean
docker system prune

# Builds
docker build -t javaminion .

# Runs
docker run javaminion

# Enter bash
docker run -it javaminion bash

# Run from bash
./gradlew build && java -Xmx512m -jar ./build/libs/gs-spring-boot-docker-0.1.0.jar

```