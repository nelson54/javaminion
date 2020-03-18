import { Pipe, PipeTransform } from '@angular/core';
import { Card } from '@app/shared/game/card.interface';

const sort = (c1: Card, c2: Card) => {
  try {
    if (c1.cost.money === c2.cost.money) {
      return 0;
    } else if (c1.cost.money > c2.cost.money) {
      return 1;
    } else {
      return -1;
    }
  } catch (e) {
    return 1;
  }
};

@Pipe({ name: 'sorterKingdom' })
export class SorterKingdom implements PipeTransform {
  transform(cards: Card[]) {
    return cards.sort(sort);
  }
}
