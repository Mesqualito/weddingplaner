import { element, by, promise, ElementFinder } from 'protractor';

export class UserExtraComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-user-extra div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class UserExtraUpdatePage {
    pageTitle = element(by.id('jhi-user-extra-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    codeInput = element(by.id('field_code'));
    addressLine1Input = element(by.id('field_addressLine1'));
    addressLine2Input = element(by.id('field_addressLine2'));
    cityInput = element(by.id('field_city'));
    zipCodeInput = element(by.id('field_zipCode'));
    countryInput = element(by.id('field_country'));
    businessPhoneNrInput = element(by.id('field_businessPhoneNr'));
    privatePhoneNrInput = element(by.id('field_privatePhoneNr'));
    mobilePhoneNrInput = element(by.id('field_mobilePhoneNr'));
    guestInvitationDateInput = element(by.id('field_guestInvitationDate'));
    guestCommittedInput = element(by.id('field_guestCommitted'));
    genderSelect = element(by.id('field_gender'));
    ageGroupSelect = element(by.id('field_ageGroup'));
    userSelect = element(by.id('field_user'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setCodeInput(code): promise.Promise<void> {
        return this.codeInput.sendKeys(code);
    }

    getCodeInput() {
        return this.codeInput.getAttribute('value');
    }

    setAddressLine1Input(addressLine1): promise.Promise<void> {
        return this.addressLine1Input.sendKeys(addressLine1);
    }

    getAddressLine1Input() {
        return this.addressLine1Input.getAttribute('value');
    }

    setAddressLine2Input(addressLine2): promise.Promise<void> {
        return this.addressLine2Input.sendKeys(addressLine2);
    }

    getAddressLine2Input() {
        return this.addressLine2Input.getAttribute('value');
    }

    setCityInput(city): promise.Promise<void> {
        return this.cityInput.sendKeys(city);
    }

    getCityInput() {
        return this.cityInput.getAttribute('value');
    }

    setZipCodeInput(zipCode): promise.Promise<void> {
        return this.zipCodeInput.sendKeys(zipCode);
    }

    getZipCodeInput() {
        return this.zipCodeInput.getAttribute('value');
    }

    setCountryInput(country): promise.Promise<void> {
        return this.countryInput.sendKeys(country);
    }

    getCountryInput() {
        return this.countryInput.getAttribute('value');
    }

    setBusinessPhoneNrInput(businessPhoneNr): promise.Promise<void> {
        return this.businessPhoneNrInput.sendKeys(businessPhoneNr);
    }

    getBusinessPhoneNrInput() {
        return this.businessPhoneNrInput.getAttribute('value');
    }

    setPrivatePhoneNrInput(privatePhoneNr): promise.Promise<void> {
        return this.privatePhoneNrInput.sendKeys(privatePhoneNr);
    }

    getPrivatePhoneNrInput() {
        return this.privatePhoneNrInput.getAttribute('value');
    }

    setMobilePhoneNrInput(mobilePhoneNr): promise.Promise<void> {
        return this.mobilePhoneNrInput.sendKeys(mobilePhoneNr);
    }

    getMobilePhoneNrInput() {
        return this.mobilePhoneNrInput.getAttribute('value');
    }

    setGuestInvitationDateInput(guestInvitationDate): promise.Promise<void> {
        return this.guestInvitationDateInput.sendKeys(guestInvitationDate);
    }

    getGuestInvitationDateInput() {
        return this.guestInvitationDateInput.getAttribute('value');
    }

    getGuestCommittedInput() {
        return this.guestCommittedInput;
    }
    setGenderSelect(gender): promise.Promise<void> {
        return this.genderSelect.sendKeys(gender);
    }

    getGenderSelect() {
        return this.genderSelect.element(by.css('option:checked')).getText();
    }

    genderSelectLastOption(): promise.Promise<void> {
        return this.genderSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }
    setAgeGroupSelect(ageGroup): promise.Promise<void> {
        return this.ageGroupSelect.sendKeys(ageGroup);
    }

    getAgeGroupSelect() {
        return this.ageGroupSelect.element(by.css('option:checked')).getText();
    }

    ageGroupSelectLastOption(): promise.Promise<void> {
        return this.ageGroupSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }
    userSelectLastOption(): promise.Promise<void> {
        return this.userSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    userSelectOption(option): promise.Promise<void> {
        return this.userSelect.sendKeys(option);
    }

    getUserSelect(): ElementFinder {
        return this.userSelect;
    }

    getUserSelectedOption() {
        return this.userSelect.element(by.css('option:checked')).getText();
    }

    save(): promise.Promise<void> {
        return this.saveButton.click();
    }

    cancel(): promise.Promise<void> {
        return this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}
