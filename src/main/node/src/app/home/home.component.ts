import { Component, OnInit, TemplateRef, ViewEncapsulation } from '@angular/core';
import { MatchService } from '@app/shared/match.service';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { GameService } from '@app/shared/game.service';
import { Match } from '@app/shared/game/match.interface';
import { AuthenticationService, Credentials, CredentialsService } from '@app/core';
import { MatchFormComponent } from '@app/shared/match/match-form.component';
import { FormControl, FormGroup } from '@angular/forms';
import { forEach } from '@angular/router/src/utils/collection';

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

  public first = false;
  public last = true;
  public page = 0;
  public totalPages = 1;

  public filters = new FormGroup({
    waitingForOpponent: new FormControl(true),
    inProgress: new FormControl(true),
    isFinished: new FormControl(true)
  });

  constructor(
    private modalService: NgbModal,
    private matchService: MatchService,
    private gameService: GameService,
    private credentialsService: CredentialsService
  ) {
    this.credentials = this.credentialsService.credentials;
    this.filters.valueChanges.subscribe(() => this.reload());
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
        console.log(reason);
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

    const request = this.filters.getRawValue();
    request.page = this.page;

    return this.matchService.query(request).subscribe((matches: any) => {
      this.isLoading = false;
      this.page = matches.number;
      this.first = matches.first;
      this.last = matches.last;
      this.totalPages = matches.totalPages;
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

  canJoin(game: Match) {
    return (
      game.participants.filter(
        participant => participant.account.user.username === this.credentialsService.credentials.username
      ).length === 0
    );
  }

  canPlay(game: Match) {
    return (
      game.participants.filter(
        participant => participant.account.user.username === this.credentialsService.credentials.username
      ).length === 1
    );
  }

  next() {
    if (!this.last) {
      this.page++;
      this.reload();
    }
  }

  prev() {
    if (!this.first) {
      this.page--;
      this.reload();
    }
  }
}
