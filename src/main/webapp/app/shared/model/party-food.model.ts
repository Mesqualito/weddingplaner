import { Moment } from 'moment';
import { IUserExtra } from 'app/shared/model/user-extra.model';

export interface IPartyFood {
    id?: number;
    foodName?: string;
    foodShortDescription?: string;
    foodLongDescription?: any;
    foodQuantityPersons?: number;
    foodBestServeTime?: Moment;
    foodProposalAccepted?: boolean;
    userExtra?: IUserExtra;
}

export class PartyFood implements IPartyFood {
    constructor(
        public id?: number,
        public foodName?: string,
        public foodShortDescription?: string,
        public foodLongDescription?: any,
        public foodQuantityPersons?: number,
        public foodBestServeTime?: Moment,
        public foodProposalAccepted?: boolean,
        public userExtra?: IUserExtra
    ) {
        this.foodProposalAccepted = false;
    }
}
