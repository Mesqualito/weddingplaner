import { BaseEntity } from './../../shared';

export class Message implements BaseEntity {
    constructor(
        public id?: number,
        public messageShortDescription?: string,
        public messageInitTime?: any,
        public messageText?: any,
        public messageValidFrom?: any,
        public messageValidUntil?: any,
        public from?: BaseEntity,
        public tos?: BaseEntity[],
    ) {
    }
}
