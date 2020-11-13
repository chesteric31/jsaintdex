import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IArmorVersionAttack } from 'app/shared/model/armor-version-attack.model';

@Component({
  selector: 'jhi-armor-version-attack-detail',
  templateUrl: './armor-version-attack-detail.component.html',
})
export class ArmorVersionAttackDetailComponent implements OnInit {
  armorVersionAttack: IArmorVersionAttack | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ armorVersionAttack }) => (this.armorVersionAttack = armorVersionAttack));
  }

  previousState(): void {
    window.history.back();
  }
}
