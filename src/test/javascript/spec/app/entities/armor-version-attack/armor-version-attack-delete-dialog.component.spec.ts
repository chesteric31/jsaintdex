import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JsaintdexTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { ArmorVersionAttackDeleteDialogComponent } from 'app/entities/armor-version-attack/armor-version-attack-delete-dialog.component';
import { ArmorVersionAttackService } from 'app/entities/armor-version-attack/armor-version-attack.service';

describe('Component Tests', () => {
  describe('ArmorVersionAttack Management Delete Component', () => {
    let comp: ArmorVersionAttackDeleteDialogComponent;
    let fixture: ComponentFixture<ArmorVersionAttackDeleteDialogComponent>;
    let service: ArmorVersionAttackService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JsaintdexTestModule],
        declarations: [ArmorVersionAttackDeleteDialogComponent],
      })
        .overrideTemplate(ArmorVersionAttackDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ArmorVersionAttackDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ArmorVersionAttackService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
