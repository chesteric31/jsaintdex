import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IStrength } from 'app/shared/model/strength.model';

type EntityResponseType = HttpResponse<IStrength>;
type EntityArrayResponseType = HttpResponse<IStrength[]>;

@Injectable({ providedIn: 'root' })
export class StrengthService {
  public resourceUrl = SERVER_API_URL + 'api/strengths';

  constructor(protected http: HttpClient) {}

  create(strength: IStrength): Observable<EntityResponseType> {
    return this.http.post<IStrength>(this.resourceUrl, strength, { observe: 'response' });
  }

  update(strength: IStrength): Observable<EntityResponseType> {
    return this.http.put<IStrength>(this.resourceUrl, strength, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStrength>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStrength[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
