import { browser, protractor } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { MessageComponentsPage, MessageUpdatePage } from './message.page-object';
import * as path from 'path';

describe('Message e2e test', () => {
    let navBarPage: NavBarPage;
    let messageUpdatePage: MessageUpdatePage;
    let messageComponentsPage: MessageComponentsPage;
    const fileToUpload = '../../../../../main/webapp/content/images/logo-jhipster.png';
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
        expect(messageComponentsPage.getTitle()).toMatch(/weddingplanerApp.message.home.title/);
    });

    it('should load create Message page', () => {
        messageComponentsPage.clickOnCreateButton();
        messageUpdatePage = new MessageUpdatePage();
        expect(messageUpdatePage.getPageTitle()).toMatch(/weddingplanerApp.message.home.createOrEditLabel/);
        messageUpdatePage.cancel();
    });

    /* it('should create and save Messages', () => {
        messageComponentsPage.clickOnCreateButton();
        messageUpdatePage.setMessageShortDescriptionInput('messageShortDescription');
        expect(messageUpdatePage.getMessageShortDescriptionInput()).toMatch('messageShortDescription');
        messageUpdatePage.setMessageInitTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(messageUpdatePage.getMessageInitTimeInput()).toContain('2001-01-01T02:30');
        messageUpdatePage.setMessageTextInput('messageText');
        expect(messageUpdatePage.getMessageTextInput()).toMatch('messageText');
        messageUpdatePage.setMessageValidFromInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(messageUpdatePage.getMessageValidFromInput()).toContain('2001-01-01T02:30');
        messageUpdatePage.setMessageValidUntilInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(messageUpdatePage.getMessageValidUntilInput()).toContain('2001-01-01T02:30');
        messageUpdatePage.setImageInput(absolutePath);
        // messageUpdatePage.toSelectLastOption();
        messageUpdatePage.fromSelectLastOption();
        messageUpdatePage.save();
        expect(messageUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
