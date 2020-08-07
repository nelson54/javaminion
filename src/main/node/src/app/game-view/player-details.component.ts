import { Component, OnInit, Input } from '@angular/core';
import {CardRef} from "@app/shared/view/game-view.interface";
import {HighlightOptions} from "@app/game-view/highlight-options.service";

interface Card {
  name: string;
}

interface PlayerDetails {
  hand: Card[];
}

@Component({
  selector: 'player-details',
  templateUrl: './player-details.component.html',
  styleUrls: ['./player-details.component.scss']
})
export class PlayerDetailsComponent implements OnInit {
  @Input() public gameId: string;
  @Input() public highlights: HighlightOptions;
  @Input() public playerDetails: PlayerDetails;

  constructor() {}

  ngOnInit() {}
}
