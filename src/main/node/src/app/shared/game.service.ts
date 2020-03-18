import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Game } from '@app/shared/game/game.interface';
import { Choice } from '@app/shared/game/choice.interface';
import { Card } from '@app/shared/game/card.interface';

@Injectable()
export class GameService {
  private apiRoot = '/dominion';

  constructor(private httpClient: HttpClient) {}

  get(id: string, params?: { string: string }) {
    return this.httpClient.get(this.apiRoot + '/' + id, { params, observe: 'response' });
  }

  purchase(game: Game, card: Card) {
    return this.httpClient.post(`${this.apiRoot}/${game.id}/purchase/${card.id}`, {});
  }

  play(game: Game, card: Card) {
    return this.httpClient.post(`${this.apiRoot}/${game.id}/play/${card.id}`, {});
  }

  choice(game: Game, choice: Choice) {
    return this.httpClient.post(`${this.apiRoot}/${game.id}/choice`, choice);
  }

  endPhase(game: Game) {
    return this.httpClient.post(`${this.apiRoot}/${game.id}/next-phase`, {});
  }

  getRecommendedCards() {
    return this.httpClient.get('/dominion/recommended');
  }
}
