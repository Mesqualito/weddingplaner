/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WeddingplanerTestModule } from '../../../test.module';
import { PartyFoodDetailComponent } from 'app/entities/party-food/party-food-detail.component';
import { PartyFood } from 'app/shared/model/party-food.model';

describe('Component Tests', () => {
    describe('PartyFood Management Detail Component', () => {
        let comp: PartyFoodDetailComponent;
        let fixture: ComponentFixture<PartyFoodDetailComponent>;
        const route = ({ data: of({ partyFood: new PartyFood(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [PartyFoodDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PartyFoodDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PartyFoodDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.partyFood).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
