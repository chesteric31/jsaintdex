import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IArmor, Armor } from 'app/shared/model/armor.model';
import { ArmorService } from './armor.service';
import { ArmorComponent } from './armor.component';
import { ArmorDetailComponent } from './armor-detail.component';
import { ArmorUpdateComponent } from './armor-update.component';

@Injectable({ providedIn: 'root' })
export class ArmorResolve implements Resolve<IArmor> {
  constructor(private service: ArmorService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IArmor> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((armor: HttpResponse<Armor>) => {
          if (armor.body) {
            return of(armor.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Armor());
  }
}

export const armorRoute: Routes = [
  {
    path: '',
    component: ArmorComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'jsaintdexApp.armor.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ArmorDetailComponent,
    resolve: {
      armor: ArmorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jsaintdexApp.armor.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ArmorUpdateComponent,
    resolve: {
      armor: ArmorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jsaintdexApp.armor.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ArmorUpdateComponent,
    resolve: {
      armor: ArmorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jsaintdexApp.armor.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
