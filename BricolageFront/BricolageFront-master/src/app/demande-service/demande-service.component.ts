import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AnnonceService} from "../services/annonce.service";
import {Domaine} from "../models/Domaine";
import {Annonce} from "../models/Annonce";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthenticationService} from "../services/authentication.service";

@Component({
  selector: 'app-demande-service',
  templateUrl: './demande-service.component.html',
  styleUrls: ['./demande-service.component.css']
})
export class DemandeServiceComponent implements OnInit {

  domaines: Domaine[] | undefined;
  isLoading: boolean = false;
  message: string = "";
  showMessage: boolean = false;
  imageData: any;
  currentAnnonce: Annonce | undefined;

  annonceForm = new FormGroup({
    idDomaine: new FormControl('', [Validators.required]),
    id: new FormControl(0,),
    idUser: new FormControl('',),
    titre: new FormControl('', [Validators.required]),
    date: new FormControl('', [Validators.required]),
    adresse: new FormControl('', [Validators.required]),
    budgetClient: new FormControl('', [Validators.required]),
    description: new FormControl('', [Validators.required]),
    details: new FormControl('', [Validators.required]),
  });

  constructor(private annonceService: AnnonceService,
              private router: Router,
              private authService: AuthenticationService,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.isLoading = true;
    this.getDomains();
    this.route.url.subscribe(url => {
      if (url[0].path == "modifierAnnonce") {
        this.annonceService.getAnnonce(parseInt(url[1].path)).subscribe(
          (annonce: any) => {
            this.currentAnnonce = annonce;
            this.annonceForm.setValue({
              idDomaine: annonce?.domaine?.id,
              id: annonce?.id,
              // @ts-ignore
              idUser: parseInt(localStorage.getItem("currentUserId")),
              titre: annonce.titre,
              date: annonce.date,
              adresse: annonce.adresse,
              details: annonce.details,
              description: annonce.description,
              budgetClient: annonce.budgetClient
            })
          }, () => {
          }, () => {
            this.isLoading = false;
          }
        )
      }
    })
  }

  onDemandeService() {
    const annonce: Annonce = this.annonceForm.value;
    const formData = new FormData();
    // @ts-ignore
    annonce.idUser = parseInt(localStorage.getItem("currentUserId"));
    // @ts-ignore
    formData.set('idDomaine', parseInt(annonce.idDomaine));
    // @ts-ignore
    formData.set('budgetClient', annonce.budgetClient);
    const date = new Date(annonce.date);
    formData.set('date', date.toString());
    formData.set('titre', annonce.titre);
    formData.set('details', annonce.details);
    formData.set('adresse', annonce.adresse);
    formData.set('description', annonce.description);
    // @ts-ignore
    formData.set('idUser', annonce.idUser);
    if (annonce.id == undefined)
      // @ts-ignore
      formData.set('id', 0)
    else
      // @ts-ignore
      formData.set('id', parseInt(annonce.id));
    if (this.imageData != undefined)
      formData.append('image', this.imageData);
    if (this.currentAnnonce != undefined) {
      this.modifierAnnonce(formData)
    } else {
      this.addAnnonce(formData);
      this.annonceForm.reset();
    }
  }

  modifierAnnonce(annonce: FormData) {
    this.isLoading = true;
    this.annonceService.updateAnnonce(annonce).subscribe(
      (data) => {
      }, () => {
        // to handle error
      }, () => {
        this.isLoading = false;
        this.message = "annonce modifiée avec succès";
        this.showMessage = true;
        setTimeout(() => {
          this.showMessage = false;
        }, 2000);
      });
  }

  addAnnonce(annonce: FormData) {
    this.isLoading = true;
    this.annonceService.addAnnonce(annonce).subscribe(
      (data) => {
      }, () => {
        // to handle error
      }, () => {
        this.isLoading = false;
        this.message = "annonce publiée avec succès";
        this.showMessage = true;
        setTimeout(() => {
          this.showMessage = false;
        }, 2000);
      });
  }

  getDomains() {
    this.isLoading = true;
    this.annonceService.getDomaines().subscribe(
      (data) => {
        this.domaines = data;
      }, () => {
        // to handle error
      }, () => {
        this.isLoading = false;
      });
  }

  onShowImage($event: Event) {
    // @ts-ignore
    const image = event.target.files[0];
    this.imageData = image;
  }
}
