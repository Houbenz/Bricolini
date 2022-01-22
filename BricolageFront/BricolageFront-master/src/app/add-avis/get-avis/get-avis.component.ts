import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AvisService} from "../../services/avis.service";
import {Avis} from "../../models/Avis";
import {Observable} from 'rxjs';

@Component({
  selector: 'app-get-avis',
  templateUrl: './get-avis.component.html',
  styleUrls: ['./get-avis.component.css']
})
export class GetAvisComponent implements OnInit {
  idAnnonce: number | undefined;
  avis: Avis | undefined;
  isLoading: boolean = false;
  rate: number = 0;
  type: string = "client";

  constructor(private router: Router,
              private avisService: AvisService,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {

    this.isLoading = true;
    this.route.url.subscribe(url => {
      this.type = url[1].path;
      this.idAnnonce = parseInt(url[2].path);
    });

    let tempSend: Observable<Avis>;
    //exemple : http://localhost:4200/get_avis/pro/6
    //exemple : http://localhost:4200/get_avis/cli/7
    if (this.type == 'client' || this.type == "cli") {
      tempSend = this.avisService.getAvisClient(this.idAnnonce);
    } else {
      tempSend = this.avisService.getAvisPro(this.idAnnonce);
    }

    tempSend.subscribe((data: any) => {
      this.avis = data;
      this.rate = data.rate;
    }, () => {

    }, () => {
      this.isLoading = false;
    })
  }

}
