import { Pipe, PipeTransform } from '@angular/core';

const sort = (c1: any, c2: any) => {
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
  transform(cards: any) {
    if(!cards) return [];

    return cards.sort(sort);
  }
}
