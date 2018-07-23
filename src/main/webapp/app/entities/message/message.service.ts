import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMessage } from 'app/shared/model/message.model';

type EntityResponseType = HttpResponse<IMessage>;
type EntityArrayResponseType = HttpResponse<IMessage[]>;

@Injectable({ providedIn: 'root' })
export class MessageService {
    private resourceUrl = SERVER_API_URL + 'api/messages';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/messages';

    constructor(private http: HttpClient) {}

    create(message: IMessage): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(message);
        return this.http
            .post<IMessage>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(message: IMessage): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(message);
        return this.http
            .put<IMessage>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IMessage>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IMessage[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IMessage[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    private convertDateFromClient(message: IMessage): IMessage {
        const copy: IMessage = Object.assign({}, message, {
            messageInitTime: message.messageInitTime != null && message.messageInitTime.isValid() ? message.messageInitTime.toJSON() : null,
            messageValidFrom:
                message.messageValidFrom != null && message.messageValidFrom.isValid() ? message.messageValidFrom.toJSON() : null,
            messageValidUntil:
                message.messageValidUntil != null && message.messageValidUntil.isValid() ? message.messageValidUntil.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.messageInitTime = res.body.messageInitTime != null ? moment(res.body.messageInitTime) : null;
        res.body.messageValidFrom = res.body.messageValidFrom != null ? moment(res.body.messageValidFrom) : null;
        res.body.messageValidUntil = res.body.messageValidUntil != null ? moment(res.body.messageValidUntil) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((message: IMessage) => {
            message.messageInitTime = message.messageInitTime != null ? moment(message.messageInitTime) : null;
            message.messageValidFrom = message.messageValidFrom != null ? moment(message.messageValidFrom) : null;
            message.messageValidUntil = message.messageValidUntil != null ? moment(message.messageValidUntil) : null;
        });
        return res;
    }
}
