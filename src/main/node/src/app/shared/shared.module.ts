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

@NgModule({
  imports: [CommonModule],
  declarations: [LoaderComponent, IdenticonComponent, ToastGroupComponentComponent, EloComponent],
  providers: [MessageService, MatchService, GameService, ShowMainNavService],
  exports: [LoaderComponent, IdenticonComponent, ToastGroupComponentComponent, EloComponent]
})
export class SharedModule {}
