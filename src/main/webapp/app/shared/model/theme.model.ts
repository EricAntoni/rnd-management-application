import { ISujet } from 'app/shared/model//sujet.model';

export interface ITheme {
    id?: number;
    name?: string;
    sujets?: ISujet[];
}

export class Theme implements ITheme {
    constructor(public id?: number, public name?: string, public sujets?: ISujet[]) {}
}
