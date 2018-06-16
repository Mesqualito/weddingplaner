import { BaseEntity } from './../../shared';

export class PartyFood implements BaseEntity {
    constructor(
        public id?: number,
        public foodName?: string,
        public foodShortDescription?: string,
        public foodLongDescription?: any,
        public foodQuantityPersons?: number,
        public foodBestServeTime?: any,
        public foodProposalAccepted?: boolean,
        public userExtra?: BaseEntity,
    ) {
        this.foodProposalAccepted = false;
    }
}
