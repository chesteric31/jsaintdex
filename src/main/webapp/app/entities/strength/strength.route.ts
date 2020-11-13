import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IStrength, Strength } from 'app/shared/model/strength.model';
import { StrengthService } from './strength.service';
import { StrengthComponent } from './strength.component';
import { StrengthDetailComponent } from './strength-detail.component';
import { StrengthUpdateComponent } from './strength-update.component';

@Injectable({ providedIn: 'root' })
export class StrengthResolve implements Resolve<IStrength> {
  constructor(private service: StrengthService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStrength> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((strength: HttpResponse<Strength>) => {
          if (strength.body) {
            return of(strength.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Strength());
  }
}

export const strengthRoute: Routes = [
  {
    path: '',
    component: StrengthComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'jsaintdexApp.strength.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StrengthDetailComponent,
    resolve: {
      strength: StrengthResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jsaintdexApp.strength.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StrengthUpdateComponent,
    resolve: {
      strength: StrengthResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jsaintdexApp.strength.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StrengthUpdateComponent,
    resolve: {
      strength: StrengthResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jsaintdexApp.strength.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
