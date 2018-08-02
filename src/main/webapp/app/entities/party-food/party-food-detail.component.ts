import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPartyFood } from 'app/shared/model/party-food.model';

@Component({
    selector: 'jhi-party-food-detail',
    templateUrl: './party-food-detail.component.html'
})
export class PartyFoodDetailComponent implements OnInit {
    partyFood: IPartyFood;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ partyFood }) => {
            this.partyFood = partyFood;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
