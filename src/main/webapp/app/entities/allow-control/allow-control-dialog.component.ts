import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AllowControl } from './allow-control.model';
import { AllowControlPopupService } from './allow-control-popup.service';
import { AllowControlService } from './allow-control.service';
import { UserExtra, UserExtraService } from '../user-extra';

@Component({
    selector: 'jhi-allow-control-dialog',
    templateUrl: './allow-control-dialog.component.html'
})
export class AllowControlDialogComponent implements OnInit {

    allowControl: AllowControl;
    isSaving: boolean;

    userextras: UserExtra[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private allowControlService: AllowControlService,
        private userExtraService: UserExtraService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userExtraService.query()
            .subscribe((res: HttpResponse<UserExtra[]>) => { this.userextras = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.allowControl.id !== undefined) {
            this.subscribeToSaveResponse(
                this.allowControlService.update(this.allowControl));
        } else {
            this.subscribeToSaveResponse(
                this.allowControlService.create(this.allowControl));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<AllowControl>>) {
        result.subscribe((res: HttpResponse<AllowControl>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: AllowControl) {
        this.eventManager.broadcast({ name: 'allowControlListModification', content: 'OK'});
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

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-allow-control-popup',
    template: ''
})
export class AllowControlPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private allowControlPopupService: AllowControlPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.allowControlPopupService
                    .open(AllowControlDialogComponent as Component, params['id']);
            } else {
                this.allowControlPopupService
                    .open(AllowControlDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
