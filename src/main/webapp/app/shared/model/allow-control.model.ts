import { IUserExtra } from 'app/shared/model/user-extra.model';

export const enum AllowGroup {
    ADRESSE = 'ADRESSE',
    EMAIL = 'EMAIL',
    TELEFON = 'TELEFON'
}

export interface IAllowControl {
    id?: number;
    allowGroup?: AllowGroup;
    controlledGroups?: IUserExtra[];
    controlGroup?: IUserExtra;
}

export class AllowControl implements IAllowControl {
    constructor(
        public id?: number,
        public allowGroup?: AllowGroup,
        public controlledGroups?: IUserExtra[],
        public controlGroup?: IUserExtra
    ) {}
}
