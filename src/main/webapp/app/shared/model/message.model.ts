import { Moment } from 'moment';
import { IUserExtra } from 'app/shared/model/user-extra.model';

export interface IMessage {
    id?: number;
    messageShortDescription?: string;
    messageInitTime?: Moment;
    messageText?: any;
    messageValidFrom?: Moment;
    messageValidUntil?: Moment;
    imageContentType?: string;
    image?: any;
    tos?: IUserExtra[];
    from?: IUserExtra;
}

export class Message implements IMessage {
    constructor(
        public id?: number,
        public messageShortDescription?: string,
        public messageInitTime?: Moment,
        public messageText?: any,
        public messageValidFrom?: Moment,
        public messageValidUntil?: Moment,
        public imageContentType?: string,
        public image?: any,
        public tos?: IUserExtra[],
        public from?: IUserExtra
    ) {}
}
