/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WeddingplanerTestModule } from '../../../test.module';
import { PartyFoodComponent } from '../../../../../../main/webapp/app/entities/party-food/party-food.component';
import { PartyFoodService } from '../../../../../../main/webapp/app/entities/party-food/party-food.service';
import { PartyFood } from '../../../../../../main/webapp/app/entities/party-food/party-food.model';

describe('Component Tests', () => {

    describe('PartyFood Management Component', () => {
        let comp: PartyFoodComponent;
        let fixture: ComponentFixture<PartyFoodComponent>;
        let service: PartyFoodService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [PartyFoodComponent],
                providers: [
                    PartyFoodService
                ]
            })
            .overrideTemplate(PartyFoodComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PartyFoodComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PartyFoodService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new PartyFood(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.partyFoods[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
