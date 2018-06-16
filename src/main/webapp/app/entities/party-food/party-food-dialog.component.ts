import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { PartyFood } from './party-food.model';
import { PartyFoodPopupService } from './party-food-popup.service';
import { PartyFoodService } from './party-food.service';
import { UserExtra, UserExtraService } from '../user-extra';

@Component({
    selector: 'jhi-party-food-dialog',
    templateUrl: './party-food-dialog.component.html'
})
export class PartyFoodDialogComponent implements OnInit {

    partyFood: PartyFood;
    isSaving: boolean;

    userextras: UserExtra[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private partyFoodService: PartyFoodService,
        private userExtraService: UserExtraService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userExtraService.query()
            .subscribe((res: HttpResponse<UserExtra[]>) => { this.userextras = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.partyFood.id !== undefined) {
            this.subscribeToSaveResponse(
                this.partyFoodService.update(this.partyFood));
        } else {
            this.subscribeToSaveResponse(
                this.partyFoodService.create(this.partyFood));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<PartyFood>>) {
        result.subscribe((res: HttpResponse<PartyFood>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: PartyFood) {
        this.eventManager.broadcast({ name: 'partyFoodListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserExtraById(index: number, item: UserExtra) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-party-food-popup',
    template: ''
})
export class PartyFoodPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private partyFoodPopupService: PartyFoodPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.partyFoodPopupService
                    .open(PartyFoodDialogComponent as Component, params['id']);
            } else {
                this.partyFoodPopupService
                    .open(PartyFoodDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
