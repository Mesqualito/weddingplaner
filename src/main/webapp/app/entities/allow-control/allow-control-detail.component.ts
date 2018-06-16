import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { AllowControl } from './allow-control.model';
import { AllowControlService } from './allow-control.service';

@Component({
    selector: 'jhi-allow-control-detail',
    templateUrl: './allow-control-detail.component.html'
})
export class AllowControlDetailComponent implements OnInit, OnDestroy {

    allowControl: AllowControl;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private allowControlService: AllowControlService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAllowControls();
    }

    load(id) {
        this.allowControlService.find(id)
            .subscribe((allowControlResponse: HttpResponse<AllowControl>) => {
                this.allowControl = allowControlResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAllowControls() {
        this.eventSubscriber = this.eventManager.subscribe(
            'allowControlListModification',
            (response) => this.load(this.allowControl.id)
        );
    }
}
