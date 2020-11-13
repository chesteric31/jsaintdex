import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStrength } from 'app/shared/model/strength.model';

@Component({
  selector: 'jhi-strength-detail',
  templateUrl: './strength-detail.component.html',
})
export class StrengthDetailComponent implements OnInit {
  strength: IStrength | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ strength }) => (this.strength = strength));
  }

  previousState(): void {
    window.history.back();
  }
}
