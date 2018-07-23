import { element, by, promise, ElementFinder } from 'protractor';

export class PartyFoodComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-party-food div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class PartyFoodUpdatePage {
    pageTitle = element(by.id('jhi-party-food-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    foodNameInput = element(by.id('field_foodName'));
    foodShortDescriptionInput = element(by.id('field_foodShortDescription'));
    foodLongDescriptionInput = element(by.id('field_foodLongDescription'));
    foodQuantityPersonsInput = element(by.id('field_foodQuantityPersons'));
    foodBestServeTimeInput = element(by.id('field_foodBestServeTime'));
    foodProposalAcceptedInput = element(by.id('field_foodProposalAccepted'));
    userExtraSelect = element(by.id('field_userExtra'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setFoodNameInput(foodName): promise.Promise<void> {
        return this.foodNameInput.sendKeys(foodName);
    }

    getFoodNameInput() {
        return this.foodNameInput.getAttribute('value');
    }

    setFoodShortDescriptionInput(foodShortDescription): promise.Promise<void> {
        return this.foodShortDescriptionInput.sendKeys(foodShortDescription);
    }

    getFoodShortDescriptionInput() {
        return this.foodShortDescriptionInput.getAttribute('value');
    }

    setFoodLongDescriptionInput(foodLongDescription): promise.Promise<void> {
        return this.foodLongDescriptionInput.sendKeys(foodLongDescription);
    }

    getFoodLongDescriptionInput() {
        return this.foodLongDescriptionInput.getAttribute('value');
    }

    setFoodQuantityPersonsInput(foodQuantityPersons): promise.Promise<void> {
        return this.foodQuantityPersonsInput.sendKeys(foodQuantityPersons);
    }

    getFoodQuantityPersonsInput() {
        return this.foodQuantityPersonsInput.getAttribute('value');
    }

    setFoodBestServeTimeInput(foodBestServeTime): promise.Promise<void> {
        return this.foodBestServeTimeInput.sendKeys(foodBestServeTime);
    }

    getFoodBestServeTimeInput() {
        return this.foodBestServeTimeInput.getAttribute('value');
    }

    getFoodProposalAcceptedInput() {
        return this.foodProposalAcceptedInput;
    }
    userExtraSelectLastOption(): promise.Promise<void> {
        return this.userExtraSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    userExtraSelectOption(option): promise.Promise<void> {
        return this.userExtraSelect.sendKeys(option);
    }

    getUserExtraSelect(): ElementFinder {
        return this.userExtraSelect;
    }

    getUserExtraSelectedOption() {
        return this.userExtraSelect.element(by.css('option:checked')).getText();
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
