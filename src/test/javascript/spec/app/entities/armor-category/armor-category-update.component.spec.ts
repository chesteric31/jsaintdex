import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JsaintdexTestModule } from '../../../test.module';
import { ArmorCategoryUpdateComponent } from 'app/entities/armor-category/armor-category-update.component';
import { ArmorCategoryService } from 'app/entities/armor-category/armor-category.service';
import { ArmorCategory } from 'app/shared/model/armor-category.model';

describe('Component Tests', () => {
  describe('ArmorCategory Management Update Component', () => {
    let comp: ArmorCategoryUpdateComponent;
    let fixture: ComponentFixture<ArmorCategoryUpdateComponent>;
    let service: ArmorCategoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JsaintdexTestModule],
        declarations: [ArmorCategoryUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ArmorCategoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ArmorCategoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ArmorCategoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ArmorCategory(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ArmorCategory();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
