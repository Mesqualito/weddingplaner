import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AccordionModule, AutoCompleteModule} from 'primeng/primeng';

import {WeddingplanerSharedModule} from 'app/shared';

import {
    MessageComponent,
    MessageDeleteDialogComponent,
    MessageDeletePopupComponent,
    MessageDetailComponent,
    messagePopupRoute,
    messageRoute,
    MessageUpdateComponent
} from './';

const ENTITY_STATES = [...messageRoute, ...messagePopupRoute];

@NgModule({
    imports: [
        WeddingplanerSharedModule,
        BrowserAnimationsModule,
        AutoCompleteModule,
        AccordionModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MessageComponent,
        MessageDetailComponent,
        MessageUpdateComponent,
        MessageDeleteDialogComponent,
        MessageDeletePopupComponent
    ],
    entryComponents: [MessageComponent, MessageUpdateComponent, MessageDeleteDialogComponent, MessageDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WeddingplanerMessageModule {}
