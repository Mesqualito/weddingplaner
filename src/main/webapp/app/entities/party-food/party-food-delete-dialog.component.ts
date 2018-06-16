import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PartyFood } from './party-food.model';
import { PartyFoodPopupService } from './party-food-popup.service';
import { PartyFoodService } from './party-food.service';

@Component({
    selector: 'jhi-party-food-delete-dialog',
    templateUrl: './party-food-delete-dialog.component.html'
})
export class PartyFoodDeleteDialogComponent {

    partyFood: PartyFood;

    constructor(
        private partyFoodService: PartyFoodService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.partyFoodService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'partyFoodListModification',
                content: 'Deleted an partyFood'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-party-food-delete-popup',
    template: ''
})
export class PartyFoodDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private partyFoodPopupService: PartyFoodPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.partyFoodPopupService
                .open(PartyFoodDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
