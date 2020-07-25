import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'filterCommon' })
export class FilterCommon implements PipeTransform {
  transform(cards: any): any {
    if(!cards) return [];
    return cards.filter((c: any) => !c.kingdom)
  }
}
