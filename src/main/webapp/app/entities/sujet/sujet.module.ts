import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RndManagementApplicationSharedModule } from 'app/shared';
import {
    SujetComponent,
    SujetDetailComponent,
    SujetUpdateComponent,
    SujetDeletePopupComponent,
    SujetDeleteDialogComponent,
    sujetRoute,
    sujetPopupRoute
} from './';

const ENTITY_STATES = [...sujetRoute, ...sujetPopupRoute];

@NgModule({
    imports: [RndManagementApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [SujetComponent, SujetDetailComponent, SujetUpdateComponent, SujetDeleteDialogComponent, SujetDeletePopupComponent],
    entryComponents: [SujetComponent, SujetUpdateComponent, SujetDeleteDialogComponent, SujetDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RndManagementApplicationSujetModule {}
