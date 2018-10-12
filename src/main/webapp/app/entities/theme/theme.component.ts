import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITheme } from 'app/shared/model/theme.model';
import { Principal } from 'app/core';
import { ThemeService } from './theme.service';

@Component({
    selector: 'jhi-theme',
    templateUrl: './theme.component.html'
})
export class ThemeComponent implements OnInit, OnDestroy {
    themes: ITheme[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private themeService: ThemeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.themeService.query().subscribe(
            (res: HttpResponse<ITheme[]>) => {
                this.themes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInThemes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITheme) {
        return item.id;
    }

    registerChangeInThemes() {
        this.eventSubscriber = this.eventManager.subscribe('themeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
