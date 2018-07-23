import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AutoCompleteModule } from 'primeng/primeng';

import { WeddingplanerSharedModule } from '../../shared';
import {
    AllowControlService,
    AllowControlPopupService,
    AllowControlComponent,
    AllowControlDetailComponent,
    AllowControlDialogComponent,
    AllowControlPopupComponent,
    AllowControlDeletePopupComponent,
    AllowControlDeleteDialogComponent,
    allowControlRoute,
    allowControlPopupRoute,
    AllowControlResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...allowControlRoute,
    ...allowControlPopupRoute,
];

@NgModule({
    imports: [
        WeddingplanerSharedModule,
        BrowserAnimationsModule,
        AutoCompleteModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AllowControlComponent,
        AllowControlDetailComponent,
        AllowControlDialogComponent,
        AllowControlDeleteDialogComponent,
        AllowControlPopupComponent,
        AllowControlDeletePopupComponent,
    ],
    entryComponents: [
        AllowControlComponent,
        AllowControlDialogComponent,
        AllowControlPopupComponent,
        AllowControlDeleteDialogComponent,
        AllowControlDeletePopupComponent,
    ],
    providers: [
        AllowControlService,
        AllowControlPopupService,
        AllowControlResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WeddingplanerAllowControlModule {}
