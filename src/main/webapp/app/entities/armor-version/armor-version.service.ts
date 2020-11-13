import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IArmorVersion } from 'app/shared/model/armor-version.model';

type EntityResponseType = HttpResponse<IArmorVersion>;
type EntityArrayResponseType = HttpResponse<IArmorVersion[]>;

@Injectable({ providedIn: 'root' })
export class ArmorVersionService {
  public resourceUrl = SERVER_API_URL + 'api/armor-versions';

  constructor(protected http: HttpClient) {}

  create(armorVersion: IArmorVersion): Observable<EntityResponseType> {
    return this.http.post<IArmorVersion>(this.resourceUrl, armorVersion, { observe: 'response' });
  }

  update(armorVersion: IArmorVersion): Observable<EntityResponseType> {
    return this.http.put<IArmorVersion>(this.resourceUrl, armorVersion, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IArmorVersion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IArmorVersion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
