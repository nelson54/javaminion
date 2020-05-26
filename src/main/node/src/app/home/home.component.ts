import { Component, OnInit, TemplateRef, ViewEncapsulation } from '@angular/core';
import { MatchService } from '@app/shared/match.service';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { GameService } from '@app/shared/game.service';
import { Match } from '@app/shared/game/match.interface';
import { AuthenticationService, Credentials, CredentialsService } from '@app/core';
import { MatchFormComponent } from '@app/shared/match/match-form.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  private modalInstance: NgbModalRef;
  public isLoading: boolean;
  public credentials: Credentials;
  public matches: Match[];

  public filters = {
    waitingForOpponent: true,
    inProgress: true,
    isFinished: false
  };

  constructor(
    private modalService: NgbModal,
    private matchService: MatchService,
    private gameService: GameService,
    private credentialsService: CredentialsService
  ) {
    this.credentials = this.credentialsService.credentials;
  }

  ngOnInit() {
    this.isLoading = true;
    this.reload();
  }

  create() {
    this.modalInstance = this.modalService.open(MatchFormComponent, { ariaLabelledBy: 'modal-basic-title' });

    this.modalInstance.result.then(
      result => {
        this.reload();
      },
      reason => {
        console.log(reason)
      }
    );
  }

  joinGame(game: any) {
    this.matchService.join(game.id).subscribe(() => {
      this.reload();
    });
  }

  reload() {
    this.isLoading = true;
    return this.matchService.query(this.filters).subscribe((matches: any) => {
      this.isLoading = false;
      this.matches = matches.content;
    });
  }

  isMyGame(match: Match) {
    return (
      match.participants.filter(participant => {
        return participant.account.user.username === this.credentialsService.credentials.username;
      }).length > 0
    );
  }
}
