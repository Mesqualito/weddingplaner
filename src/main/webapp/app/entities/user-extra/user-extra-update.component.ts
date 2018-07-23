import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IUserExtra } from 'app/shared/model/user-extra.model';
import { UserExtraService } from './user-extra.service';
import { IUser, UserService } from 'app/core';
import { IAllowControl } from 'app/shared/model/allow-control.model';
import { AllowControlService } from 'app/entities/allow-control';
import { IMessage } from 'app/shared/model/message.model';
import { MessageService } from 'app/entities/message';

@Component({
    selector: 'jhi-user-extra-update',
    templateUrl: './user-extra-update.component.html'
})
export class UserExtraUpdateComponent implements OnInit {
    private _userExtra: IUserExtra;
    isSaving: boolean;

    users: IUser[];

    allowcontrols: IAllowControl[];

    messages: IMessage[];
    guestInvitationDateDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private userExtraService: UserExtraService,
        private userService: UserService,
        private allowControlService: AllowControlService,
        private messageService: MessageService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ userExtra }) => {
            this.userExtra = userExtra;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.allowControlService.query().subscribe(
            (res: HttpResponse<IAllowControl[]>) => {
                this.allowcontrols = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.messageService.query().subscribe(
            (res: HttpResponse<IMessage[]>) => {
                this.messages = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.userExtra.id !== undefined) {
            this.subscribeToSaveResponse(this.userExtraService.update(this.userExtra));
        } else {
            this.subscribeToSaveResponse(this.userExtraService.create(this.userExtra));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IUserExtra>>) {
        result.subscribe((res: HttpResponse<IUserExtra>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackAllowControlById(index: number, item: IAllowControl) {
        return item.id;
    }

    trackMessageById(index: number, item: IMessage) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get userExtra() {
        return this._userExtra;
    }

    set userExtra(userExtra: IUserExtra) {
        this._userExtra = userExtra;
    }
}
