import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IArmor } from 'app/shared/model/armor.model';
import { ArmorService } from './armor.service';

@Component({
  templateUrl: './armor-delete-dialog.component.html',
})
export class ArmorDeleteDialogComponent {
  armor?: IArmor;

  constructor(protected armorService: ArmorService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.armorService.delete(id).subscribe(() => {
      this.eventManager.broadcast('armorListModification');
      this.activeModal.close();
    });
  }
}
