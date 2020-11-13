import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JsaintdexSharedModule } from 'app/shared/shared.module';
import { StrengthComponent } from './strength.component';
import { StrengthDetailComponent } from './strength-detail.component';
import { StrengthUpdateComponent } from './strength-update.component';
import { StrengthDeleteDialogComponent } from './strength-delete-dialog.component';
import { strengthRoute } from './strength.route';

@NgModule({
  imports: [JsaintdexSharedModule, RouterModule.forChild(strengthRoute)],
  declarations: [StrengthComponent, StrengthDetailComponent, StrengthUpdateComponent, StrengthDeleteDialogComponent],
  entryComponents: [StrengthDeleteDialogComponent],
})
export class JsaintdexStrengthModule {}
