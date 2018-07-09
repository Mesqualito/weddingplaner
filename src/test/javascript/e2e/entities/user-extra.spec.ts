import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('UserExtra e2e test', () => {

    let navBarPage: NavBarPage;
    let userExtraDialogPage: UserExtraDialogPage;
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
        expect(userExtraComponentsPage.getTitle())
            .toMatch(/weddingplanerApp.userExtra.home.title/);

    });

    it('should load create UserExtra dialog', () => {
        userExtraComponentsPage.clickOnCreateButton();
        userExtraDialogPage = new UserExtraDialogPage();
        expect(userExtraDialogPage.getModalTitle())
            .toMatch(/weddingplanerApp.userExtra.home.createOrEditLabel/);
        userExtraDialogPage.close();
    });

   /* it('should create and save UserExtras', () => {
        userExtraComponentsPage.clickOnCreateButton();
        userExtraDialogPage.setCodeInput('code');
        expect(userExtraDialogPage.getCodeInput()).toMatch('code');
        userExtraDialogPage.setAddressLine1Input('addressLine1');
        expect(userExtraDialogPage.getAddressLine1Input()).toMatch('addressLine1');
        userExtraDialogPage.setAddressLine2Input('addressLine2');
        expect(userExtraDialogPage.getAddressLine2Input()).toMatch('addressLine2');
        userExtraDialogPage.setCityInput('city');
        expect(userExtraDialogPage.getCityInput()).toMatch('city');
        userExtraDialogPage.setZipCodeInput('zipCode');
        expect(userExtraDialogPage.getZipCodeInput()).toMatch('zipCode');
        userExtraDialogPage.setCountryInput('country');
        expect(userExtraDialogPage.getCountryInput()).toMatch('country');
        userExtraDialogPage.setBusinessPhoneNrInput('businessPhoneNr');
        expect(userExtraDialogPage.getBusinessPhoneNrInput()).toMatch('businessPhoneNr');
        userExtraDialogPage.setPrivatePhoneNrInput('privatePhoneNr');
        expect(userExtraDialogPage.getPrivatePhoneNrInput()).toMatch('privatePhoneNr');
        userExtraDialogPage.setMobilePhoneNrInput('mobilePhoneNr');
        expect(userExtraDialogPage.getMobilePhoneNrInput()).toMatch('mobilePhoneNr');
        userExtraDialogPage.setGuestInvitationDateInput('2000-12-31');
        expect(userExtraDialogPage.getGuestInvitationDateInput()).toMatch('2000-12-31');
        userExtraDialogPage.getGuestCommittedInput().isSelected().then((selected) => {
            if (selected) {
                userExtraDialogPage.getGuestCommittedInput().click();
                expect(userExtraDialogPage.getGuestCommittedInput().isSelected()).toBeFalsy();
            } else {
                userExtraDialogPage.getGuestCommittedInput().click();
                expect(userExtraDialogPage.getGuestCommittedInput().isSelected()).toBeTruthy();
            }
        });
        userExtraDialogPage.genderSelectLastOption();
        userExtraDialogPage.ageGroupSelectLastOption();
        userExtraDialogPage.userSelectLastOption();
        userExtraDialogPage.save();
        expect(userExtraDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class UserExtraComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-user-extra div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class UserExtraDialogPage {
    modalTitle = element(by.css('h4#myUserExtraLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    codeInput = element(by.css('input#field_code'));
    addressLine1Input = element(by.css('input#field_addressLine1'));
    addressLine2Input = element(by.css('input#field_addressLine2'));
    cityInput = element(by.css('input#field_city'));
    zipCodeInput = element(by.css('input#field_zipCode'));
    countryInput = element(by.css('input#field_country'));
    businessPhoneNrInput = element(by.css('input#field_businessPhoneNr'));
    privatePhoneNrInput = element(by.css('input#field_privatePhoneNr'));
    mobilePhoneNrInput = element(by.css('input#field_mobilePhoneNr'));
    guestInvitationDateInput = element(by.css('input#field_guestInvitationDate'));
    guestCommittedInput = element(by.css('input#field_guestCommitted'));
    genderSelect = element(by.css('select#field_gender'));
    ageGroupSelect = element(by.css('select#field_ageGroup'));
    userSelect = element(by.css('select#field_user'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setCodeInput = function(code) {
        this.codeInput.sendKeys(code);
    };

    getCodeInput = function() {
        return this.codeInput.getAttribute('value');
    };

    setAddressLine1Input = function(addressLine1) {
        this.addressLine1Input.sendKeys(addressLine1);
    };

    getAddressLine1Input = function() {
        return this.addressLine1Input.getAttribute('value');
    };

    setAddressLine2Input = function(addressLine2) {
        this.addressLine2Input.sendKeys(addressLine2);
    };

    getAddressLine2Input = function() {
        return this.addressLine2Input.getAttribute('value');
    };

    setCityInput = function(city) {
        this.cityInput.sendKeys(city);
    };

    getCityInput = function() {
        return this.cityInput.getAttribute('value');
    };

    setZipCodeInput = function(zipCode) {
        this.zipCodeInput.sendKeys(zipCode);
    };

    getZipCodeInput = function() {
        return this.zipCodeInput.getAttribute('value');
    };

    setCountryInput = function(country) {
        this.countryInput.sendKeys(country);
    };

    getCountryInput = function() {
        return this.countryInput.getAttribute('value');
    };

    setBusinessPhoneNrInput = function(businessPhoneNr) {
        this.businessPhoneNrInput.sendKeys(businessPhoneNr);
    };

    getBusinessPhoneNrInput = function() {
        return this.businessPhoneNrInput.getAttribute('value');
    };

    setPrivatePhoneNrInput = function(privatePhoneNr) {
        this.privatePhoneNrInput.sendKeys(privatePhoneNr);
    };

    getPrivatePhoneNrInput = function() {
        return this.privatePhoneNrInput.getAttribute('value');
    };

    setMobilePhoneNrInput = function(mobilePhoneNr) {
        this.mobilePhoneNrInput.sendKeys(mobilePhoneNr);
    };

    getMobilePhoneNrInput = function() {
        return this.mobilePhoneNrInput.getAttribute('value');
    };

    setGuestInvitationDateInput = function(guestInvitationDate) {
        this.guestInvitationDateInput.sendKeys(guestInvitationDate);
    };

    getGuestInvitationDateInput = function() {
        return this.guestInvitationDateInput.getAttribute('value');
    };

    getGuestCommittedInput = function() {
        return this.guestCommittedInput;
    };
    setGenderSelect = function(gender) {
        this.genderSelect.sendKeys(gender);
    };

    getGenderSelect = function() {
        return this.genderSelect.element(by.css('option:checked')).getText();
    };

    genderSelectLastOption = function() {
        this.genderSelect.all(by.tagName('option')).last().click();
    };
    setAgeGroupSelect = function(ageGroup) {
        this.ageGroupSelect.sendKeys(ageGroup);
    };

    getAgeGroupSelect = function() {
        return this.ageGroupSelect.element(by.css('option:checked')).getText();
    };

    ageGroupSelectLastOption = function() {
        this.ageGroupSelect.all(by.tagName('option')).last().click();
    };
    userSelectLastOption = function() {
        this.userSelect.all(by.tagName('option')).last().click();
    };

    userSelectOption = function(option) {
        this.userSelect.sendKeys(option);
    };

    getUserSelect = function() {
        return this.userSelect;
    };

    getUserSelectedOption = function() {
        return this.userSelect.element(by.css('option:checked')).getText();
    };

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
