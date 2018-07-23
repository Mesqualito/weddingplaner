import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WeddingplanerSharedModule } from 'app/shared';
import {
    AllowControlComponent,
    AllowControlDetailComponent,
    AllowControlUpdateComponent,
    AllowControlDeletePopupComponent,
    AllowControlDeleteDialogComponent,
    allowControlRoute,
    allowControlPopupRoute
} from './';

const ENTITY_STATES = [...allowControlRoute, ...allowControlPopupRoute];

@NgModule({
    imports: [WeddingplanerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AllowControlComponent,
        AllowControlDetailComponent,
        AllowControlUpdateComponent,
        AllowControlDeleteDialogComponent,
        AllowControlDeletePopupComponent
    ],
    entryComponents: [
        AllowControlComponent,
        AllowControlUpdateComponent,
        AllowControlDeleteDialogComponent,
        AllowControlDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WeddingplanerAllowControlModule {}
