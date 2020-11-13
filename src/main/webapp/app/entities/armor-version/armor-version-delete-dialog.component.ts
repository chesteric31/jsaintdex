import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IArmorVersion } from 'app/shared/model/armor-version.model';
import { ArmorVersionService } from './armor-version.service';

@Component({
  templateUrl: './armor-version-delete-dialog.component.html',
})
export class ArmorVersionDeleteDialogComponent {
  armorVersion?: IArmorVersion;

  constructor(
    protected armorVersionService: ArmorVersionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.armorVersionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('armorVersionListModification');
      this.activeModal.close();
    });
  }
}
