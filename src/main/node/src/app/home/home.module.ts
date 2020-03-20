import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { Angulartics2Module } from 'angulartics2';
import { MomentModule } from 'ngx-moment';

import { CoreModule } from '@app/core';
import { SharedModule } from '@app/shared';
import { HomeRoutingModule } from './home-routing.module';
import { HomeComponent } from './home.component';
import { MatchService } from '@app/shared/match.service';
import { GameStatePipe } from '@app/home/GameState.pipe';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenInterceptor } from '@app/core/http/token-interceptor';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { GameOrderPipe } from '@app/home/game-order.pipe';
import { MatchFormComponent } from '@app/shared/match/match-form.component';

@NgModule({
  imports: [
    CommonModule,
    TranslateModule,
    CoreModule,
    SharedModule,
    Angulartics2Module,
    HomeRoutingModule,
    MomentModule,
    FormsModule,
    ReactiveFormsModule
  ],

  declarations: [HomeComponent, GameStatePipe, GameOrderPipe],
  providers: [MatchService, { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true }]
})
export class HomeModule {}
