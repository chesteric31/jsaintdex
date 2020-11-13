import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'armor-category',
        loadChildren: () => import('./armor-category/armor-category.module').then(m => m.JsaintdexArmorCategoryModule),
      },
      {
        path: 'strength',
        loadChildren: () => import('./strength/strength.module').then(m => m.JsaintdexStrengthModule),
      },
      {
        path: 'armor',
        loadChildren: () => import('./armor/armor.module').then(m => m.JsaintdexArmorModule),
      },
      {
        path: 'armor-strength',
        loadChildren: () => import('./armor-strength/armor-strength.module').then(m => m.JsaintdexArmorStrengthModule),
      },
      {
        path: 'armor-version',
        loadChildren: () => import('./armor-version/armor-version.module').then(m => m.JsaintdexArmorVersionModule),
      },
      {
        path: 'armor-version-attack',
        loadChildren: () => import('./armor-version-attack/armor-version-attack.module').then(m => m.JsaintdexArmorVersionAttackModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class JsaintdexEntityModule {}
