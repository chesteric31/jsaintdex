import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JsaintdexSharedModule } from 'app/shared/shared.module';
import { ArmorCategoryComponent } from './armor-category.component';
import { ArmorCategoryDetailComponent } from './armor-category-detail.component';
import { ArmorCategoryUpdateComponent } from './armor-category-update.component';
import { ArmorCategoryDeleteDialogComponent } from './armor-category-delete-dialog.component';
import { armorCategoryRoute } from './armor-category.route';

@NgModule({
  imports: [JsaintdexSharedModule, RouterModule.forChild(armorCategoryRoute)],
  declarations: [ArmorCategoryComponent, ArmorCategoryDetailComponent, ArmorCategoryUpdateComponent, ArmorCategoryDeleteDialogComponent],
  entryComponents: [ArmorCategoryDeleteDialogComponent],
})
export class JsaintdexArmorCategoryModule {}
