import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IArmorVersionAttack } from 'app/shared/model/armor-version-attack.model';
import { ArmorVersionAttackService } from './armor-version-attack.service';

@Component({
  templateUrl: './armor-version-attack-delete-dialog.component.html',
})
export class ArmorVersionAttackDeleteDialogComponent {
  armorVersionAttack?: IArmorVersionAttack;

  constructor(
    protected armorVersionAttackService: ArmorVersionAttackService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.armorVersionAttackService.delete(id).subscribe(() => {
      this.eventManager.broadcast('armorVersionAttackListModification');
      this.activeModal.close();
    });
  }
}
