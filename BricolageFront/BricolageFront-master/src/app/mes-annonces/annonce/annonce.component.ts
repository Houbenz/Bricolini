import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AnnonceService} from "../../services/annonce.service";
import {Annonce} from "../../models/Annonce";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {AddPropositionComponent} from "../../add-propos/add-proposition.component";
import {AuthenticationService} from "../../services/authentication.service";
import {PropositionService} from '../../services/proposition.service';
import {ShareDataService} from '../../services/shareData.service';
import {Proposition} from "../../models/Proposition";
import {AddAvisComponent} from 'src/app/add-avis/add-avis.component';
import {DevisService} from "../../services/devis.service";
import {DialogComponent} from "../../dialog/dialog.component";
import {DIALOG_BLUE} from "../../models/TypeDialog";
import {SignalementService} from "../../services/signalement.service";

@Component({
  selector: 'app-annonce',
  templateUrl: './annonce.component.html',
  styleUrls: ['./annonce.component.css']
})
export class AnnonceComponent implements OnInit {
  idAnnonce: number | undefined;
  base64Data: any;
  retrievedImage: string | undefined;
  annonce: Annonce | undefined;
  isLoading: boolean = false;
  showButtonAddPropo: boolean = true;
  errorMessage: string | undefined;
  showMessage: boolean | undefined;
  message: string | undefined;
  alreadyProposed = false;
  isDevis = false;
  propositions: Proposition[] | undefined;

  constructor(private router: Router,
              public matDialog: MatDialog,
              public devisService: DevisService,
              public auth: AuthenticationService,
              private annonceService: AnnonceService,
              public propoService: PropositionService,
              public sDSataService: ShareDataService,
              public signalementService: SignalementService,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.isLoading = true;
    this.route.url.subscribe(url => {
      this.idAnnonce = parseInt(url[1].path);
      this.setShareData(this.idAnnonce);
      this.getAnnonce();
    });
    this.showButtonPropo();
    this.getPropositionsByAnnonces();
  }

  getAnnonce() {
    this.annonceService.getAnnonce(this.idAnnonce).subscribe((data: any) => {
      this.base64Data = data?.image?.picByte;
      this.retrievedImage = 'data:image/jpeg;base64,' + this.base64Data;
      this.annonce = data;
    }, () => {

    }, () => {
      this.isLoading = false;
    })
  }

  addProposition() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.height = "55%";
    dialogConfig.width = "60%";
    dialogConfig.disableClose = true;
    dialogConfig.id = "dialog-add-proposition";
    const addPropositionDialog = this.matDialog.open(AddPropositionComponent, dialogConfig);
    if (addPropositionDialog) {
      this.getPropositionsByAnnonces();
      this.alreadyProposed = true;
    }
  }

  showAvis() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.height = "50%";
    dialogConfig.width = "50%";
    //dialogConfig.disableClose = true;
    dialogConfig.id = "dialog-add-avis";
    const modalDialog = this.matDialog.open(AddAvisComponent, dialogConfig);
  }

  showButtonPropo() {
    let propositions: Proposition[] = [];
    this.propoService.getPropositionsByAnnonce(this.idAnnonce).subscribe(
      data => {
        this.propoService.propositions = data;
        propositions = data;
        if (data.length == 0) {
          //this.showRien= true;
        } else {
          this.isDevis = data[0].devis;
        }

        let userIdVar = localStorage.getItem("currentUserId");
        let userId = parseInt(userIdVar !== null ? userIdVar : "-1");
        propositions?.forEach((propo) => {
          propo.date = String(Date.parse(propo.date))
          if (propo.idAnnonce == this.idAnnonce) {
            this.showButtonAddPropo = false;
          }
          if (propo.idProfessionnel == userId) {
            this.alreadyProposed = true;
          }
          ;
        });
      }, () => {

      }, () => {
        this.propoService.isLoading = false;
      }
    );

    propositions?.forEach((propo) => {
      if (propo.idAnnonce == this.idAnnonce) {
        this.showButtonAddPropo = false;
      }
    })

  }

  setShareData(value: number) {
    this.sDSataService.idAnnonceCurrent = value;
  }

  getPropositionsByAnnonces() {
    this.annonceService.isLoading = true;
    this.propoService.getPropositionsByAnnonce(this.idAnnonce).subscribe(
      data => {
        this.propoService.propositions = data;
        this.isDevis = data[0]?.devis;
        data.forEach(propos => {
          propos.date = new Date(propos.date).toDateString();

        });
      }, () => {

      }, () => {
        // @ts-ignore
        this.signalementService.idBricoleur = this.propoService?.propositions[0]?.idProfessionnel;
        this.annonceService.isLoading = false;
      }
    );
  }

  onSendDevis(idP: number | any,idBricoleur: number|any) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.id = "dialog-component";
    dialogConfig.height = "200px";
    dialogConfig.width = "500px";
    dialogConfig.data = {
      header: "CONFIRMATION DE LA PROPOSITION",
      message: "Etes-vous sur de vouloir confirmer cette proposition ",
      type: DIALOG_BLUE
    };
    const modalDialog = this.matDialog.open(DialogComponent, dialogConfig);
    this.signalementService.idBricoleur = idBricoleur;
/*    modalDialog.afterClosed().subscribe(result => {
      if (result) {
        this.addDevis(idP);
        this.message = "Vous venez de selectionner cette proposition pour devis. Merci";
        this.showMessage = true;
        this.isLoading = false;
      }
    });*/
  }

  addDevis(idProposition: number) {
    this.isLoading = true;
    this.devisService.addDevis(idProposition).subscribe(
      (data) => {
      }, (error) => {
      }, () => {
        this.getPropositionsByAnnonces();
      });
    setTimeout(() => {
      this.showMessage = false;
    }, 2000);
  }
}
