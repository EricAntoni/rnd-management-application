import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITheme } from 'app/shared/model/theme.model';
import { ThemeService } from './theme.service';

@Component({
    selector: 'jhi-theme-update',
    templateUrl: './theme-update.component.html'
})
export class ThemeUpdateComponent implements OnInit {
    theme: ITheme;
    isSaving: boolean;

    constructor(private themeService: ThemeService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ theme }) => {
            this.theme = theme;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.theme.id !== undefined) {
            this.subscribeToSaveResponse(this.themeService.update(this.theme));
        } else {
            this.subscribeToSaveResponse(this.themeService.create(this.theme));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITheme>>) {
        result.subscribe((res: HttpResponse<ITheme>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
