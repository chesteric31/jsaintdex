import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IArmorVersion, ArmorVersion } from 'app/shared/model/armor-version.model';
import { ArmorVersionService } from './armor-version.service';
import { ArmorVersionComponent } from './armor-version.component';
import { ArmorVersionDetailComponent } from './armor-version-detail.component';
import { ArmorVersionUpdateComponent } from './armor-version-update.component';

@Injectable({ providedIn: 'root' })
export class ArmorVersionResolve implements Resolve<IArmorVersion> {
  constructor(private service: ArmorVersionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IArmorVersion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((armorVersion: HttpResponse<ArmorVersion>) => {
          if (armorVersion.body) {
            return of(armorVersion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ArmorVersion());
  }
}

export const armorVersionRoute: Routes = [
  {
    path: '',
    component: ArmorVersionComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'jsaintdexApp.armorVersion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ArmorVersionDetailComponent,
    resolve: {
      armorVersion: ArmorVersionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jsaintdexApp.armorVersion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ArmorVersionUpdateComponent,
    resolve: {
      armorVersion: ArmorVersionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jsaintdexApp.armorVersion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ArmorVersionUpdateComponent,
    resolve: {
      armorVersion: ArmorVersionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jsaintdexApp.armorVersion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
