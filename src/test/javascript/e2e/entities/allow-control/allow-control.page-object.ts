import { element, by, promise, ElementFinder } from 'protractor';

export class AllowControlComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-allow-control div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class AllowControlUpdatePage {
    pageTitle = element(by.id('jhi-allow-control-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    allowGroupSelect = element(by.id('field_allowGroup'));
    controlledGroupSelect = element(by.id('field_controlledGroup'));
    controlGroupSelect = element(by.id('field_controlGroup'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setAllowGroupSelect(allowGroup): promise.Promise<void> {
        return this.allowGroupSelect.sendKeys(allowGroup);
    }

    getAllowGroupSelect() {
        return this.allowGroupSelect.element(by.css('option:checked')).getText();
    }

    allowGroupSelectLastOption(): promise.Promise<void> {
        return this.allowGroupSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }
    controlledGroupSelectLastOption(): promise.Promise<void> {
        return this.controlledGroupSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    controlledGroupSelectOption(option): promise.Promise<void> {
        return this.controlledGroupSelect.sendKeys(option);
    }

    getControlledGroupSelect(): ElementFinder {
        return this.controlledGroupSelect;
    }

    getControlledGroupSelectedOption() {
        return this.controlledGroupSelect.element(by.css('option:checked')).getText();
    }

    controlGroupSelectLastOption(): promise.Promise<void> {
        return this.controlGroupSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    controlGroupSelectOption(option): promise.Promise<void> {
        return this.controlGroupSelect.sendKeys(option);
    }

    getControlGroupSelect(): ElementFinder {
        return this.controlGroupSelect;
    }

    getControlGroupSelectedOption() {
        return this.controlGroupSelect.element(by.css('option:checked')).getText();
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
