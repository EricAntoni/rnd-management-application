import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { RndManagementApplicationSujetModule } from './sujet/sujet.module';
import { RndManagementApplicationProjetModule } from './projet/projet.module';
import { RndManagementApplicationThemeModule } from './theme/theme.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        RndManagementApplicationSujetModule,
        RndManagementApplicationProjetModule,
        RndManagementApplicationThemeModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RndManagementApplicationEntityModule {}
