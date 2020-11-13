import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IArmorCategory } from 'app/shared/model/armor-category.model';

@Component({
  selector: 'jhi-armor-category-detail',
  templateUrl: './armor-category-detail.component.html',
})
export class ArmorCategoryDetailComponent implements OnInit {
  armorCategory: IArmorCategory | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ armorCategory }) => (this.armorCategory = armorCategory));
  }

  previousState(): void {
    window.history.back();
  }
}
