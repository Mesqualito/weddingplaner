import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAllowControl } from 'app/shared/model/allow-control.model';
import { AllowControlService } from './allow-control.service';
import { IUserExtra } from 'app/shared/model/user-extra.model';
import { UserExtraService } from 'app/entities/user-extra';

@Component({
    selector: 'jhi-allow-control-update',
    templateUrl: './allow-control-update.component.html'
})
export class AllowControlUpdateComponent implements OnInit {
    private _allowControl: IAllowControl;
    isSaving: boolean;

    userextras: IUserExtra[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private allowControlService: AllowControlService,
        private userExtraService: UserExtraService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ allowControl }) => {
            this.allowControl = allowControl;
        });
        this.userExtraService.query().subscribe(
            (res: HttpResponse<IUserExtra[]>) => {
                this.userextras = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.allowControl.id !== undefined) {
            this.subscribeToSaveResponse(this.allowControlService.update(this.allowControl));
        } else {
            this.subscribeToSaveResponse(this.allowControlService.create(this.allowControl));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAllowControl>>) {
        result.subscribe((res: HttpResponse<IAllowControl>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserExtraById(index: number, item: IUserExtra) {
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
    get allowControl() {
        return this._allowControl;
    }

    set allowControl(allowControl: IAllowControl) {
        this._allowControl = allowControl;
    }
}
