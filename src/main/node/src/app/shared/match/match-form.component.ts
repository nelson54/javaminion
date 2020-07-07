import { Component, OnInit, TemplateRef, ViewEncapsulation } from '@angular/core';
import { MatchService } from '@app/shared/match.service';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { GameService } from '@app/shared/game.service';
import { Match } from '@app/shared/game/match.interface';
import { AuthenticationService, Credentials, CredentialsService } from '@app/core';
import { FormControl, FormGroup } from '@angular/forms';
import {CreateMatch} from "@app/shared/match/match.interface";

@Component({
  selector: 'match-form',
  templateUrl: './match-form.component.html'
  // styleUrls: ['./match-form.component.scss']
})
export class MatchFormComponent implements OnInit {
  recommendedCards: [] | any;
  newGame: boolean = false;
  private isSaving: boolean = false;
  cards: string = 'First Game';
  playerNumbers: number[] = [2, 3, 4];
  playerCount: number = 2;

  players: object[] = [{ id: 0, ai: true }, { id: 1, ai: true }];
  credentials: Credentials;

  form = new FormGroup({
    cards: new FormControl([]),
    playerCount: new FormControl([]),

    player1: new FormControl([]),
    player2: new FormControl([]),
    player3: new FormControl([]),
    player4: new FormControl([])
  });

  constructor(
    private matchService: MatchService,
    private gameService: GameService,
    private credentialsService: CredentialsService,
    public modal: NgbActiveModal
  ) {
    this.credentials = this.credentialsService.credentials;
  }

  ngOnInit() {
    this.matchService.getRecommendedCards().subscribe(response => (this.recommendedCards = response));

    this.form.controls.cards.setValue('First Game');

    this.form.controls.playerCount.setValue(2);

    this.form.controls.player1.setValue(false);
    this.form.controls.player2.setValue(true);
    this.form.controls.player3.setValue(true);
    this.form.controls.player4.setValue(true);
  }

  setCards(cards: any) {
    this.cards = cards;
  }

  private findNumberOfAiPlayers() : number {
    return [
      this.form.controls.player1,
      this.form.controls.player2,
      this.form.controls.player3,
      this.form.controls.player4
    ].filter((control: FormControl, i: number) => {
      return i <= this.form.controls.playerCount.value && !control.value
    }).length;
  }

  createMatch() {
    let match: CreateMatch = <CreateMatch>{};
    match.count = this.form.controls.playerCount.value;
    match.numberOfAiPlayers = this.findNumberOfAiPlayers();
    match.numberOfHumanPlayers = match.count - match.numberOfAiPlayers;
    match.cards = this.form.controls.cards.value;

    this.isSaving = true;

    console.log(match);

    return this.matchService.save(match)
      .subscribe(() => {
        this.modal.close('Saved');
      })
  }

  cancel(){
    this.modal.dismiss('Cancelled')
  }

  getPlayerCount() {
    return this.form.controls.playerCount.value
  }
}
