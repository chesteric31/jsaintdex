import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IArmor } from 'app/shared/model/armor.model';

type EntityResponseType = HttpResponse<IArmor>;
type EntityArrayResponseType = HttpResponse<IArmor[]>;

@Injectable({ providedIn: 'root' })
export class ArmorService {
  public resourceUrl = SERVER_API_URL + 'api/armors';

  constructor(protected http: HttpClient) {}

  create(armor: IArmor): Observable<EntityResponseType> {
    return this.http.post<IArmor>(this.resourceUrl, armor, { observe: 'response' });
  }

  update(armor: IArmor): Observable<EntityResponseType> {
    return this.http.put<IArmor>(this.resourceUrl, armor, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IArmor>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IArmor[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
