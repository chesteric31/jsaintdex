import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IArmorStrength } from 'app/shared/model/armor-strength.model';

@Component({
  selector: 'jhi-armor-strength-detail',
  templateUrl: './armor-strength-detail.component.html',
})
export class ArmorStrengthDetailComponent implements OnInit {
  armorStrength: IArmorStrength | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ armorStrength }) => (this.armorStrength = armorStrength));
  }

  previousState(): void {
    window.history.back();
  }
}
