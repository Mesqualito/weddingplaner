import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUserExtra } from 'app/shared/model/user-extra.model';

type EntityResponseType = HttpResponse<IUserExtra>;
type EntityArrayResponseType = HttpResponse<IUserExtra[]>;

@Injectable({ providedIn: 'root' })
export class UserExtraService {
    private resourceUrl = SERVER_API_URL + 'api/user-extras';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/user-extras';

    constructor(private http: HttpClient) {}

    create(userExtra: IUserExtra): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(userExtra);
        return this.http
            .post<IUserExtra>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(userExtra: IUserExtra): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(userExtra);
        return this.http
            .put<IUserExtra>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IUserExtra>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IUserExtra[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IUserExtra[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    private convertDateFromClient(userExtra: IUserExtra): IUserExtra {
        const copy: IUserExtra = Object.assign({}, userExtra, {
            guestInvitationDate:
                userExtra.guestInvitationDate != null && userExtra.guestInvitationDate.isValid()
                    ? userExtra.guestInvitationDate.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.guestInvitationDate = res.body.guestInvitationDate != null ? moment(res.body.guestInvitationDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((userExtra: IUserExtra) => {
            userExtra.guestInvitationDate = userExtra.guestInvitationDate != null ? moment(userExtra.guestInvitationDate) : null;
        });
        return res;
    }
}
