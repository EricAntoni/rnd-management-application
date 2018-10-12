import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RndManagementApplicationSharedModule } from 'app/shared';
import {
    ProjetComponent,
    ProjetDetailComponent,
    ProjetUpdateComponent,
    ProjetDeletePopupComponent,
    ProjetDeleteDialogComponent,
    projetRoute,
    projetPopupRoute
} from './';

const ENTITY_STATES = [...projetRoute, ...projetPopupRoute];

@NgModule({
    imports: [RndManagementApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ProjetComponent, ProjetDetailComponent, ProjetUpdateComponent, ProjetDeleteDialogComponent, ProjetDeletePopupComponent],
    entryComponents: [ProjetComponent, ProjetUpdateComponent, ProjetDeleteDialogComponent, ProjetDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RndManagementApplicationProjetModule {}
