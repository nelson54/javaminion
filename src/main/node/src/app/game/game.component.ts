import { Component, Input, OnInit, ViewEncapsulation } from '@angular/core';

import * as moment from 'moment';
import { GameService } from '@app/shared/game.service';
import { MessageService } from '@app/shared/message.service';
import { Message } from '@app/shared/game/message.interface';
import { UserService } from '@app/shared/user.service';
import { Turn } from '@app/shared/game/turn.interface';
import { Card } from '@app/shared/game/card.interface';
import { Game } from '@app/shared/game/game.interface';
import { Player } from '@app/shared/game/player.interface';
import { Choice } from '@app/shared/game/choice.interface';
import { Subscription } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { Market } from '@app/shared/game/market.interface';
import { CredentialsService } from '@app/core';
import { ShowMainNavService } from '@app/shared/ShowMainNav.service';

interface Players {
  [key: string]: Player;
}

@Component({
  selector: 'game-play',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.scss']
})
export class GameComponent implements OnInit {
  showLogs = false;
  title = 'Loading game';

  gameId: string;

  @Input() game: Game;
  @Input() playerId: string;

  turn: any;

  kingdomCards: Market;
  commonCards: Market;

  player: Player;
  players: Players;

  @Input() deck: Card[];
  @Input() hand: Card[];
  @Input() discard: Card[];

  @Input() choice: Choice;
  @Input() play: Card[];
  @Input() hashcode: number;
  @Input() interval: number;
  @Input() isWatching: boolean;
  @Input() playBack: boolean;

  @Input() playBackTurn = 0;
  @Input() playBackPointer: any;
  @Input() cardDetails: any;

  @Input() shownMessages: any;
  @Input() logs: Message[];

  loopSubscription: Subscription;

  constructor(
    private route: ActivatedRoute,
    private gameService: GameService,
    private userService: UserService,
    private messageService: MessageService,
    private credentialsService: CredentialsService,
    public showMainNav: ShowMainNavService
  ) {
    showMainNav.show = false;

    if (credentialsService.isAuthenticated()) {
      this.playerId = credentialsService.credentials.id;
    }
  }

  showLogWindow() {
    this.showLogs = true;
  }

  hideLogWindow() {
    this.showLogs = false;
  }

  ngOnInit() {
    this.isWatching = false;

    this.gameId = this.route.snapshot.params.id;

    this.startInterval();
  }

  playerIds() {
    if (this.players != null) {
      return Object.keys(this.players);
    } else {
      return [];
    }
  }

  playBackGame() {
    this.playBack = true;
  }

  // playTurn(turn: Turn, i: number) {
  //   const playTurn = this.game.pastTurns[i];

  //   if (!playTurn || (playTurn.play && playTurn.play.length < i)) {
  //     this.playBackTurn++;
  //     this.playBackPointer = 0;
  //   } else if (playTurn.play && playTurn.play.length) {
  //     this.play.append(playTurn.play[i]);
  //   }
  // }

  showCardDetails(card: string) {
    this.cardDetails = card;
  }

  canAfford(card: Card) {
    return this.player && this.turn.money >= card.cost.money;
  }

  purchase(card: Card) {
    this.gameService.purchase(this.game, card).subscribe();
  }

  playCard(card: Card) {
    this.gameService.play(this.game, card).subscribe();
  }

  nextPhase() {
    this.gameService.endPhase(this.game).subscribe();
  }

  selectCard(card: Card) {
    if (this.turn.phase === 'ACTION') {
      this.playCard(card);
    } else if (this.turn.phase === 'WAITING_FOR_CHOICE') {
      this.choose(this.game, this.player, this.choice, card);
    } else if (this.turn.phase === 'BUY') {
      this.purchase(card);
    }
  }

  choose(game: Game, player: Player, choose: any, response: any) {
    const choice: Choice = {
      cardOptions: [],
      options: [],
      expectedAnswerType: null,
      required: false,
      targetChoice: null,
      card: null,
      yes: null
    };

    choice.targetChoice = choose.id;

    switch (choose.expectedAnswerType) {
      case 'CARD':
        choice.card = response;
        break;
      case 'YES_OR_NO':
        choice.yes = response;
        break;
    }

    this.gameService.choice(game, choice).subscribe();
  }

  chooseDone(game: Game, player: Player, choose: Choice) {
    const choice = {
      targetChoice: choose.id,
      done: true
    };

    this.gameService.choice(game, choice).subscribe(
      () => {
        console.log('Done with choice.');
      },
      error => {
        console.log(error);
      }
    );
  }

  isLoggedInPlayer(player: Player) {
    return player.id === this.playerId;
  }

  logout() {}

  isActivePlayer(player: Player) {
    return this.playerId === player.id;
  }

  public hasCurrentPlayer() {
    return this.game && this.game.players && this.game.players.hasOwnProperty(this.playerId);
  }

  getCurrentPlayer() {
    if (this.hasCurrentPlayer()) {
      return this.players[this.playerId];
    }
  }

  hasCardType(type: string, card: Card) {
    card.cardTypes.includes(type);
  }

  getChoices() {
    return this.getCurrentPlayer().choices;
  }

  getImagePath(card: Card) {
    if (card && card.name) {
      return '/assets/cards/' + card.name.toLowerCase().replace(/\s/g, '') + '-sm.jpg';
    } else {
      return '/assets/empty-card-back-sm.jpg';
    }
  }

  getLargeImagePath(card: any) {
    if (card && card.name) {
      return '/assets/cards/' + card.name.toLowerCase().replace(/\s/g, '') + '.jpg';
    } else {
      return '/assets/empty-card-back.jpg';
    }
  }

  isCardInPlay(card: any) {
    return (
      Object.keys(this.play)
        .map(i => this.play[i].id)
        .indexOf(card.id) > 0
    );
  }

  numberOfCardsInKingdom(name: string) {
    return this.game.kingdom.cardMarket[name].length;
  }

  isOption(id: string) {
    return this.choice && this.choice.options && this.choice.options.indexOf(id) >= 0;
  }

  updateData(game: Game, hashcode: number) {
    if (hashcode) {
      if (hashcode === this.hashcode) {
        return;
      }
      if (this.interval) {
        clearInterval(this.interval);
        this.interval = null;
      }
      this.hashcode = hashcode;
    }

    if (this.interval) {
      clearInterval(this.interval);
    }

    this.game = game;

    this.players = this.game.players;
    let messagePointer = 0;
    this.logs = this.game.logs
      .map(
        (log: any): Object => {
          const logParts = log.split('::');
          if (logParts.length > 1) {
            return { time: moment(logParts[0]), message: logParts[1] };
          } else {
            return { message: logParts[0] };
          }
        }
      )
      .map((message: Message) => {
        if (messagePointer++ > this.shownMessages) {
          message.addedAt = Date.now() + 500 * (this.shownMessages - messagePointer);

          this.messageService.publish(message);
        }
        return message;
      });

    this.shownMessages = messagePointer;
    if (this.playerId) {
      this.player = this.game.players[this.playerId];

      this.hand = this.hand = this.game.players[this.playerId].hand;
      this.deck = this.deck = this.game.players[this.playerId].deck;
      this.turn = this.game.players[this.playerId].currentTurn;
      this.discard = this.discard = this.game.players[this.playerId].discard;
      this.play = this.play = this.game.turn.play;

      if (this.game.players[this.playerId] && this.game.players[this.playerId].choices) {
        this.choice = this.choice = this.game.players[this.playerId].choices[0];
      }
    }

    this.commonCards = {
      cardMarket: []
    };

    Object.keys(this.game.kingdom.cardMarket)
      .filter(id => !this.game.kingdom.cardMarket[id][0].isKingdom)
      .forEach(id => {
        const stack = this.game.kingdom.cardMarket[id];
        const card = stack[0];
        card.remaining = stack.length;
        this.commonCards.cardMarket.push(card);
      });

    this.kingdomCards = {
      cardMarket: []
    };
    Object.keys(this.game.kingdom.cardMarket)
      .filter(id => this.game.kingdom.cardMarket[id][0].isKingdom)
      .forEach(id => {
        const stack = this.game.kingdom.cardMarket[id];
        const card = stack[0];
        card.remaining = stack.length;
        this.kingdomCards.cardMarket.push(card);
      });

    this.interval = this.startInterval();
  }

  startInterval() {
    return setInterval(() => {
      if (this.loopSubscription == null || this.loopSubscription.closed) {
        this.loopSubscription = this.gameService.get(this.gameId).subscribe(response => {
          this.updateData(<Game>response.body, parseInt(response.headers.get('hashcode'), 10));

          this.title = `Game ${this.game.id}`;
          this.gameId = this.game.id;
          this.players = this.game.players;

          if (Object.keys(this.game.players).includes(this.playerId)) {
            this.playerId = this.game.turn.playerId;
          }
        });
      }
    }, 1000);
  }
}
