import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IPartyFood } from 'app/shared/model/party-food.model';
import { PartyFoodService } from './party-food.service';
import { IUserExtra } from 'app/shared/model/user-extra.model';
import { UserExtraService } from 'app/entities/user-extra';

@Component({
    selector: 'jhi-party-food-update',
    templateUrl: './party-food-update.component.html'
})
export class PartyFoodUpdateComponent implements OnInit {
    private _partyFood: IPartyFood;
    isSaving: boolean;

    userextras: IUserExtra[];
    foodBestServeTime: string;

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private partyFoodService: PartyFoodService,
        private userExtraService: UserExtraService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ partyFood }) => {
            this.partyFood = partyFood;
        });
        this.userExtraService.query().subscribe(
            (res: HttpResponse<IUserExtra[]>) => {
                this.userextras = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.partyFood.foodBestServeTime = moment(this.foodBestServeTime, DATE_TIME_FORMAT);
        if (this.partyFood.id !== undefined) {
            this.subscribeToSaveResponse(this.partyFoodService.update(this.partyFood));
        } else {
            this.subscribeToSaveResponse(this.partyFoodService.create(this.partyFood));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPartyFood>>) {
        result.subscribe((res: HttpResponse<IPartyFood>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get partyFood() {
        return this._partyFood;
    }

    set partyFood(partyFood: IPartyFood) {
        this._partyFood = partyFood;
        this.foodBestServeTime = moment(partyFood.foodBestServeTime).format(DATE_TIME_FORMAT);
    }
}
