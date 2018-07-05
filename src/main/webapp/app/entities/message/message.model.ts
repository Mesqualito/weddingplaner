import { BaseEntity } from './../../shared';

export class Message implements BaseEntity {
    constructor(
        public id?: number,
        public messageShortDescription?: string,
        public messageInitTime?: any,
        public messageText?: any,
        public messageValidFrom?: any,
        public messageValidUntil?: any,
        public imageContentType?: string,
        public image?: any,
        public tos?: BaseEntity[],
        public from?: BaseEntity,
    ) {
    }
}
