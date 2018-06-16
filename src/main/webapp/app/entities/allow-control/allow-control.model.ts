import { BaseEntity } from './../../shared';

export const enum AllowGroup {
    'ADRESSE',
    'EMAIL',
    'TELEFON'
}

export class AllowControl implements BaseEntity {
    constructor(
        public id?: number,
        public allowGroup?: AllowGroup,
        public userExtras?: BaseEntity[],
    ) {
    }
}
