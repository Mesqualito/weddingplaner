import { BaseEntity, User } from './../../shared';

export const enum Gender {
    'FEMALE',
    'MALE'
}

export const enum AgeGroup {
    'PRE_BOUNCER_CASTLE',
    'BOUNCER_CASTLE',
    'POST_BOUNCER_CASTLE'
}

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
        public gender?: Gender,
        public ageGroup?: AgeGroup,
        public user?: User,
        public owners?: BaseEntity[],
        public partyFoods?: BaseEntity[],
        public ownedMessages?: BaseEntity[],
        public allowedUsers?: BaseEntity[],
        public receivedMessages?: BaseEntity[],
    ) {
        this.guestCommitted = false;
    }
}
