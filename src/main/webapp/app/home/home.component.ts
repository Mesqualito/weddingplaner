import {Component, OnInit} from '@angular/core';
import {NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager, JhiAlertService} from 'ng-jhipster';

<<<<<<< HEAD
import {Principal} from '../shared';
import { Router } from '@angular/router';

import { LoginService } from '../shared/login/login.service';
import { StateStorageService } from '../shared/auth/state-storage.service';
=======
import { LoginModalService, Principal, Account } from 'app/core';
>>>>>>> jhipster_upgrade

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit {
    authenticationError: boolean;
    account: Account;
    modalRef: NgbModalRef;
    name: string;
    vorname: string;
    code: string;

<<<<<<< HEAD
    constructor(
        private principal: Principal,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private loginService: LoginService,
        private stateStorageService: StateStorageService,
        private router: Router
    ) {
    }
=======
    constructor(private principal: Principal, private loginModalService: LoginModalService, private eventManager: JhiEventManager) {}
>>>>>>> jhipster_upgrade

    ngOnInit() {
        this.principal.identity().then(account => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.principal.identity().then(account => {
                this.account = account;
            });
        });
    }

    parseLogin() {
        this.loginService.login({
            username: this.vorname + '.' + this.name,
            password: this.code,
            rememberMe: false
        }).then(() => {

            this.authenticationError = false;
            // this.activeModal.dismiss('login success');
            if (this.router.url === '/register' || (/^\/activate\//.test(this.router.url)) ||
                (/^\/reset\//.test(this.router.url))) {
                this.router.navigate(['']);
            }

            this.eventManager.broadcast({
                name: 'authenticationSuccess',
                content: 'Sending Authentication Success'
            });

            // // previousState was set in the authExpiredInterceptor before being redirected to login modal.
            // // since login is succesful, go to stored previousState and clear previousState
            const redirect = this.stateStorageService.getUrl();
            if (redirect) {
                this.stateStorageService.storeUrl(null);
                this.router.navigate([redirect]);
            }
        }).catch(() => {
            this.authenticationError = true;
        });

    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

}
