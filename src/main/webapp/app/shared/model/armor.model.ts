import { IArmorVersion } from 'app/shared/model/armor-version.model';
import { IArmorStrength } from 'app/shared/model/armor-strength.model';

export interface IArmor {
  id?: number;
  name?: string;
  versions?: IArmorVersion[];
  strengths?: IArmorStrength[];
  categoryId?: number;
}

export class Armor implements IArmor {
  constructor(
    public id?: number,
    public name?: string,
    public versions?: IArmorVersion[],
    public strengths?: IArmorStrength[],
    public categoryId?: number
  ) {}
}
