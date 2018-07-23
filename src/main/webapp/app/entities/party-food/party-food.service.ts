import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { PartyFood } from './party-food.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PartyFood>;

@Injectable()
export class PartyFoodService {

    private resourceUrl =  SERVER_API_URL + 'api/party-foods';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/party-foods';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(partyFood: PartyFood): Observable<EntityResponseType> {
        const copy = this.convert(partyFood);
        return this.http.post<PartyFood>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(partyFood: PartyFood): Observable<EntityResponseType> {
        const copy = this.convert(partyFood);
        return this.http.put<PartyFood>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PartyFood>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PartyFood[]>> {
        const options = createRequestOption(req);
        return this.http.get<PartyFood[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PartyFood[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<PartyFood[]>> {
        const options = createRequestOption(req);
        return this.http.get<PartyFood[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PartyFood[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PartyFood = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PartyFood[]>): HttpResponse<PartyFood[]> {
        const jsonResponse: PartyFood[] = res.body;
        const body: PartyFood[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PartyFood.
     */
    private convertItemFromServer(partyFood: PartyFood): PartyFood {
        const copy: PartyFood = Object.assign({}, partyFood);
        copy.foodBestServeTime = this.dateUtils
            .convertDateTimeFromServer(partyFood.foodBestServeTime);
        return copy;
    }

    /**
     * Convert a PartyFood to a JSON which can be sent to the server.
     */
    private convert(partyFood: PartyFood): PartyFood {
        const copy: PartyFood = Object.assign({}, partyFood);

        copy.foodBestServeTime = this.dateUtils.toDate(partyFood.foodBestServeTime);
        return copy;
    }
}
