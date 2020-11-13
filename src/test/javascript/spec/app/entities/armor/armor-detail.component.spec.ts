import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JsaintdexTestModule } from '../../../test.module';
import { ArmorDetailComponent } from 'app/entities/armor/armor-detail.component';
import { Armor } from 'app/shared/model/armor.model';

describe('Component Tests', () => {
  describe('Armor Management Detail Component', () => {
    let comp: ArmorDetailComponent;
    let fixture: ComponentFixture<ArmorDetailComponent>;
    const route = ({ data: of({ armor: new Armor(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JsaintdexTestModule],
        declarations: [ArmorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ArmorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ArmorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load armor on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.armor).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
