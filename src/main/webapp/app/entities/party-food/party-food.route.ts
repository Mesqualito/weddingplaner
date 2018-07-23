import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { PartyFoodComponent } from './party-food.component';
import { PartyFoodDetailComponent } from './party-food-detail.component';
import { PartyFoodPopupComponent } from './party-food-dialog.component';
import { PartyFoodDeletePopupComponent } from './party-food-delete-dialog.component';

@Injectable()
export class PartyFoodResolvePagingParams implements Resolve<any> {

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

export const partyFoodRoute: Routes = [
    {
        path: 'party-food',
        component: PartyFoodComponent,
        resolve: {
            'pagingParams': PartyFoodResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.partyFood.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'party-food/:id',
        component: PartyFoodDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.partyFood.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const partyFoodPopupRoute: Routes = [
    {
        path: 'party-food-new',
        component: PartyFoodPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.partyFood.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'party-food/:id/edit',
        component: PartyFoodPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.partyFood.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'party-food/:id/delete',
        component: PartyFoodDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.partyFood.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
