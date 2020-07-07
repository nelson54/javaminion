import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {SignupRoutingModule} from "@app/signup/signup-routing.module";
import {SignupComponent} from "@app/signup/signup.component";



@NgModule({
  imports: [CommonModule, ReactiveFormsModule, TranslateModule, NgbModule, SignupRoutingModule],
  declarations: [SignupComponent]
})
export class SignupModule {}
