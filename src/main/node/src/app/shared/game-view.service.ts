import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Game } from '@app/shared/game/game.interface';
import { Choice } from '@app/shared/game/choice.interface';
import { Card } from '@app/shared/game/card.interface';
import { Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import {GameView} from '@app/shared/view/game-view.interface';

@Injectable()
export class GameViewService {
  private apiRoot = '/api/v2/game';

  constructor(private httpClient: HttpClient) {}

  get(id: string, params?: { string: string }): Observable<GameView> {
    return this.httpClient.get(this.apiRoot + '/' + id, { params, observe: 'response' })
      .pipe(
        map((body: any) => body),
        catchError(() => of('Error, Unable to access game state.'))
      );
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

  surrender(gameId: string) {
    return this.httpClient.post(`${this.apiRoot}/${gameId}/surrender`, {});
  }
}
