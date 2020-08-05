import { Component, OnInit, Input } from '@angular/core';
import {CardRef, CardStack} from '@app/shared/view/game-view.interface';


@Component({
  selector: 'card-market-view',
  templateUrl: './card-market-view.component.html',
  // styleUrls: ['./card-view.component.scss']
})
export class CardMarketViewComponent implements OnInit {
  @Input() gameId: string;
  @Input() public market: CardStack[];
  @Input() public highlights: CardRef[];
  constructor() {

  }

  ngOnInit() {
  }

  choose() {

  }

  showCardDetails() {

  }
}
