import { Component, OnInit, TemplateRef, ViewEncapsulation } from '@angular/core';
import { MatchService } from '@app/shared/match.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { GameService } from '@app/shared/game.service';
import { Match } from '@app/shared/game/match.interface';
import { AuthenticationService, Credentials, CredentialsService } from '@app/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  public filters = {
    waitingForOpponent: true,
    inProgress: true,
    isFinished: false
  };
  recommendedCards: [] | any;
  matches: Match[];
  isLoading = false;
  newGame = false;

  cards = 'First Game';
  playerNumbers = [2, 3, 4];
  playerCount = 2;

  players = [{ id: 0, ai: true }, { id: 1, ai: true }];

  closeResult: string;
  credentials: Credentials;

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
    this.gameService.getRecommendedCards().subscribe(response => (this.recommendedCards = response));
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

  startCreate() {
    this.newGame = true;
  }

  setCards(cards: any) {
    this.cards = cards;
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

  createGame() {
    this.newGame = true;
  }

  updatePlayerCount(count: number) {
    this.players = [];
    for (count; count !== 1; count--) {
      this.players.push({ id: count, ai: true });
    }
  }

  isMyGame(match: Match) {
    return (
      match.participants.filter(participant => {
        return participant.account.user.username === this.credentialsService.credentials.username;
      }).length > 0
    );
  }
}
