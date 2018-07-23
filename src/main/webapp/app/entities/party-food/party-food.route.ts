import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PartyFood } from 'app/shared/model/party-food.model';
import { PartyFoodService } from './party-food.service';
import { PartyFoodComponent } from './party-food.component';
import { PartyFoodDetailComponent } from './party-food-detail.component';
import { PartyFoodUpdateComponent } from './party-food-update.component';
import { PartyFoodDeletePopupComponent } from './party-food-delete-dialog.component';
import { IPartyFood } from 'app/shared/model/party-food.model';

@Injectable({ providedIn: 'root' })
export class PartyFoodResolve implements Resolve<IPartyFood> {
    constructor(private service: PartyFoodService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((partyFood: HttpResponse<PartyFood>) => partyFood.body));
        }
        return of(new PartyFood());
    }
}

export const partyFoodRoute: Routes = [
    {
        path: 'party-food',
        component: PartyFoodComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'weddingplanerApp.partyFood.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'party-food/:id/view',
        component: PartyFoodDetailComponent,
        resolve: {
            partyFood: PartyFoodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.partyFood.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'party-food/new',
        component: PartyFoodUpdateComponent,
        resolve: {
            partyFood: PartyFoodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.partyFood.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'party-food/:id/edit',
        component: PartyFoodUpdateComponent,
        resolve: {
            partyFood: PartyFoodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.partyFood.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const partyFoodPopupRoute: Routes = [
    {
        path: 'party-food/:id/delete',
        component: PartyFoodDeletePopupComponent,
        resolve: {
            partyFood: PartyFoodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.partyFood.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
