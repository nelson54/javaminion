import { Component, OnInit, Input } from '@angular/core';

interface Card {
  name: string;
}

interface PlayerDetails {
  hand: Card[];
}

@Component({
  selector: 'player-details',
  templateUrl: './player-details.component.html'
  // styleUrls: ['./player-area-view.component.scss']
})
export class PlayerDetailsComponent implements OnInit {
  id: string;

  @Input() playerDetails: PlayerDetails;

  constructor() {}

  ngOnInit() {}
}
