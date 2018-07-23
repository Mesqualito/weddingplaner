import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { PartyFood } from './party-food.model';
import { PartyFoodService } from './party-food.service';

@Component({
    selector: 'jhi-party-food-detail',
    templateUrl: './party-food-detail.component.html'
})
export class PartyFoodDetailComponent implements OnInit, OnDestroy {

    partyFood: PartyFood;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private partyFoodService: PartyFoodService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPartyFoods();
    }

    load(id) {
        this.partyFoodService.find(id)
            .subscribe((partyFoodResponse: HttpResponse<PartyFood>) => {
                this.partyFood = partyFoodResponse.body;
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

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPartyFoods() {
        this.eventSubscriber = this.eventManager.subscribe(
            'partyFoodListModification',
            (response) => this.load(this.partyFood.id)
        );
    }
}
