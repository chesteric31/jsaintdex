import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IStrength, Strength } from 'app/shared/model/strength.model';
import { StrengthService } from './strength.service';

@Component({
  selector: 'jhi-strength-update',
  templateUrl: './strength-update.component.html',
})
export class StrengthUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
  });

  constructor(protected strengthService: StrengthService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ strength }) => {
      this.updateForm(strength);
    });
  }

  updateForm(strength: IStrength): void {
    this.editForm.patchValue({
      id: strength.id,
      name: strength.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const strength = this.createFromForm();
    if (strength.id !== undefined) {
      this.subscribeToSaveResponse(this.strengthService.update(strength));
    } else {
      this.subscribeToSaveResponse(this.strengthService.create(strength));
    }
  }

  private createFromForm(): IStrength {
    return {
      ...new Strength(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStrength>>): void {
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
}
