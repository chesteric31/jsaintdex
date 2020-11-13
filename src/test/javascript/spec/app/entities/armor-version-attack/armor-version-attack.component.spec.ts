import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { JsaintdexTestModule } from '../../../test.module';
import { ArmorVersionAttackComponent } from 'app/entities/armor-version-attack/armor-version-attack.component';
import { ArmorVersionAttackService } from 'app/entities/armor-version-attack/armor-version-attack.service';
import { ArmorVersionAttack } from 'app/shared/model/armor-version-attack.model';

describe('Component Tests', () => {
  describe('ArmorVersionAttack Management Component', () => {
    let comp: ArmorVersionAttackComponent;
    let fixture: ComponentFixture<ArmorVersionAttackComponent>;
    let service: ArmorVersionAttackService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JsaintdexTestModule],
        declarations: [ArmorVersionAttackComponent],
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
        .overrideTemplate(ArmorVersionAttackComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ArmorVersionAttackComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ArmorVersionAttackService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ArmorVersionAttack(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.armorVersionAttacks && comp.armorVersionAttacks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ArmorVersionAttack(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.armorVersionAttacks && comp.armorVersionAttacks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
