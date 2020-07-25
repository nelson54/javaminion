import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { Angulartics2Module } from 'angulartics2';

import { CoreModule } from '@app/core';
import { SharedModule } from '@app/shared';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenInterceptor } from '@app/core/http/token-interceptor';
import { GameComponent } from '@app/game/game.component';
import { GameNavComponent } from '@app/game/game-nav.component';
import { KingdomComponent } from '@app/game/kingdom.component';
import { GameService } from '@app/shared/game.service';
import { GameRoutingModule } from '@app/game/game-routing.module';
import { MessageService } from '@app/shared/message.service';
import { PhasePipe } from '@app/game/phase.pipe';
import { UserService } from '@app/shared/user.service';
import { SorterCommon } from '@app/game/sorter-common.pipe';
import { SorterKingdom } from '@app/game/sorter-kingdom.pipe';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { GameViewComponent } from '@app/game-view/game-view.component';
import { PlayerDetailsComponent } from '@app/game-view/player-details.component';
import { CardViewComponent } from '@app/game-view/card-view.component';
import {CardStackViewComponent} from "@app/game-view/card-stack-view.component";
import {CardMarketViewComponent} from "@app/game-view/card-market-view.component";
import {FilterKingdom} from "@app/game/filter-kingdom.pipe";
import {FilterCommon} from "@app/game/filter-common.pipe";

@NgModule({
  imports: [CommonModule, TranslateModule, CoreModule, SharedModule, Angulartics2Module, GameRoutingModule, NgbModule],
  declarations: [
    GameComponent,
    GameNavComponent,
    KingdomComponent,
    PhasePipe,
    SorterCommon,
    FilterCommon,
    FilterKingdom,
    SorterKingdom,
    GameViewComponent,
    PlayerDetailsComponent,
    CardViewComponent,
    CardStackViewComponent,
    CardMarketViewComponent
  ],
  providers: [
    GameService,
    MessageService,
    UserService,
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true }
  ]
})
export class GameModule {}
