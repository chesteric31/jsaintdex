import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IArmorStrength, ArmorStrength } from 'app/shared/model/armor-strength.model';
import { ArmorStrengthService } from './armor-strength.service';
import { ArmorStrengthComponent } from './armor-strength.component';
import { ArmorStrengthDetailComponent } from './armor-strength-detail.component';
import { ArmorStrengthUpdateComponent } from './armor-strength-update.component';

@Injectable({ providedIn: 'root' })
export class ArmorStrengthResolve implements Resolve<IArmorStrength> {
  constructor(private service: ArmorStrengthService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IArmorStrength> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((armorStrength: HttpResponse<ArmorStrength>) => {
          if (armorStrength.body) {
            return of(armorStrength.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ArmorStrength());
  }
}

export const armorStrengthRoute: Routes = [
  {
    path: '',
    component: ArmorStrengthComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'jsaintdexApp.armorStrength.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ArmorStrengthDetailComponent,
    resolve: {
      armorStrength: ArmorStrengthResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jsaintdexApp.armorStrength.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ArmorStrengthUpdateComponent,
    resolve: {
      armorStrength: ArmorStrengthResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jsaintdexApp.armorStrength.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ArmorStrengthUpdateComponent,
    resolve: {
      armorStrength: ArmorStrengthResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jsaintdexApp.armorStrength.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
