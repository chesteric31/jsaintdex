import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IArmorCategory, ArmorCategory } from 'app/shared/model/armor-category.model';
import { ArmorCategoryService } from './armor-category.service';

@Component({
  selector: 'jhi-armor-category-update',
  templateUrl: './armor-category-update.component.html',
})
export class ArmorCategoryUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
  });

  constructor(protected armorCategoryService: ArmorCategoryService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ armorCategory }) => {
      this.updateForm(armorCategory);
    });
  }

  updateForm(armorCategory: IArmorCategory): void {
    this.editForm.patchValue({
      id: armorCategory.id,
      name: armorCategory.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const armorCategory = this.createFromForm();
    if (armorCategory.id !== undefined) {
      this.subscribeToSaveResponse(this.armorCategoryService.update(armorCategory));
    } else {
      this.subscribeToSaveResponse(this.armorCategoryService.create(armorCategory));
    }
  }

  private createFromForm(): IArmorCategory {
    return {
      ...new ArmorCategory(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArmorCategory>>): void {
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
