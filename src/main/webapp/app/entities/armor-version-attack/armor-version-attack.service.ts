import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IArmorVersionAttack } from 'app/shared/model/armor-version-attack.model';

type EntityResponseType = HttpResponse<IArmorVersionAttack>;
type EntityArrayResponseType = HttpResponse<IArmorVersionAttack[]>;

@Injectable({ providedIn: 'root' })
export class ArmorVersionAttackService {
  public resourceUrl = SERVER_API_URL + 'api/armor-version-attacks';

  constructor(protected http: HttpClient) {}

  create(armorVersionAttack: IArmorVersionAttack): Observable<EntityResponseType> {
    return this.http.post<IArmorVersionAttack>(this.resourceUrl, armorVersionAttack, { observe: 'response' });
  }

  update(armorVersionAttack: IArmorVersionAttack): Observable<EntityResponseType> {
    return this.http.put<IArmorVersionAttack>(this.resourceUrl, armorVersionAttack, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IArmorVersionAttack>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IArmorVersionAttack[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
