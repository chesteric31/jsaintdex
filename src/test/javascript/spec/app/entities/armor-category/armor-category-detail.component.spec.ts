import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JsaintdexTestModule } from '../../../test.module';
import { ArmorCategoryDetailComponent } from 'app/entities/armor-category/armor-category-detail.component';
import { ArmorCategory } from 'app/shared/model/armor-category.model';

describe('Component Tests', () => {
  describe('ArmorCategory Management Detail Component', () => {
    let comp: ArmorCategoryDetailComponent;
    let fixture: ComponentFixture<ArmorCategoryDetailComponent>;
    const route = ({ data: of({ armorCategory: new ArmorCategory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JsaintdexTestModule],
        declarations: [ArmorCategoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ArmorCategoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ArmorCategoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load armorCategory on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.armorCategory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
