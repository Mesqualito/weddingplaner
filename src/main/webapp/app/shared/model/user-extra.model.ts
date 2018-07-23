import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IAllowControl } from 'app/shared/model/allow-control.model';
import { IPartyFood } from 'app/shared/model/party-food.model';
import { IMessage } from 'app/shared/model/message.model';

export const enum Gender {
    FEMALE = 'FEMALE',
    MALE = 'MALE'
}

export const enum AgeGroup {
    PRE_BOUNCER_CASTLE = 'PRE_BOUNCER_CASTLE',
    BOUNCER_CASTLE = 'BOUNCER_CASTLE',
    POST_BOUNCER_CASTLE = 'POST_BOUNCER_CASTLE'
}

export interface IUserExtra {
    id?: number;
    code?: string;
    addressLine1?: string;
    addressLine2?: string;
    city?: string;
    zipCode?: string;
    country?: string;
    businessPhoneNr?: string;
    privatePhoneNr?: string;
    mobilePhoneNr?: string;
    guestInvitationDate?: Moment;
    guestCommitted?: boolean;
    gender?: Gender;
    ageGroup?: AgeGroup;
    user?: IUser;
    owners?: IAllowControl[];
    partyFoods?: IPartyFood[];
    ownedMessages?: IMessage[];
    allowedUsers?: IAllowControl[];
    receivedMessages?: IMessage[];
}

export class UserExtra implements IUserExtra {
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
        public guestInvitationDate?: Moment,
        public guestCommitted?: boolean,
        public gender?: Gender,
        public ageGroup?: AgeGroup,
        public user?: IUser,
        public owners?: IAllowControl[],
        public partyFoods?: IPartyFood[],
        public ownedMessages?: IMessage[],
        public allowedUsers?: IAllowControl[],
        public receivedMessages?: IMessage[]
    ) {
        this.guestCommitted = false;
    }
}
