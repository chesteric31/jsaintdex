import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IArmorVersion, ArmorVersion } from 'app/shared/model/armor-version.model';
import { ArmorVersionService } from './armor-version.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IArmor } from 'app/shared/model/armor.model';
import { ArmorService } from 'app/entities/armor/armor.service';

@Component({
  selector: 'jhi-armor-version-update',
  templateUrl: './armor-version-update.component.html',
})
export class ArmorVersionUpdateComponent implements OnInit {
  isSaving = false;
  armors: IArmor[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    image: [],
    imageContentType: [],
    armorId: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected armorVersionService: ArmorVersionService,
    protected armorService: ArmorService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ armorVersion }) => {
      this.updateForm(armorVersion);

      this.armorService.query().subscribe((res: HttpResponse<IArmor[]>) => (this.armors = res.body || []));
    });
  }

  updateForm(armorVersion: IArmorVersion): void {
    this.editForm.patchValue({
      id: armorVersion.id,
      name: armorVersion.name,
      image: armorVersion.image,
      imageContentType: armorVersion.imageContentType,
      armorId: armorVersion.armorId,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('jsaintdexApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const armorVersion = this.createFromForm();
    if (armorVersion.id !== undefined) {
      this.subscribeToSaveResponse(this.armorVersionService.update(armorVersion));
    } else {
      this.subscribeToSaveResponse(this.armorVersionService.create(armorVersion));
    }
  }

  private createFromForm(): IArmorVersion {
    return {
      ...new ArmorVersion(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      armorId: this.editForm.get(['armorId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArmorVersion>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IArmor): any {
    return item.id;
  }
}
