import { element, by, promise, ElementFinder } from 'protractor';

export class MessageComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-message div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class MessageUpdatePage {
    pageTitle = element(by.id('jhi-message-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    messageShortDescriptionInput = element(by.id('field_messageShortDescription'));
    messageInitTimeInput = element(by.id('field_messageInitTime'));
    messageTextInput = element(by.id('field_messageText'));
    messageValidFromInput = element(by.id('field_messageValidFrom'));
    messageValidUntilInput = element(by.id('field_messageValidUntil'));
    imageInput = element(by.id('file_image'));
    toSelect = element(by.id('field_to'));
    fromSelect = element(by.id('field_from'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setMessageShortDescriptionInput(messageShortDescription): promise.Promise<void> {
        return this.messageShortDescriptionInput.sendKeys(messageShortDescription);
    }

    getMessageShortDescriptionInput() {
        return this.messageShortDescriptionInput.getAttribute('value');
    }

    setMessageInitTimeInput(messageInitTime): promise.Promise<void> {
        return this.messageInitTimeInput.sendKeys(messageInitTime);
    }

    getMessageInitTimeInput() {
        return this.messageInitTimeInput.getAttribute('value');
    }

    setMessageTextInput(messageText): promise.Promise<void> {
        return this.messageTextInput.sendKeys(messageText);
    }

    getMessageTextInput() {
        return this.messageTextInput.getAttribute('value');
    }

    setMessageValidFromInput(messageValidFrom): promise.Promise<void> {
        return this.messageValidFromInput.sendKeys(messageValidFrom);
    }

    getMessageValidFromInput() {
        return this.messageValidFromInput.getAttribute('value');
    }

    setMessageValidUntilInput(messageValidUntil): promise.Promise<void> {
        return this.messageValidUntilInput.sendKeys(messageValidUntil);
    }

    getMessageValidUntilInput() {
        return this.messageValidUntilInput.getAttribute('value');
    }

    setImageInput(image): promise.Promise<void> {
        return this.imageInput.sendKeys(image);
    }

    getImageInput() {
        return this.imageInput.getAttribute('value');
    }

    toSelectLastOption(): promise.Promise<void> {
        return this.toSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    toSelectOption(option): promise.Promise<void> {
        return this.toSelect.sendKeys(option);
    }

    getToSelect(): ElementFinder {
        return this.toSelect;
    }

    getToSelectedOption() {
        return this.toSelect.element(by.css('option:checked')).getText();
    }

    fromSelectLastOption(): promise.Promise<void> {
        return this.fromSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    fromSelectOption(option): promise.Promise<void> {
        return this.fromSelect.sendKeys(option);
    }

    getFromSelect(): ElementFinder {
        return this.fromSelect;
    }

    getFromSelectedOption() {
        return this.fromSelect.element(by.css('option:checked')).getText();
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
