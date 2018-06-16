/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WeddingplanerTestModule } from '../../../test.module';
import { AllowControlComponent } from '../../../../../../main/webapp/app/entities/allow-control/allow-control.component';
import { AllowControlService } from '../../../../../../main/webapp/app/entities/allow-control/allow-control.service';
import { AllowControl } from '../../../../../../main/webapp/app/entities/allow-control/allow-control.model';

describe('Component Tests', () => {

    describe('AllowControl Management Component', () => {
        let comp: AllowControlComponent;
        let fixture: ComponentFixture<AllowControlComponent>;
        let service: AllowControlService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WeddingplanerTestModule],
                declarations: [AllowControlComponent],
                providers: [
                    AllowControlService
                ]
            })
            .overrideTemplate(AllowControlComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AllowControlComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AllowControlService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new AllowControl(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.allowControls[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
