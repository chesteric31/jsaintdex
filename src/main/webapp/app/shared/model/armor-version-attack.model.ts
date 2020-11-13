export interface IArmorVersionAttack {
  id?: number;
  name?: string;
  versionId?: number;
}

export class ArmorVersionAttack implements IArmorVersionAttack {
  constructor(public id?: number, public name?: string, public versionId?: number) {}
}
