import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JsaintdexTestModule } from '../../../test.module';
import { StrengthDetailComponent } from 'app/entities/strength/strength-detail.component';
import { Strength } from 'app/shared/model/strength.model';

describe('Component Tests', () => {
  describe('Strength Management Detail Component', () => {
    let comp: StrengthDetailComponent;
    let fixture: ComponentFixture<StrengthDetailComponent>;
    const route = ({ data: of({ strength: new Strength(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JsaintdexTestModule],
        declarations: [StrengthDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(StrengthDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StrengthDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load strength on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.strength).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
