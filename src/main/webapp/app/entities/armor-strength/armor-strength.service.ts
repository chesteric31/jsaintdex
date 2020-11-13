import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IArmorStrength } from 'app/shared/model/armor-strength.model';

type EntityResponseType = HttpResponse<IArmorStrength>;
type EntityArrayResponseType = HttpResponse<IArmorStrength[]>;

@Injectable({ providedIn: 'root' })
export class ArmorStrengthService {
  public resourceUrl = SERVER_API_URL + 'api/armor-strengths';

  constructor(protected http: HttpClient) {}

  create(armorStrength: IArmorStrength): Observable<EntityResponseType> {
    return this.http.post<IArmorStrength>(this.resourceUrl, armorStrength, { observe: 'response' });
  }

  update(armorStrength: IArmorStrength): Observable<EntityResponseType> {
    return this.http.put<IArmorStrength>(this.resourceUrl, armorStrength, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IArmorStrength>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IArmorStrength[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
