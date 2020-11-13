export interface IStrength {
  id?: number;
  name?: string;
}

export class Strength implements IStrength {
  constructor(public id?: number, public name?: string) {}
}
