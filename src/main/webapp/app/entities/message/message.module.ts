import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

<<<<<<< HEAD
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AutoCompleteModule, AccordionModule } from 'primeng/primeng';

import { WeddingplanerSharedModule } from '../../shared';
=======
import { WeddingplanerSharedModule } from 'app/shared';
>>>>>>> jhipster_upgrade
import {
    MessageComponent,
    MessageDetailComponent,
    MessageUpdateComponent,
    MessageDeletePopupComponent,
    MessageDeleteDialogComponent,
    messageRoute,
    messagePopupRoute
} from './';

const ENTITY_STATES = [...messageRoute, ...messagePopupRoute];

@NgModule({
<<<<<<< HEAD
    imports: [
        WeddingplanerSharedModule,
        BrowserAnimationsModule,
        AutoCompleteModule,
        AccordionModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
=======
    imports: [WeddingplanerSharedModule, RouterModule.forChild(ENTITY_STATES)],
>>>>>>> jhipster_upgrade
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
