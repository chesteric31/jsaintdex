import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IArmorStrength, ArmorStrength } from 'app/shared/model/armor-strength.model';
import { ArmorStrengthService } from './armor-strength.service';
import { IStrength } from 'app/shared/model/strength.model';
import { StrengthService } from 'app/entities/strength/strength.service';
import { IArmor } from 'app/shared/model/armor.model';
import { ArmorService } from 'app/entities/armor/armor.service';

type SelectableEntity = IStrength | IArmor;

@Component({
  selector: 'jhi-armor-strength-update',
  templateUrl: './armor-strength-update.component.html',
})
export class ArmorStrengthUpdateComponent implements OnInit {
  isSaving = false;
  strengths: IStrength[] = [];
  armors: IArmor[] = [];

  editForm = this.fb.group({
    id: [],
    value: [],
    strengthId: [],
    armorId: [],
  });

  constructor(
    protected armorStrengthService: ArmorStrengthService,
    protected strengthService: StrengthService,
    protected armorService: ArmorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ armorStrength }) => {
      this.updateForm(armorStrength);

      this.strengthService.query().subscribe((res: HttpResponse<IStrength[]>) => (this.strengths = res.body || []));

      this.armorService.query().subscribe((res: HttpResponse<IArmor[]>) => (this.armors = res.body || []));
    });
  }

  updateForm(armorStrength: IArmorStrength): void {
    this.editForm.patchValue({
      id: armorStrength.id,
      value: armorStrength.value,
      strengthId: armorStrength.strengthId,
      armorId: armorStrength.armorId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const armorStrength = this.createFromForm();
    if (armorStrength.id !== undefined) {
      this.subscribeToSaveResponse(this.armorStrengthService.update(armorStrength));
    } else {
      this.subscribeToSaveResponse(this.armorStrengthService.create(armorStrength));
    }
  }

  private createFromForm(): IArmorStrength {
    return {
      ...new ArmorStrength(),
      id: this.editForm.get(['id'])!.value,
      value: this.editForm.get(['value'])!.value,
      strengthId: this.editForm.get(['strengthId'])!.value,
      armorId: this.editForm.get(['armorId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArmorStrength>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
