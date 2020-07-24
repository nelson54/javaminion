import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { Shell } from '@app/shell/shell.service';
import { GameComponent } from '@app/game/game.component';
import { GameViewComponent } from '@app/game-view/game-view.component';

const routes: Routes = [
  Shell.childRoutes([
    { path: 'game/:id', component: GameComponent },
    { path: 'view/:id', component: GameViewComponent }
  ])
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: []
})
export class GameRoutingModule {}
