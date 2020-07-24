import { Component, OnInit, Input } from '@angular/core';
import { Card } from '../shared/view/game-view.interface';

@Component({
  selector: 'card-view',
  templateUrl: './card-view.component.html',
  styleUrls: ['./card-view.component.scss']
})
export class CardViewComponent implements OnInit {
  id: string;

  @Input() card: Card;

  constructor() {}

  ngOnInit() {}

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
}
