import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Message e2e test', () => {

    let navBarPage: NavBarPage;
    let messageDialogPage: MessageDialogPage;
    let messageComponentsPage: MessageComponentsPage;

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
        // messageDialogPage.userExtraSelectLastOption();
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
    userExtraSelect = element(by.css('select#field_userExtra'));

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

    userExtraSelectLastOption = function() {
        this.userExtraSelect.all(by.tagName('option')).last().click();
    };

    userExtraSelectOption = function(option) {
        this.userExtraSelect.sendKeys(option);
    };

    getUserExtraSelect = function() {
        return this.userExtraSelect;
    };

    getUserExtraSelectedOption = function() {
        return this.userExtraSelect.element(by.css('option:checked')).getText();
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
