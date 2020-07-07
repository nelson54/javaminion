import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { extract } from '@app/core';
import { SignupComponent } from './signup.component';

const routes: Routes = [{ path: 'signup', component: SignupComponent, data: { title: extract('Sign Up') } }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: []
})
export class SignupRoutingModule {}
