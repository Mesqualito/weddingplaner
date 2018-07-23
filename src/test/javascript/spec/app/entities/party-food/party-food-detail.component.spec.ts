/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { WeddingplanerTestModule } from '../../../test.module';
import { PartyFoodDetailComponent } from '../../../../../../main/webapp/app/entities/party-food/party-food-detail.component';
import { PartyFoodService } from '../../../../../../main/webapp/app/entities/party-food/party-food.service';
import { PartyFood } from '../../../../../../main/webapp/app/entities/party-food/party-food.model';

describe('Component Tests', () => {

    describe('PartyFood Management Detail Component', () => {
        let comp: PartyFoodDetailComponent;
        let fixture: ComponentFixture<PartyFoodDetailComponent>;
        let service: PartyFoodService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [PartyFoodDetailComponent],
                providers: [
                    PartyFoodService
                ]
            })
            .overrideTemplate(PartyFoodDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PartyFoodDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PartyFoodService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new PartyFood(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.partyFood).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
