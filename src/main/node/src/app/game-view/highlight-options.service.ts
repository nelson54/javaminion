import {CardRef, CardType, GameView, Phase} from '@app/shared/view/game-view.interface';
import {Injectable} from '@angular/core';

export interface HighlightOptions {
  market: CardRef[];
  hand: CardRef[];
  choice: CardRef[];
}

@Injectable()
export class HighlightOptionsService {

  public getHighlights(game: GameView): HighlightOptions {
    const highlights = <HighlightOptions>{};

    highlights.market = this.getPurchaseOptions(game);
    highlights.hand = this.getActionOptions(game);
    highlights.choice = this.getChoiceOptions(game, highlights);

    return highlights;
  }



  private getPurchaseOptions(game: GameView): CardRef[] {
    const market: CardRef[] = [];
    const money: number = game.turn.money;
    const buys: number = game.turn.buys;
    if (game.playerDetails.isMyTurn && game.gamePhase === Phase.BUY && buys > 0) {
      game.cardMarket
        .filter(stack => money >= stack.cost.money)
        .map(stack => <CardRef>{name: stack.name})
        .forEach(stack => market.push(stack));
    }

    return market;
  }

  private getActionOptions(game: GameView): CardRef[] {
    const hand: CardRef[] = [];
    const actions: number = game.turn.actions;

    if (game.playerDetails.isMyTurn && game.gamePhase === Phase.ACTION && actions > 0) {
      game.playerDetails.hand
        .filter((card) => card.types.indexOf(CardType.ACTION) >= 0)
        .map(card => <CardRef>{id: card.id, name: card.name})
        .forEach(card => hand.push(card));
    }

    return hand;
  }

  private getChoiceOptions(game: GameView, highlights: HighlightOptions): CardRef[] {
    return [];
  }
}
