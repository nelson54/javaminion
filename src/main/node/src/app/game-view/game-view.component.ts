import { Component, OnInit } from '@angular/core';
import { GameViewService } from '@app/shared/game-view.service';
import { ActivatedRoute } from '@angular/router';
import {GameView} from '@app/shared/view/game-view.interface';
import {HighlightOptions, HighlightOptionsService} from "@app/game-view/highlight-options.service";

@Component({
  selector: 'app-game-view',
  templateUrl: './game-view.component.html',
  styleUrls: ['./game-view.component.scss']
})
export class GameViewComponent implements OnInit {
  public id: string;
  public gameView: GameView;
  public highlightOptions: HighlightOptions;

  constructor(
    private route: ActivatedRoute,
    private gameViewService: GameViewService,
    private highlightOptionsService: HighlightOptionsService,
  ) {
    this.id = this.route.snapshot.params.id;
  }

  ngOnInit() {
    this.tick();
  }

  private tick() {
    this.gameViewService.get(this.id)
      .subscribe(gameView => {
        this.gameView = gameView;
        this.highlightOptions = this.highlightOptionsService.getHighlights(gameView);

        console.dir(this.gameView);
      });
  }
}
