import { browser, protractor } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { PartyFoodComponentsPage, PartyFoodUpdatePage } from './party-food.page-object';

describe('PartyFood e2e test', () => {
    let navBarPage: NavBarPage;
    let partyFoodUpdatePage: PartyFoodUpdatePage;
    let partyFoodComponentsPage: PartyFoodComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load PartyFoods', () => {
        navBarPage.goToEntity('party-food');
        partyFoodComponentsPage = new PartyFoodComponentsPage();
        expect(partyFoodComponentsPage.getTitle()).toMatch(/weddingplanerApp.partyFood.home.title/);
    });

    it('should load create PartyFood page', () => {
        partyFoodComponentsPage.clickOnCreateButton();
        partyFoodUpdatePage = new PartyFoodUpdatePage();
        expect(partyFoodUpdatePage.getPageTitle()).toMatch(/weddingplanerApp.partyFood.home.createOrEditLabel/);
        partyFoodUpdatePage.cancel();
    });

    it('should create and save PartyFoods', () => {
        partyFoodComponentsPage.clickOnCreateButton();
        partyFoodUpdatePage.setFoodNameInput('foodName');
        expect(partyFoodUpdatePage.getFoodNameInput()).toMatch('foodName');
        partyFoodUpdatePage.setFoodShortDescriptionInput('foodShortDescription');
        expect(partyFoodUpdatePage.getFoodShortDescriptionInput()).toMatch('foodShortDescription');
        partyFoodUpdatePage.setFoodLongDescriptionInput('foodLongDescription');
        expect(partyFoodUpdatePage.getFoodLongDescriptionInput()).toMatch('foodLongDescription');
        partyFoodUpdatePage.setFoodQuantityPersonsInput('5');
        expect(partyFoodUpdatePage.getFoodQuantityPersonsInput()).toMatch('5');
        partyFoodUpdatePage.setFoodBestServeTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(partyFoodUpdatePage.getFoodBestServeTimeInput()).toContain('2001-01-01T02:30');
        partyFoodUpdatePage
            .getFoodProposalAcceptedInput()
            .isSelected()
            .then(selected => {
                if (selected) {
                    partyFoodUpdatePage.getFoodProposalAcceptedInput().click();
                    expect(partyFoodUpdatePage.getFoodProposalAcceptedInput().isSelected()).toBeFalsy();
                } else {
                    partyFoodUpdatePage.getFoodProposalAcceptedInput().click();
                    expect(partyFoodUpdatePage.getFoodProposalAcceptedInput().isSelected()).toBeTruthy();
                }
            });
        partyFoodUpdatePage.userExtraSelectLastOption();
        partyFoodUpdatePage.save();
        expect(partyFoodUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
