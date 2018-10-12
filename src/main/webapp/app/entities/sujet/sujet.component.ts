import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISujet } from 'app/shared/model/sujet.model';
import { Principal } from 'app/core';
import { SujetService } from './sujet.service';

@Component({
    selector: 'jhi-sujet',
    templateUrl: './sujet.component.html'
})
export class SujetComponent implements OnInit, OnDestroy {
    sujets: ISujet[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private sujetService: SujetService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.sujetService.query().subscribe(
            (res: HttpResponse<ISujet[]>) => {
                this.sujets = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSujets();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISujet) {
        return item.id;
    }

    registerChangeInSujets() {
        this.eventSubscriber = this.eventManager.subscribe('sujetListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
