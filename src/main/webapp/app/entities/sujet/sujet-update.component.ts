import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ISujet } from 'app/shared/model/sujet.model';
import { SujetService } from './sujet.service';
import { IProjet } from 'app/shared/model/projet.model';
import { ProjetService } from 'app/entities/projet';
import { ITheme } from 'app/shared/model/theme.model';
import { ThemeService } from 'app/entities/theme';

@Component({
    selector: 'jhi-sujet-update',
    templateUrl: './sujet-update.component.html'
})
export class SujetUpdateComponent implements OnInit {
    sujet: ISujet;
    isSaving: boolean;

    projets: IProjet[];

    themes: ITheme[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private sujetService: SujetService,
        private projetService: ProjetService,
        private themeService: ThemeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ sujet }) => {
            this.sujet = sujet;
        });
        this.projetService.query().subscribe(
            (res: HttpResponse<IProjet[]>) => {
                this.projets = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.themeService.query().subscribe(
            (res: HttpResponse<ITheme[]>) => {
                this.themes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.sujet.id !== undefined) {
            this.subscribeToSaveResponse(this.sujetService.update(this.sujet));
        } else {
            this.subscribeToSaveResponse(this.sujetService.create(this.sujet));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISujet>>) {
        result.subscribe((res: HttpResponse<ISujet>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackProjetById(index: number, item: IProjet) {
        return item.id;
    }

    trackThemeById(index: number, item: ITheme) {
        return item.id;
    }
}
