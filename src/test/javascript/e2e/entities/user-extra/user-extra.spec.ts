import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { UserExtraComponentsPage, UserExtraUpdatePage } from './user-extra.page-object';

describe('UserExtra e2e test', () => {
    let navBarPage: NavBarPage;
    let userExtraUpdatePage: UserExtraUpdatePage;
    let userExtraComponentsPage: UserExtraComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load UserExtras', () => {
        navBarPage.goToEntity('user-extra');
        userExtraComponentsPage = new UserExtraComponentsPage();
        expect(userExtraComponentsPage.getTitle()).toMatch(/weddingplanerApp.userExtra.home.title/);
    });

    it('should load create UserExtra page', () => {
        userExtraComponentsPage.clickOnCreateButton();
        userExtraUpdatePage = new UserExtraUpdatePage();
        expect(userExtraUpdatePage.getPageTitle()).toMatch(/weddingplanerApp.userExtra.home.createOrEditLabel/);
        userExtraUpdatePage.cancel();
    });

    /* it('should create and save UserExtras', () => {
        userExtraComponentsPage.clickOnCreateButton();
        userExtraUpdatePage.setCodeInput('code');
        expect(userExtraUpdatePage.getCodeInput()).toMatch('code');
        userExtraUpdatePage.setAddressLine1Input('addressLine1');
        expect(userExtraUpdatePage.getAddressLine1Input()).toMatch('addressLine1');
        userExtraUpdatePage.setAddressLine2Input('addressLine2');
        expect(userExtraUpdatePage.getAddressLine2Input()).toMatch('addressLine2');
        userExtraUpdatePage.setCityInput('city');
        expect(userExtraUpdatePage.getCityInput()).toMatch('city');
        userExtraUpdatePage.setZipCodeInput('zipCode');
        expect(userExtraUpdatePage.getZipCodeInput()).toMatch('zipCode');
        userExtraUpdatePage.setCountryInput('country');
        expect(userExtraUpdatePage.getCountryInput()).toMatch('country');
        userExtraUpdatePage.setBusinessPhoneNrInput('businessPhoneNr');
        expect(userExtraUpdatePage.getBusinessPhoneNrInput()).toMatch('businessPhoneNr');
        userExtraUpdatePage.setPrivatePhoneNrInput('privatePhoneNr');
        expect(userExtraUpdatePage.getPrivatePhoneNrInput()).toMatch('privatePhoneNr');
        userExtraUpdatePage.setMobilePhoneNrInput('mobilePhoneNr');
        expect(userExtraUpdatePage.getMobilePhoneNrInput()).toMatch('mobilePhoneNr');
        userExtraUpdatePage.setGuestInvitationDateInput('2000-12-31');
        expect(userExtraUpdatePage.getGuestInvitationDateInput()).toMatch('2000-12-31');
        userExtraUpdatePage.getGuestCommittedInput().isSelected().then((selected) => {
            if (selected) {
                userExtraUpdatePage.getGuestCommittedInput().click();
                expect(userExtraUpdatePage.getGuestCommittedInput().isSelected()).toBeFalsy();
            } else {
                userExtraUpdatePage.getGuestCommittedInput().click();
                expect(userExtraUpdatePage.getGuestCommittedInput().isSelected()).toBeTruthy();
            }
        });
        userExtraUpdatePage.genderSelectLastOption();
        userExtraUpdatePage.ageGroupSelectLastOption();
        userExtraUpdatePage.userSelectLastOption();
        userExtraUpdatePage.save();
        expect(userExtraUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
