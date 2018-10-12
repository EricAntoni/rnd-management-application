import { NgModule } from '@angular/core';

import { RndManagementApplicationSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [RndManagementApplicationSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [RndManagementApplicationSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class RndManagementApplicationSharedCommonModule {}
