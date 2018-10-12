import { ISujet } from 'app/shared/model//sujet.model';

export interface IProjet {
    id?: number;
    name?: string;
    sujets?: ISujet[];
}

export class Projet implements IProjet {
    constructor(public id?: number, public name?: string, public sujets?: ISujet[]) {}
}
