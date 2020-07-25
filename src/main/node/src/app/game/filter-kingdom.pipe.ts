import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'filterKingdom' })
export class FilterKingdom implements PipeTransform {
  transform(cards: any): any {
    if(!cards) return [];
    return cards.filter((c: any)=> c.kingdom);
  }
}
