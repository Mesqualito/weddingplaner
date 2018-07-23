/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { WeddingplanerTestModule } from '../../../test.module';
import { AllowControlDetailComponent } from '../../../../../../main/webapp/app/entities/allow-control/allow-control-detail.component';
import { AllowControlService } from '../../../../../../main/webapp/app/entities/allow-control/allow-control.service';
import { AllowControl } from '../../../../../../main/webapp/app/entities/allow-control/allow-control.model';

describe('Component Tests', () => {

    describe('AllowControl Management Detail Component', () => {
        let comp: AllowControlDetailComponent;
        let fixture: ComponentFixture<AllowControlDetailComponent>;
        let service: AllowControlService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [AllowControlDetailComponent],
                providers: [
                    AllowControlService
                ]
            })
            .overrideTemplate(AllowControlDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AllowControlDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AllowControlService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new AllowControl(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.allowControl).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
