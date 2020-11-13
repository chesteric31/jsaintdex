export interface IArmorStrength {
  id?: number;
  value?: number;
  strengthId?: number;
  armorId?: number;
}

export class ArmorStrength implements IArmorStrength {
  constructor(public id?: number, public value?: number, public strengthId?: number, public armorId?: number) {}
}
