import { Component, OnInit } from '@angular/core';
import {GameViewService} from "@app/shared/game-view.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-game-view',
  templateUrl: './game-view.component.html',
  styleUrls: ['./game-view.component.scss']
})
export class GameViewComponent implements OnInit {
  private id: string;
  public gameView: any;

  constructor(
    private route: ActivatedRoute,
    private gameViewService: GameViewService,
  ) {
    this.id = this.route.snapshot.params.id;
  }

  ngOnInit() {
    this.gameViewService.get(this.id)
      .subscribe((gameViewRequest) => {
        this.gameView = gameViewRequest.body;
        console.dir(this.gameView);
      });
  }

}
