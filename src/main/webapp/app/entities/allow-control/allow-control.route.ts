import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { AllowControlComponent } from './allow-control.component';
import { AllowControlDetailComponent } from './allow-control-detail.component';
import { AllowControlPopupComponent } from './allow-control-dialog.component';
import { AllowControlDeletePopupComponent } from './allow-control-delete-dialog.component';

@Injectable()
export class AllowControlResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const allowControlRoute: Routes = [
    {
        path: 'allow-control',
        component: AllowControlComponent,
        resolve: {
            'pagingParams': AllowControlResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.allowControl.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'allow-control/:id',
        component: AllowControlDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.allowControl.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const allowControlPopupRoute: Routes = [
    {
        path: 'allow-control-new',
        component: AllowControlPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.allowControl.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'allow-control/:id/edit',
        component: AllowControlPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.allowControl.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'allow-control/:id/delete',
        component: AllowControlDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.allowControl.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
