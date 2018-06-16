import { BaseEntity, User } from './../../shared';

export class UserExtra implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public addressLine1?: string,
        public addressLine2?: string,
        public city?: string,
        public zipCode?: string,
        public country?: string,
        public businessPhoneNr?: string,
        public privatePhoneNr?: string,
        public mobilePhoneNr?: string,
        public guestInvitationDate?: any,
        public guestCommitted?: boolean,
        public user?: User,
        public partyFoods?: BaseEntity[],
        public messages?: BaseEntity[],
        public allowControls?: BaseEntity[],
    ) {
        this.guestCommitted = false;
    }
}
