import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JsaintdexTestModule } from '../../../test.module';
import { ArmorUpdateComponent } from 'app/entities/armor/armor-update.component';
import { ArmorService } from 'app/entities/armor/armor.service';
import { Armor } from 'app/shared/model/armor.model';

describe('Component Tests', () => {
  describe('Armor Management Update Component', () => {
    let comp: ArmorUpdateComponent;
    let fixture: ComponentFixture<ArmorUpdateComponent>;
    let service: ArmorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JsaintdexTestModule],
        declarations: [ArmorUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ArmorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ArmorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ArmorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Armor(123);
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
        const entity = new Armor();
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
