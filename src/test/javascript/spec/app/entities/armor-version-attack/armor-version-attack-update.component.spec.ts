import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JsaintdexTestModule } from '../../../test.module';
import { ArmorVersionAttackUpdateComponent } from 'app/entities/armor-version-attack/armor-version-attack-update.component';
import { ArmorVersionAttackService } from 'app/entities/armor-version-attack/armor-version-attack.service';
import { ArmorVersionAttack } from 'app/shared/model/armor-version-attack.model';

describe('Component Tests', () => {
  describe('ArmorVersionAttack Management Update Component', () => {
    let comp: ArmorVersionAttackUpdateComponent;
    let fixture: ComponentFixture<ArmorVersionAttackUpdateComponent>;
    let service: ArmorVersionAttackService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JsaintdexTestModule],
        declarations: [ArmorVersionAttackUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ArmorVersionAttackUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ArmorVersionAttackUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ArmorVersionAttackService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ArmorVersionAttack(123);
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
        const entity = new ArmorVersionAttack();
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
