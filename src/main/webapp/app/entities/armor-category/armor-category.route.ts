import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IArmorCategory, ArmorCategory } from 'app/shared/model/armor-category.model';
import { ArmorCategoryService } from './armor-category.service';
import { ArmorCategoryComponent } from './armor-category.component';
import { ArmorCategoryDetailComponent } from './armor-category-detail.component';
import { ArmorCategoryUpdateComponent } from './armor-category-update.component';

@Injectable({ providedIn: 'root' })
export class ArmorCategoryResolve implements Resolve<IArmorCategory> {
  constructor(private service: ArmorCategoryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IArmorCategory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((armorCategory: HttpResponse<ArmorCategory>) => {
          if (armorCategory.body) {
            return of(armorCategory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ArmorCategory());
  }
}

export const armorCategoryRoute: Routes = [
  {
    path: '',
    component: ArmorCategoryComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'jsaintdexApp.armorCategory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ArmorCategoryDetailComponent,
    resolve: {
      armorCategory: ArmorCategoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jsaintdexApp.armorCategory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ArmorCategoryUpdateComponent,
    resolve: {
      armorCategory: ArmorCategoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jsaintdexApp.armorCategory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ArmorCategoryUpdateComponent,
    resolve: {
      armorCategory: ArmorCategoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jsaintdexApp.armorCategory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
