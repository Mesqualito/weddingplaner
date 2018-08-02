import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAllowControl } from 'app/shared/model/allow-control.model';
import { AllowControlService } from './allow-control.service';

@Component({
    selector: 'jhi-allow-control-delete-dialog',
    templateUrl: './allow-control-delete-dialog.component.html'
})
export class AllowControlDeleteDialogComponent {
    allowControl: IAllowControl;

    constructor(
        private allowControlService: AllowControlService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.allowControlService.delete(id).subscribe(response => {
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
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ allowControl }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AllowControlDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.allowControl = allowControl;
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
