import { Component, OnInit, Input } from '@angular/core';
import {CardStack} from "@app/shared/view/game-view.interface";


@Component({
  selector: 'card-stack-view',
  templateUrl: './card-stack-view.component.html',
  styleUrls: ['./card-stack-view.component.scss']
})
export class CardStackViewComponent implements OnInit {
  @Input() gameId: string;
  @Input() cardStack: CardStack;

  public largeImage: string;
  public smallImage: string;

  static smallBack: string = '/assets/empty-card-back-sm.jpg';
  static largeBack: string = '/assets/empty-card-back.jpg';

  constructor() {

  }

  ngOnInit() {
    this.smallImage = '/assets/cards/' + this.cardStack.name.toLowerCase().replace(/\s/g, '') + '-sm.jpg';
    this.largeImage = '/assets/cards/' + this.cardStack.name.toLowerCase().replace(/\s/g, '') + '.jpg';
  }

  isOption() {
    
  }

  choose() {
    
  }

  showCardDetails() {
    
  }
}
