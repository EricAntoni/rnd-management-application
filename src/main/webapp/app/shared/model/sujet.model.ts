import { IProjet } from 'app/shared/model//projet.model';
import { ITheme } from 'app/shared/model//theme.model';

export const enum ContextOrigineEnum {
    PRODUCT = 'PRODUCT',
    SERVICE = 'SERVICE',
    PRESALES = 'PRESALES'
}

export interface ISujet {
    id?: number;
    libelle?: string;
    detail?: string;
    suivi?: boolean;
    idEvolution?: number;
    origine?: ContextOrigineEnum;
    projet?: IProjet;
    theme?: ITheme;
}

export class Sujet implements ISujet {
    constructor(
        public id?: number,
        public libelle?: string,
        public detail?: string,
        public suivi?: boolean,
        public idEvolution?: number,
        public origine?: ContextOrigineEnum,
        public projet?: IProjet,
        public theme?: ITheme
    ) {
        this.suivi = this.suivi || false;
    }
}
