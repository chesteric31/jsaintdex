import { IArmorVersionAttack } from 'app/shared/model/armor-version-attack.model';

export interface IArmorVersion {
  id?: number;
  name?: string;
  imageContentType?: string;
  image?: any;
  attacks?: IArmorVersionAttack[];
  armorId?: number;
}

export class ArmorVersion implements IArmorVersion {
  constructor(
    public id?: number,
    public name?: string,
    public imageContentType?: string,
    public image?: any,
    public attacks?: IArmorVersionAttack[],
    public armorId?: number
  ) {}
}
