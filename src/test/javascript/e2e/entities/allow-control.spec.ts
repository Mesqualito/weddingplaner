import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('AllowControl e2e test', () => {

    let navBarPage: NavBarPage;
    let allowControlDialogPage: AllowControlDialogPage;
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
        expect(allowControlComponentsPage.getTitle())
            .toMatch(/weddingplanerApp.allowControl.home.title/);

    });

    it('should load create AllowControl dialog', () => {
        allowControlComponentsPage.clickOnCreateButton();
        allowControlDialogPage = new AllowControlDialogPage();
        expect(allowControlDialogPage.getModalTitle())
            .toMatch(/weddingplanerApp.allowControl.home.createOrEditLabel/);
        allowControlDialogPage.close();
    });

    it('should create and save AllowControls', () => {
        allowControlComponentsPage.clickOnCreateButton();
        allowControlDialogPage.allowGroupSelectLastOption();
        // allowControlDialogPage.userExtraSelectLastOption();
        allowControlDialogPage.save();
        expect(allowControlDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class AllowControlComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-allow-control div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class AllowControlDialogPage {
    modalTitle = element(by.css('h4#myAllowControlLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    allowGroupSelect = element(by.css('select#field_allowGroup'));
    userExtraSelect = element(by.css('select#field_userExtra'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setAllowGroupSelect = function(allowGroup) {
        this.allowGroupSelect.sendKeys(allowGroup);
    };

    getAllowGroupSelect = function() {
        return this.allowGroupSelect.element(by.css('option:checked')).getText();
    };

    allowGroupSelectLastOption = function() {
        this.allowGroupSelect.all(by.tagName('option')).last().click();
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
