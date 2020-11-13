import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JsaintdexTestModule } from '../../../test.module';
import { ArmorVersionAttackDetailComponent } from 'app/entities/armor-version-attack/armor-version-attack-detail.component';
import { ArmorVersionAttack } from 'app/shared/model/armor-version-attack.model';

describe('Component Tests', () => {
  describe('ArmorVersionAttack Management Detail Component', () => {
    let comp: ArmorVersionAttackDetailComponent;
    let fixture: ComponentFixture<ArmorVersionAttackDetailComponent>;
    const route = ({ data: of({ armorVersionAttack: new ArmorVersionAttack(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JsaintdexTestModule],
        declarations: [ArmorVersionAttackDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ArmorVersionAttackDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ArmorVersionAttackDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load armorVersionAttack on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.armorVersionAttack).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
