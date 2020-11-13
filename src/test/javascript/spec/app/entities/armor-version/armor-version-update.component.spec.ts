import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JsaintdexTestModule } from '../../../test.module';
import { ArmorVersionUpdateComponent } from 'app/entities/armor-version/armor-version-update.component';
import { ArmorVersionService } from 'app/entities/armor-version/armor-version.service';
import { ArmorVersion } from 'app/shared/model/armor-version.model';

describe('Component Tests', () => {
  describe('ArmorVersion Management Update Component', () => {
    let comp: ArmorVersionUpdateComponent;
    let fixture: ComponentFixture<ArmorVersionUpdateComponent>;
    let service: ArmorVersionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JsaintdexTestModule],
        declarations: [ArmorVersionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ArmorVersionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ArmorVersionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ArmorVersionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ArmorVersion(123);
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
        const entity = new ArmorVersion();
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
