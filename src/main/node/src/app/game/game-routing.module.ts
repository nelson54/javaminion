import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { Shell } from '@app/shell/shell.service';
import { GameComponent } from '@app/game/game.component';

const routes: Routes = [
  Shell.childRoutes([
    // { path: '', redirectTo: '/game', pathMatch: 'full' },
    { path: 'game/:id', component: GameComponent }
  ])
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: []
})
export class GameRoutingModule {}
