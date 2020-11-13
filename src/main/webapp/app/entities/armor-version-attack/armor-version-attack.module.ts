import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JsaintdexSharedModule } from 'app/shared/shared.module';
import { ArmorVersionAttackComponent } from './armor-version-attack.component';
import { ArmorVersionAttackDetailComponent } from './armor-version-attack-detail.component';
import { ArmorVersionAttackUpdateComponent } from './armor-version-attack-update.component';
import { ArmorVersionAttackDeleteDialogComponent } from './armor-version-attack-delete-dialog.component';
import { armorVersionAttackRoute } from './armor-version-attack.route';

@NgModule({
  imports: [JsaintdexSharedModule, RouterModule.forChild(armorVersionAttackRoute)],
  declarations: [
    ArmorVersionAttackComponent,
    ArmorVersionAttackDetailComponent,
    ArmorVersionAttackUpdateComponent,
    ArmorVersionAttackDeleteDialogComponent,
  ],
  entryComponents: [ArmorVersionAttackDeleteDialogComponent],
})
export class JsaintdexArmorVersionAttackModule {}
