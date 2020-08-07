import { Component, OnInit, Input } from '@angular/core';
import {Card, CardRef} from '../shared/view/game-view.interface';

@Component({
  selector: 'card-view',
  templateUrl: './card-view.component.html',
  styleUrls: ['./card-view.component.scss']
})
export class CardViewComponent implements OnInit {
  public static readonly smallBack: string = '/assets/empty-card-back-sm.jpg';
  public static readonly largeBack: string = '/assets/empty-card-back.jpg';

  @Input() public gameId: string;
  @Input() public card: Card;
  @Input() public highlights: CardRef[];

  public largeImage: string;
  public smallImage: string;

  constructor() {

  }

  ngOnInit() {
    this.smallImage = '/assets/cards/' + this.card.name.toLowerCase().replace(/\s/g, '') + '-sm.jpg';
    this.largeImage = '/assets/cards/' + this.card.name.toLowerCase().replace(/\s/g, '') + '.jpg';
  }

  get isHighlighted() {
    return this.highlights
      .filter(card => card.id === this.card.id || card.name === this.card.name)
      .length > 0;
  }

  selectCard() {

  }

  showCardDetails() {

  }
}
