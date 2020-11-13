import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JsaintdexSharedModule } from 'app/shared/shared.module';
import { ArmorComponent } from './armor.component';
import { ArmorDetailComponent } from './armor-detail.component';
import { ArmorUpdateComponent } from './armor-update.component';
import { ArmorDeleteDialogComponent } from './armor-delete-dialog.component';
import { armorRoute } from './armor.route';

@NgModule({
  imports: [JsaintdexSharedModule, RouterModule.forChild(armorRoute)],
  declarations: [ArmorComponent, ArmorDetailComponent, ArmorUpdateComponent, ArmorDeleteDialogComponent],
  entryComponents: [ArmorDeleteDialogComponent],
})
export class JsaintdexArmorModule {}
