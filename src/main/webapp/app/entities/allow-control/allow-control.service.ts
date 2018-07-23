import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAllowControl } from 'app/shared/model/allow-control.model';

type EntityResponseType = HttpResponse<IAllowControl>;
type EntityArrayResponseType = HttpResponse<IAllowControl[]>;

@Injectable({ providedIn: 'root' })
export class AllowControlService {
    private resourceUrl = SERVER_API_URL + 'api/allow-controls';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/allow-controls';

    constructor(private http: HttpClient) {}

    create(allowControl: IAllowControl): Observable<EntityResponseType> {
        return this.http.post<IAllowControl>(this.resourceUrl, allowControl, { observe: 'response' });
    }

    update(allowControl: IAllowControl): Observable<EntityResponseType> {
        return this.http.put<IAllowControl>(this.resourceUrl, allowControl, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAllowControl>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAllowControl[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAllowControl[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
