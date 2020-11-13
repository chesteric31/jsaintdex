import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IArmorVersionAttack, ArmorVersionAttack } from 'app/shared/model/armor-version-attack.model';
import { ArmorVersionAttackService } from './armor-version-attack.service';
import { ArmorVersionAttackComponent } from './armor-version-attack.component';
import { ArmorVersionAttackDetailComponent } from './armor-version-attack-detail.component';
import { ArmorVersionAttackUpdateComponent } from './armor-version-attack-update.component';

@Injectable({ providedIn: 'root' })
export class ArmorVersionAttackResolve implements Resolve<IArmorVersionAttack> {
  constructor(private service: ArmorVersionAttackService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IArmorVersionAttack> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((armorVersionAttack: HttpResponse<ArmorVersionAttack>) => {
          if (armorVersionAttack.body) {
            return of(armorVersionAttack.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ArmorVersionAttack());
  }
}

export const armorVersionAttackRoute: Routes = [
  {
    path: '',
    component: ArmorVersionAttackComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'jsaintdexApp.armorVersionAttack.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ArmorVersionAttackDetailComponent,
    resolve: {
      armorVersionAttack: ArmorVersionAttackResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jsaintdexApp.armorVersionAttack.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ArmorVersionAttackUpdateComponent,
    resolve: {
      armorVersionAttack: ArmorVersionAttackResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jsaintdexApp.armorVersionAttack.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ArmorVersionAttackUpdateComponent,
    resolve: {
      armorVersionAttack: ArmorVersionAttackResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jsaintdexApp.armorVersionAttack.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
