/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { WeddingplanerTestModule } from '../../../test.module';
import { AllowControlUpdateComponent } from 'app/entities/allow-control/allow-control-update.component';
import { AllowControlService } from 'app/entities/allow-control/allow-control.service';
import { AllowControl } from 'app/shared/model/allow-control.model';

describe('Component Tests', () => {
    describe('AllowControl Management Update Component', () => {
        let comp: AllowControlUpdateComponent;
        let fixture: ComponentFixture<AllowControlUpdateComponent>;
        let service: AllowControlService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [AllowControlUpdateComponent]
            })
                .overrideTemplate(AllowControlUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AllowControlUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AllowControlService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AllowControl(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.allowControl = entity;
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
                    const entity = new AllowControl();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.allowControl = entity;
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
