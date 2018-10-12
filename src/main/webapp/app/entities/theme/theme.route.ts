import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Theme } from 'app/shared/model/theme.model';
import { ThemeService } from './theme.service';
import { ThemeComponent } from './theme.component';
import { ThemeDetailComponent } from './theme-detail.component';
import { ThemeUpdateComponent } from './theme-update.component';
import { ThemeDeletePopupComponent } from './theme-delete-dialog.component';
import { ITheme } from 'app/shared/model/theme.model';

@Injectable({ providedIn: 'root' })
export class ThemeResolve implements Resolve<ITheme> {
    constructor(private service: ThemeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((theme: HttpResponse<Theme>) => theme.body));
        }
        return of(new Theme());
    }
}

export const themeRoute: Routes = [
    {
        path: 'theme',
        component: ThemeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Themes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'theme/:id/view',
        component: ThemeDetailComponent,
        resolve: {
            theme: ThemeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Themes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'theme/new',
        component: ThemeUpdateComponent,
        resolve: {
            theme: ThemeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Themes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'theme/:id/edit',
        component: ThemeUpdateComponent,
        resolve: {
            theme: ThemeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Themes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const themePopupRoute: Routes = [
    {
        path: 'theme/:id/delete',
        component: ThemeDeletePopupComponent,
        resolve: {
            theme: ThemeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Themes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
