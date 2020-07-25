import { Pipe, PipeTransform } from '@angular/core';

const sort = (c1: any, c2: any) : number => {
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
  transform(cards: any): any {
    if(!cards) return [];
    return cards.sort(sort);
  }
}
