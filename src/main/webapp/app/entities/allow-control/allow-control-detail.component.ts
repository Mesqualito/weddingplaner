import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAllowControl } from 'app/shared/model/allow-control.model';

@Component({
    selector: 'jhi-allow-control-detail',
    templateUrl: './allow-control-detail.component.html'
})
export class AllowControlDetailComponent implements OnInit {
    allowControl: IAllowControl;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ allowControl }) => {
            this.allowControl = allowControl;
        });
    }

    previousState() {
        window.history.back();
    }
}
