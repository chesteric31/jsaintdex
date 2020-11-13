import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JsaintdexTestModule } from '../../../test.module';
import { StrengthUpdateComponent } from 'app/entities/strength/strength-update.component';
import { StrengthService } from 'app/entities/strength/strength.service';
import { Strength } from 'app/shared/model/strength.model';

describe('Component Tests', () => {
  describe('Strength Management Update Component', () => {
    let comp: StrengthUpdateComponent;
    let fixture: ComponentFixture<StrengthUpdateComponent>;
    let service: StrengthService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JsaintdexTestModule],
        declarations: [StrengthUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(StrengthUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StrengthUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StrengthService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Strength(123);
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
        const entity = new Strength();
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
