import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { WeddingplanerUserExtraModule } from './user-extra/user-extra.module';
import { WeddingplanerAllowControlModule } from './allow-control/allow-control.module';
import { WeddingplanerPartyFoodModule } from './party-food/party-food.module';
import { WeddingplanerMessageModule } from './message/message.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        WeddingplanerUserExtraModule,
        WeddingplanerAllowControlModule,
        WeddingplanerPartyFoodModule,
        WeddingplanerMessageModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WeddingplanerEntityModule {}
