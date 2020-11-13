import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IArmorCategory } from 'app/shared/model/armor-category.model';

type EntityResponseType = HttpResponse<IArmorCategory>;
type EntityArrayResponseType = HttpResponse<IArmorCategory[]>;

@Injectable({ providedIn: 'root' })
export class ArmorCategoryService {
  public resourceUrl = SERVER_API_URL + 'api/armor-categories';

  constructor(protected http: HttpClient) {}

  create(armorCategory: IArmorCategory): Observable<EntityResponseType> {
    return this.http.post<IArmorCategory>(this.resourceUrl, armorCategory, { observe: 'response' });
  }

  update(armorCategory: IArmorCategory): Observable<EntityResponseType> {
    return this.http.put<IArmorCategory>(this.resourceUrl, armorCategory, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IArmorCategory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IArmorCategory[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
