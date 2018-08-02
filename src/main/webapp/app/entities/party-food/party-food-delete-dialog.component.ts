import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPartyFood } from 'app/shared/model/party-food.model';
import { PartyFoodService } from './party-food.service';

@Component({
    selector: 'jhi-party-food-delete-dialog',
    templateUrl: './party-food-delete-dialog.component.html'
})
export class PartyFoodDeleteDialogComponent {
    partyFood: IPartyFood;

    constructor(private partyFoodService: PartyFoodService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.partyFoodService.delete(id).subscribe(response => {
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
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ partyFood }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PartyFoodDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.partyFood = partyFood;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
