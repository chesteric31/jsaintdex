import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IArmor, Armor } from 'app/shared/model/armor.model';
import { ArmorService } from './armor.service';
import { IArmorCategory } from 'app/shared/model/armor-category.model';
import { ArmorCategoryService } from 'app/entities/armor-category/armor-category.service';

@Component({
  selector: 'jhi-armor-update',
  templateUrl: './armor-update.component.html',
})
export class ArmorUpdateComponent implements OnInit {
  isSaving = false;
  armorcategories: IArmorCategory[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    categoryId: [],
  });

  constructor(
    protected armorService: ArmorService,
    protected armorCategoryService: ArmorCategoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ armor }) => {
      this.updateForm(armor);

      this.armorCategoryService.query().subscribe((res: HttpResponse<IArmorCategory[]>) => (this.armorcategories = res.body || []));
    });
  }

  updateForm(armor: IArmor): void {
    this.editForm.patchValue({
      id: armor.id,
      name: armor.name,
      categoryId: armor.categoryId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const armor = this.createFromForm();
    if (armor.id !== undefined) {
      this.subscribeToSaveResponse(this.armorService.update(armor));
    } else {
      this.subscribeToSaveResponse(this.armorService.create(armor));
    }
  }

  private createFromForm(): IArmor {
    return {
      ...new Armor(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      categoryId: this.editForm.get(['categoryId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArmor>>): void {
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

  trackById(index: number, item: IArmorCategory): any {
    return item.id;
  }
}
