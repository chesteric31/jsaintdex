import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { JsaintdexTestModule } from '../../../test.module';
import { ArmorStrengthComponent } from 'app/entities/armor-strength/armor-strength.component';
import { ArmorStrengthService } from 'app/entities/armor-strength/armor-strength.service';
import { ArmorStrength } from 'app/shared/model/armor-strength.model';

describe('Component Tests', () => {
  describe('ArmorStrength Management Component', () => {
    let comp: ArmorStrengthComponent;
    let fixture: ComponentFixture<ArmorStrengthComponent>;
    let service: ArmorStrengthService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JsaintdexTestModule],
        declarations: [ArmorStrengthComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(ArmorStrengthComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ArmorStrengthComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ArmorStrengthService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ArmorStrength(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.armorStrengths && comp.armorStrengths[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ArmorStrength(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.armorStrengths && comp.armorStrengths[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
