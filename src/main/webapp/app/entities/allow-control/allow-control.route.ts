import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AllowControl } from 'app/shared/model/allow-control.model';
import { AllowControlService } from './allow-control.service';
import { AllowControlComponent } from './allow-control.component';
import { AllowControlDetailComponent } from './allow-control-detail.component';
import { AllowControlUpdateComponent } from './allow-control-update.component';
import { AllowControlDeletePopupComponent } from './allow-control-delete-dialog.component';
import { IAllowControl } from 'app/shared/model/allow-control.model';

@Injectable({ providedIn: 'root' })
export class AllowControlResolve implements Resolve<IAllowControl> {
    constructor(private service: AllowControlService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((allowControl: HttpResponse<AllowControl>) => allowControl.body));
        }
        return of(new AllowControl());
    }
}

export const allowControlRoute: Routes = [
    {
        path: 'allow-control',
        component: AllowControlComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'weddingplanerApp.allowControl.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'allow-control/:id/view',
        component: AllowControlDetailComponent,
        resolve: {
            allowControl: AllowControlResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.allowControl.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'allow-control/new',
        component: AllowControlUpdateComponent,
        resolve: {
            allowControl: AllowControlResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.allowControl.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'allow-control/:id/edit',
        component: AllowControlUpdateComponent,
        resolve: {
            allowControl: AllowControlResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.allowControl.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const allowControlPopupRoute: Routes = [
    {
        path: 'allow-control/:id/delete',
        component: AllowControlDeletePopupComponent,
        resolve: {
            allowControl: AllowControlResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'weddingplanerApp.allowControl.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
