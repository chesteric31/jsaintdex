export interface IArmorCategory {
  id?: number;
  name?: string;
}

export class ArmorCategory implements IArmorCategory {
  constructor(public id?: number, public name?: string) {}
}
