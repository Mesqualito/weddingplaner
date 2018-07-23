import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

<<<<<<< HEAD
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AutoCompleteModule } from 'primeng/primeng';

import { WeddingplanerSharedModule } from '../../shared';
=======
import { WeddingplanerSharedModule } from 'app/shared';
>>>>>>> jhipster_upgrade
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
<<<<<<< HEAD
    imports: [
        WeddingplanerSharedModule,
        BrowserAnimationsModule,
        AutoCompleteModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
=======
    imports: [WeddingplanerSharedModule, RouterModule.forChild(ENTITY_STATES)],
>>>>>>> jhipster_upgrade
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
