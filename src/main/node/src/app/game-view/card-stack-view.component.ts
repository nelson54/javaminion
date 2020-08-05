import { Component, OnInit, Input } from '@angular/core';
import {CardRef, CardStack} from '@app/shared/view/game-view.interface';


@Component({
  selector: 'card-stack-view',
  templateUrl: './card-stack-view.component.html',
  styleUrls: ['./card-stack-view.component.scss']
})
export class CardStackViewComponent implements OnInit {

  public static readonly smallBack: string = '/assets/empty-card-back-sm.jpg';
  public static readonly largeBack: string = '/assets/empty-card-back.jpg';
  public largeImage: string;
  public smallImage: string;
  @Input() public gameId: string;
  @Input() public cardStack: CardStack;
  @Input() public highlights: CardRef[];

  constructor() {

  }

  ngOnInit() {
    this.smallImage = '/assets/cards/' + this.cardStack.name.toLowerCase().replace(/\s/g, '') + '-sm.jpg';
    this.largeImage = '/assets/cards/' + this.cardStack.name.toLowerCase().replace(/\s/g, '') + '.jpg';
  }

  get isHighlighted() {
    return this.highlights
      .filter(card => card.name === this.cardStack.name).length > 0;
  }

  choose() {

  }

  showCardDetails() {

  }
}
