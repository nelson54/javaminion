'use strict';

import { Component, Host, TemplateRef } from '@angular/core';
import { GameComponent } from '@app/game/game.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AuthenticationService } from '@app/core';
import { Router } from '@angular/router';
import { GameService } from '@app/shared/game.service';

@Component({
  selector: 'game-nav',
  templateUrl: './game-nav.component.html',
  styleUrls: ['./game-nav.component.scss']
})
export class GameNavComponent {
  additionalActions = false;
  closeResult: false | string = false;

  constructor(
    @Host() public gamePlay: GameComponent,
    private router: Router,
    private authenticationService: AuthenticationService,
    private gameService: GameService,
    private modalService: NgbModal
  ) {}

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

  logout() {
    this.authenticationService.logout().subscribe(() => this.router.navigate(['/login'], { replaceUrl: true }));
  }

  surrender() {
    this.gameService.surrender(this.gamePlay.gameId).subscribe(() => {
      this.router.navigate(['/home'], { replaceUrl: true });
    });
  }
}
