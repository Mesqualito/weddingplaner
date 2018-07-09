import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
import * as path from 'path';
describe('Message e2e test', () => {

    let navBarPage: NavBarPage;
    let messageDialogPage: MessageDialogPage;
    let messageComponentsPage: MessageComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Messages', () => {
        navBarPage.goToEntity('message');
        messageComponentsPage = new MessageComponentsPage();
        expect(messageComponentsPage.getTitle())
            .toMatch(/weddingplanerApp.message.home.title/);

    });

    it('should load create Message dialog', () => {
        messageComponentsPage.clickOnCreateButton();
        messageDialogPage = new MessageDialogPage();
        expect(messageDialogPage.getModalTitle())
            .toMatch(/weddingplanerApp.message.home.createOrEditLabel/);
        messageDialogPage.close();
    });

   /* it('should create and save Messages', () => {
        messageComponentsPage.clickOnCreateButton();
        messageDialogPage.setMessageShortDescriptionInput('messageShortDescription');
        expect(messageDialogPage.getMessageShortDescriptionInput()).toMatch('messageShortDescription');
        messageDialogPage.setMessageInitTimeInput(12310020012301);
        expect(messageDialogPage.getMessageInitTimeInput()).toMatch('2001-12-31T02:30');
        messageDialogPage.setMessageTextInput('messageText');
        expect(messageDialogPage.getMessageTextInput()).toMatch('messageText');
        messageDialogPage.setMessageValidFromInput(12310020012301);
        expect(messageDialogPage.getMessageValidFromInput()).toMatch('2001-12-31T02:30');
        messageDialogPage.setMessageValidUntilInput(12310020012301);
        expect(messageDialogPage.getMessageValidUntilInput()).toMatch('2001-12-31T02:30');
        messageDialogPage.setImageInput(absolutePath);
        // messageDialogPage.toSelectLastOption();
        messageDialogPage.fromSelectLastOption();
        messageDialogPage.save();
        expect(messageDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class MessageComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-message div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class MessageDialogPage {
    modalTitle = element(by.css('h4#myMessageLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    messageShortDescriptionInput = element(by.css('input#field_messageShortDescription'));
    messageInitTimeInput = element(by.css('input#field_messageInitTime'));
    messageTextInput = element(by.css('textarea#field_messageText'));
    messageValidFromInput = element(by.css('input#field_messageValidFrom'));
    messageValidUntilInput = element(by.css('input#field_messageValidUntil'));
    imageInput = element(by.css('input#file_image'));
    toSelect = element(by.css('select#field_to'));
    fromSelect = element(by.css('select#field_from'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setMessageShortDescriptionInput = function(messageShortDescription) {
        this.messageShortDescriptionInput.sendKeys(messageShortDescription);
    };

    getMessageShortDescriptionInput = function() {
        return this.messageShortDescriptionInput.getAttribute('value');
    };

    setMessageInitTimeInput = function(messageInitTime) {
        this.messageInitTimeInput.sendKeys(messageInitTime);
    };

    getMessageInitTimeInput = function() {
        return this.messageInitTimeInput.getAttribute('value');
    };

    setMessageTextInput = function(messageText) {
        this.messageTextInput.sendKeys(messageText);
    };

    getMessageTextInput = function() {
        return this.messageTextInput.getAttribute('value');
    };

    setMessageValidFromInput = function(messageValidFrom) {
        this.messageValidFromInput.sendKeys(messageValidFrom);
    };

    getMessageValidFromInput = function() {
        return this.messageValidFromInput.getAttribute('value');
    };

    setMessageValidUntilInput = function(messageValidUntil) {
        this.messageValidUntilInput.sendKeys(messageValidUntil);
    };

    getMessageValidUntilInput = function() {
        return this.messageValidUntilInput.getAttribute('value');
    };

    setImageInput = function(image) {
        this.imageInput.sendKeys(image);
    };

    getImageInput = function() {
        return this.imageInput.getAttribute('value');
    };

    toSelectLastOption = function() {
        this.toSelect.all(by.tagName('option')).last().click();
    };

    toSelectOption = function(option) {
        this.toSelect.sendKeys(option);
    };

    getToSelect = function() {
        return this.toSelect;
    };

    getToSelectedOption = function() {
        return this.toSelect.element(by.css('option:checked')).getText();
    };

    fromSelectLastOption = function() {
        this.fromSelect.all(by.tagName('option')).last().click();
    };

    fromSelectOption = function(option) {
        this.fromSelect.sendKeys(option);
    };

    getFromSelect = function() {
        return this.fromSelect;
    };

    getFromSelectedOption = function() {
        return this.fromSelect.element(by.css('option:checked')).getText();
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
