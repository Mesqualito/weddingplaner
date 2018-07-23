import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { AllowControl } from './allow-control.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<AllowControl>;

@Injectable()
export class AllowControlService {

    private resourceUrl =  SERVER_API_URL + 'api/allow-controls';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/allow-controls';

    constructor(private http: HttpClient) { }

    create(allowControl: AllowControl): Observable<EntityResponseType> {
        const copy = this.convert(allowControl);
        return this.http.post<AllowControl>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(allowControl: AllowControl): Observable<EntityResponseType> {
        const copy = this.convert(allowControl);
        return this.http.put<AllowControl>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<AllowControl>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<AllowControl[]>> {
        const options = createRequestOption(req);
        return this.http.get<AllowControl[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<AllowControl[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<AllowControl[]>> {
        const options = createRequestOption(req);
        return this.http.get<AllowControl[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<AllowControl[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: AllowControl = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<AllowControl[]>): HttpResponse<AllowControl[]> {
        const jsonResponse: AllowControl[] = res.body;
        const body: AllowControl[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to AllowControl.
     */
    private convertItemFromServer(allowControl: AllowControl): AllowControl {
        const copy: AllowControl = Object.assign({}, allowControl);
        return copy;
    }

    /**
     * Convert a AllowControl to a JSON which can be sent to the server.
     */
    private convert(allowControl: AllowControl): AllowControl {
        const copy: AllowControl = Object.assign({}, allowControl);
        return copy;
    }
}
