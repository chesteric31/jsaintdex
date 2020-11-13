import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JsaintdexTestModule } from '../../../test.module';
import { ArmorStrengthDetailComponent } from 'app/entities/armor-strength/armor-strength-detail.component';
import { ArmorStrength } from 'app/shared/model/armor-strength.model';

describe('Component Tests', () => {
  describe('ArmorStrength Management Detail Component', () => {
    let comp: ArmorStrengthDetailComponent;
    let fixture: ComponentFixture<ArmorStrengthDetailComponent>;
    const route = ({ data: of({ armorStrength: new ArmorStrength(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JsaintdexTestModule],
        declarations: [ArmorStrengthDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ArmorStrengthDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ArmorStrengthDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load armorStrength on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.armorStrength).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
