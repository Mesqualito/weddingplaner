import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AllowControl } from './allow-control.model';
import { AllowControlPopupService } from './allow-control-popup.service';
import { AllowControlService } from './allow-control.service';

@Component({
    selector: 'jhi-allow-control-delete-dialog',
    templateUrl: './allow-control-delete-dialog.component.html'
})
export class AllowControlDeleteDialogComponent {

    allowControl: AllowControl;

    constructor(
        private allowControlService: AllowControlService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.allowControlService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'allowControlListModification',
                content: 'Deleted an allowControl'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-allow-control-delete-popup',
    template: ''
})
export class AllowControlDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private allowControlPopupService: AllowControlPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.allowControlPopupService
                .open(AllowControlDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
