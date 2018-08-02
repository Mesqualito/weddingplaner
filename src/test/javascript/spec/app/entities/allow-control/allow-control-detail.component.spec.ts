/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WeddingplanerTestModule } from '../../../test.module';
import { AllowControlDetailComponent } from 'app/entities/allow-control/allow-control-detail.component';
import { AllowControl } from 'app/shared/model/allow-control.model';

describe('Component Tests', () => {
    describe('AllowControl Management Detail Component', () => {
        let comp: AllowControlDetailComponent;
        let fixture: ComponentFixture<AllowControlDetailComponent>;
        const route = ({ data: of({ allowControl: new AllowControl(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [AllowControlDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AllowControlDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AllowControlDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.allowControl).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
