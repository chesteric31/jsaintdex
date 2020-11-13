import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JsaintdexSharedModule } from 'app/shared/shared.module';
import { ArmorVersionComponent } from './armor-version.component';
import { ArmorVersionDetailComponent } from './armor-version-detail.component';
import { ArmorVersionUpdateComponent } from './armor-version-update.component';
import { ArmorVersionDeleteDialogComponent } from './armor-version-delete-dialog.component';
import { armorVersionRoute } from './armor-version.route';

@NgModule({
  imports: [JsaintdexSharedModule, RouterModule.forChild(armorVersionRoute)],
  declarations: [ArmorVersionComponent, ArmorVersionDetailComponent, ArmorVersionUpdateComponent, ArmorVersionDeleteDialogComponent],
  entryComponents: [ArmorVersionDeleteDialogComponent],
})
export class JsaintdexArmorVersionModule {}
