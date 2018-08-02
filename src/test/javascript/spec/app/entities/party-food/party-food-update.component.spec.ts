/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { WeddingplanerTestModule } from '../../../test.module';
import { PartyFoodUpdateComponent } from 'app/entities/party-food/party-food-update.component';
import { PartyFoodService } from 'app/entities/party-food/party-food.service';
import { PartyFood } from 'app/shared/model/party-food.model';

describe('Component Tests', () => {
    describe('PartyFood Management Update Component', () => {
        let comp: PartyFoodUpdateComponent;
        let fixture: ComponentFixture<PartyFoodUpdateComponent>;
        let service: PartyFoodService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [PartyFoodUpdateComponent]
            })
                .overrideTemplate(PartyFoodUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PartyFoodUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PartyFoodService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PartyFood(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.partyFood = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PartyFood();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.partyFood = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
