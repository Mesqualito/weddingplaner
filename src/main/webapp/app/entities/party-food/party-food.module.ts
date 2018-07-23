import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WeddingplanerSharedModule } from '../../shared';
import {
    PartyFoodService,
    PartyFoodPopupService,
    PartyFoodComponent,
    PartyFoodDetailComponent,
    PartyFoodDialogComponent,
    PartyFoodPopupComponent,
    PartyFoodDeletePopupComponent,
    PartyFoodDeleteDialogComponent,
    partyFoodRoute,
    partyFoodPopupRoute,
    PartyFoodResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...partyFoodRoute,
    ...partyFoodPopupRoute,
];

@NgModule({
    imports: [
        WeddingplanerSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PartyFoodComponent,
        PartyFoodDetailComponent,
        PartyFoodDialogComponent,
        PartyFoodDeleteDialogComponent,
        PartyFoodPopupComponent,
        PartyFoodDeletePopupComponent,
    ],
    entryComponents: [
        PartyFoodComponent,
        PartyFoodDialogComponent,
        PartyFoodPopupComponent,
        PartyFoodDeleteDialogComponent,
        PartyFoodDeletePopupComponent,
    ],
    providers: [
        PartyFoodService,
        PartyFoodPopupService,
        PartyFoodResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WeddingplanerPartyFoodModule {}
