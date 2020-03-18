'use strict';

import { Component, Host, Input, TemplateRef } from '@angular/core';
import { GameComponent } from '@app/game/game.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'game-nav',
  templateUrl: './game-nav.component.html',
  styleUrls: ['./game-nav.component.scss']
})
export class GameNavComponent {
  additionalActions = false;
  closeResult: false | string = false;

  constructor(@Host() public gamePlay: GameComponent, private modalService: NgbModal) {}

  toggleAdditionalActions() {
    this.additionalActions = !this.additionalActions;
  }

  open(content: TemplateRef<any>) {
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then(
      result => {
        this.closeResult = `Closed with: ${result}`;
      },
      reason => {
        this.closeResult = `Dismissed `; // ${this.getDismissReason(reason)}`;
      }
    );
  }

  showAdditionalActions() {
    return this.additionalActions;
  }
}
