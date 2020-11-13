import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IArmorVersion } from 'app/shared/model/armor-version.model';

@Component({
  selector: 'jhi-armor-version-detail',
  templateUrl: './armor-version-detail.component.html',
})
export class ArmorVersionDetailComponent implements OnInit {
  armorVersion: IArmorVersion | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ armorVersion }) => (this.armorVersion = armorVersion));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
