import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStrength } from 'app/shared/model/strength.model';
import { StrengthService } from './strength.service';

@Component({
  templateUrl: './strength-delete-dialog.component.html',
})
export class StrengthDeleteDialogComponent {
  strength?: IStrength;

  constructor(protected strengthService: StrengthService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.strengthService.delete(id).subscribe(() => {
      this.eventManager.broadcast('strengthListModification');
      this.activeModal.close();
    });
  }
}
