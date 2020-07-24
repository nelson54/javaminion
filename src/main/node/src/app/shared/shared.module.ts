import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LoaderComponent } from './loader/loader.component';
import { IdenticonComponent } from '@app/shared/identicon/identicon.component';
import { ToastGroupComponentComponent } from '@app/shared/toast-group/toast-group-component.component';
import { EloComponent } from '@app/shared/elo/elo.component';
import { MatchService } from '@app/shared/match.service';
import { MessageService } from '@app/shared/message.service';
import { GameService } from '@app/shared/game.service';
import { ShowMainNavService } from '@app/shared/ShowMainNav.service';
import { MatchFormComponent } from '@app/shared/match/match-form.component';
import { ReactiveFormsModule } from '@angular/forms';
import { GameViewService } from '@app/shared/game-view.service';

@NgModule({
  imports: [CommonModule, ReactiveFormsModule],
  declarations: [LoaderComponent, IdenticonComponent, ToastGroupComponentComponent, EloComponent, MatchFormComponent],
  providers: [MessageService, MatchService, GameService, GameViewService, ShowMainNavService],
  exports: [LoaderComponent, IdenticonComponent, ToastGroupComponentComponent, EloComponent, MatchFormComponent],
  entryComponents: [MatchFormComponent]
})
export class SharedModule {}
