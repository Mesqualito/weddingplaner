import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { AllowControlComponentsPage, AllowControlUpdatePage } from './allow-control.page-object';

describe('AllowControl e2e test', () => {
    let navBarPage: NavBarPage;
    let allowControlUpdatePage: AllowControlUpdatePage;
    let allowControlComponentsPage: AllowControlComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load AllowControls', () => {
        navBarPage.goToEntity('allow-control');
        allowControlComponentsPage = new AllowControlComponentsPage();
        expect(allowControlComponentsPage.getTitle()).toMatch(/weddingplanerApp.allowControl.home.title/);
    });

    it('should load create AllowControl page', () => {
        allowControlComponentsPage.clickOnCreateButton();
        allowControlUpdatePage = new AllowControlUpdatePage();
        expect(allowControlUpdatePage.getPageTitle()).toMatch(/weddingplanerApp.allowControl.home.createOrEditLabel/);
        allowControlUpdatePage.cancel();
    });

    it('should create and save AllowControls', () => {
        allowControlComponentsPage.clickOnCreateButton();
        allowControlUpdatePage.allowGroupSelectLastOption();
        // allowControlUpdatePage.controlledGroupSelectLastOption();
        allowControlUpdatePage.controlGroupSelectLastOption();
        allowControlUpdatePage.save();
        expect(allowControlUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
