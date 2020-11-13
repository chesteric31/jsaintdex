import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JsaintdexTestModule } from '../../../test.module';
import { ArmorStrengthUpdateComponent } from 'app/entities/armor-strength/armor-strength-update.component';
import { ArmorStrengthService } from 'app/entities/armor-strength/armor-strength.service';
import { ArmorStrength } from 'app/shared/model/armor-strength.model';

describe('Component Tests', () => {
  describe('ArmorStrength Management Update Component', () => {
    let comp: ArmorStrengthUpdateComponent;
    let fixture: ComponentFixture<ArmorStrengthUpdateComponent>;
    let service: ArmorStrengthService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JsaintdexTestModule],
        declarations: [ArmorStrengthUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ArmorStrengthUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ArmorStrengthUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ArmorStrengthService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ArmorStrength(123);
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
        const entity = new ArmorStrength();
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
