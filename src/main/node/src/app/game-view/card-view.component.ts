import { Component, OnInit, Input } from '@angular/core';
import { Card } from '../shared/view/game-view.interface';
import {Router} from "@angular/router";

@Component({
  selector: 'card-view',
  templateUrl: './card-view.component.html',
  //styleUrls: ['./card-view.component.scss']
})
export class CardViewComponent implements OnInit {
  @Input() gameId: string;
  @Input() card: Card;

  public largeImage: string;
  public smallImage: string;

  static smallBack: string = '/assets/empty-card-back-sm.jpg';
  static largeBack: string = '/assets/empty-card-back.jpg';

  constructor() {

  }

  ngOnInit() {
    this.smallImage = '/assets/cards/' + this.card.name.toLowerCase().replace(/\s/g, '') + '-sm.jpg';
    this.largeImage = '/assets/cards/' + this.card.name.toLowerCase().replace(/\s/g, '') + '.jpg';
  }

  isOption() {
    
  }

  selectCard() {
    
  }

  showCardDetails() {
    
  }
}
