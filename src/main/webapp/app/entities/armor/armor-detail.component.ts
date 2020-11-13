import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IArmor } from 'app/shared/model/armor.model';

@Component({
  selector: 'jhi-armor-detail',
  templateUrl: './armor-detail.component.html',
})
export class ArmorDetailComponent implements OnInit {
  armor: IArmor | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ armor }) => (this.armor = armor));
  }

  previousState(): void {
    window.history.back();
  }
}
