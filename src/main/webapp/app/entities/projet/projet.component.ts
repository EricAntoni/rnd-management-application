import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProjet } from 'app/shared/model/projet.model';
import { Principal } from 'app/core';
import { ProjetService } from './projet.service';

@Component({
    selector: 'jhi-projet',
    templateUrl: './projet.component.html'
})
export class ProjetComponent implements OnInit, OnDestroy {
    projets: IProjet[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private projetService: ProjetService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.projetService.query().subscribe(
            (res: HttpResponse<IProjet[]>) => {
                this.projets = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInProjets();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IProjet) {
        return item.id;
    }

    registerChangeInProjets() {
        this.eventSubscriber = this.eventManager.subscribe('projetListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
