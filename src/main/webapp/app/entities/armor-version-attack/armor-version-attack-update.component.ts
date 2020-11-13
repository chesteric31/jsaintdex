import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IArmorVersionAttack, ArmorVersionAttack } from 'app/shared/model/armor-version-attack.model';
import { ArmorVersionAttackService } from './armor-version-attack.service';
import { IArmorVersion } from 'app/shared/model/armor-version.model';
import { ArmorVersionService } from 'app/entities/armor-version/armor-version.service';

@Component({
  selector: 'jhi-armor-version-attack-update',
  templateUrl: './armor-version-attack-update.component.html',
})
export class ArmorVersionAttackUpdateComponent implements OnInit {
  isSaving = false;
  armorversions: IArmorVersion[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    versionId: [],
  });

  constructor(
    protected armorVersionAttackService: ArmorVersionAttackService,
    protected armorVersionService: ArmorVersionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ armorVersionAttack }) => {
      this.updateForm(armorVersionAttack);

      this.armorVersionService.query().subscribe((res: HttpResponse<IArmorVersion[]>) => (this.armorversions = res.body || []));
    });
  }

  updateForm(armorVersionAttack: IArmorVersionAttack): void {
    this.editForm.patchValue({
      id: armorVersionAttack.id,
      name: armorVersionAttack.name,
      versionId: armorVersionAttack.versionId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const armorVersionAttack = this.createFromForm();
    if (armorVersionAttack.id !== undefined) {
      this.subscribeToSaveResponse(this.armorVersionAttackService.update(armorVersionAttack));
    } else {
      this.subscribeToSaveResponse(this.armorVersionAttackService.create(armorVersionAttack));
    }
  }

  private createFromForm(): IArmorVersionAttack {
    return {
      ...new ArmorVersionAttack(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      versionId: this.editForm.get(['versionId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArmorVersionAttack>>): void {
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

  trackById(index: number, item: IArmorVersion): any {
    return item.id;
  }
}
