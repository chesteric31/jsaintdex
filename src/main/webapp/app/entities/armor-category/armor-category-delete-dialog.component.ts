import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IArmorCategory } from 'app/shared/model/armor-category.model';
import { ArmorCategoryService } from './armor-category.service';

@Component({
  templateUrl: './armor-category-delete-dialog.component.html',
})
export class ArmorCategoryDeleteDialogComponent {
  armorCategory?: IArmorCategory;

  constructor(
    protected armorCategoryService: ArmorCategoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.armorCategoryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('armorCategoryListModification');
      this.activeModal.close();
    });
  }
}
