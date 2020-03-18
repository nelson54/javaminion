import { Component, Host, Input } from '@angular/core';
import { GameComponent } from '@app/game/game.component';

@Component({
  selector: 'kingdom',
  templateUrl: './kingdom.component.html',
  styleUrls: ['./kingdom.component.scss']
})
export class KingdomComponent {
  constructor(@Host() public gamePlay: GameComponent) {}
}
