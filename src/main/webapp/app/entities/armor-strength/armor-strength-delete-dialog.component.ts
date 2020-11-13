import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IArmorStrength } from 'app/shared/model/armor-strength.model';
import { ArmorStrengthService } from './armor-strength.service';

@Component({
  templateUrl: './armor-strength-delete-dialog.component.html',
})
export class ArmorStrengthDeleteDialogComponent {
  armorStrength?: IArmorStrength;

  constructor(
    protected armorStrengthService: ArmorStrengthService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.armorStrengthService.delete(id).subscribe(() => {
      this.eventManager.broadcast('armorStrengthListModification');
      this.activeModal.close();
    });
  }
}
