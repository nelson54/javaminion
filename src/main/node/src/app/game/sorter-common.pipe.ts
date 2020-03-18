import { Pipe, PipeTransform } from '@angular/core';
import { Card } from '@app/shared/game/card.interface';

const sort = (c1: Card, c2: Card) => {
  if (c1.kingdomSortOrder === c2.kingdomSortOrder) {
    return 0;
  } else if (c1.kingdomSortOrder > c2.kingdomSortOrder) {
    return 1;
  } else {
    return -1;
  }
};

@Pipe({ name: 'sorterCommon' })
export class SorterCommon implements PipeTransform {
  transform(cards: Card[]) {
    return cards.sort(sort);
  }
}
