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

@NgModule({
  imports: [CommonModule, TranslateModule, CoreModule, SharedModule, Angulartics2Module, GameRoutingModule, NgbModule],
  declarations: [GameComponent, GameNavComponent, KingdomComponent, PhasePipe, SorterCommon, SorterKingdom],
  providers: [
    GameService,
    MessageService,
    UserService,
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true }
  ]
})
export class GameModule {}
