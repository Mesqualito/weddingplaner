import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPartyFood } from 'app/shared/model/party-food.model';

type EntityResponseType = HttpResponse<IPartyFood>;
type EntityArrayResponseType = HttpResponse<IPartyFood[]>;

@Injectable({ providedIn: 'root' })
export class PartyFoodService {
    private resourceUrl = SERVER_API_URL + 'api/party-foods';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/party-foods';

    constructor(private http: HttpClient) {}

    create(partyFood: IPartyFood): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(partyFood);
        return this.http
            .post<IPartyFood>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(partyFood: IPartyFood): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(partyFood);
        return this.http
            .put<IPartyFood>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPartyFood>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPartyFood[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPartyFood[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    private convertDateFromClient(partyFood: IPartyFood): IPartyFood {
        const copy: IPartyFood = Object.assign({}, partyFood, {
            foodBestServeTime:
                partyFood.foodBestServeTime != null && partyFood.foodBestServeTime.isValid() ? partyFood.foodBestServeTime.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.foodBestServeTime = res.body.foodBestServeTime != null ? moment(res.body.foodBestServeTime) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((partyFood: IPartyFood) => {
            partyFood.foodBestServeTime = partyFood.foodBestServeTime != null ? moment(partyFood.foodBestServeTime) : null;
        });
        return res;
    }
}
