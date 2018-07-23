import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WeddingplanerSharedModule } from 'app/shared';
import {
    PartyFoodComponent,
    PartyFoodDetailComponent,
    PartyFoodUpdateComponent,
    PartyFoodDeletePopupComponent,
    PartyFoodDeleteDialogComponent,
    partyFoodRoute,
    partyFoodPopupRoute
} from './';

const ENTITY_STATES = [...partyFoodRoute, ...partyFoodPopupRoute];

@NgModule({
    imports: [WeddingplanerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PartyFoodComponent,
        PartyFoodDetailComponent,
        PartyFoodUpdateComponent,
        PartyFoodDeleteDialogComponent,
        PartyFoodDeletePopupComponent
    ],
    entryComponents: [PartyFoodComponent, PartyFoodUpdateComponent, PartyFoodDeleteDialogComponent, PartyFoodDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WeddingplanerPartyFoodModule {}
