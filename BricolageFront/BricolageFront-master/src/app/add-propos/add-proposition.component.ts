import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {PropositionService} from '../services/proposition.service';
import {ShareDataService} from '../services/shareData.service';
import {Proposition} from '../models/Proposition';
import {Router} from "@angular/router";
import {MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-add-proposition',
  templateUrl: './add-proposition.component.html',
  styleUrls: ['./add-proposition.component.css']
})
export class AddPropositionComponent implements OnInit {

  isLoading: boolean = false;
  message: string = "";
  showMessage: boolean = false;
  propositions: Proposition[] | undefined;
  currentIdAnnonce: number | undefined;
  idAnnoncee: number = 0;

  propositionForm = new FormGroup({
    idAnnonce: new FormControl('', [Validators.required]),
    date: new FormControl('', [Validators.required]),
    price: new FormControl('', [Validators.required]),
    message: new FormControl('', [Validators.required])
  })


  constructor(private router: Router,
              private propoService: PropositionService,
              public sDSataService: ShareDataService,
              public addPropositioDialogRef: MatDialogRef<AddPropositionComponent>,) {
  }

  ngOnInit(): void {
    this.idAnnoncee = this.getShareData();
    this.getProposition();
  }

  onSendProposition() {
    let proposition: Proposition = this.propositionForm.value;
    proposition.idAnnonce = this.idAnnoncee;
    this.currentIdAnnonce = this.idAnnoncee;
    proposition.idProfessionnel = 28;

    let existPropoByUser: boolean = false;
    this.getProposition();
    this.propositions?.forEach((propo) => {
      if (propo.idAnnonce == this.currentIdAnnonce) {
        existPropoByUser = true;
      }
    })
    if (!existPropoByUser) {
      this.isLoading = true;
      this.propoService.addProposition(proposition).subscribe(
        (data) => {
        }, (error) => {
        }, () => {
          this.message = "Proposition ajoutée avec succès";
          this.showMessage = true;
          this.isLoading = false;
          this.propositionForm.reset();
          this.closeAddProposition();
          this.getProposition();
          this.getPropositionsByAnnonces();
        });
      setTimeout(() => {
        this.showMessage = false;
      }, 2000);
    } else {
      this.message = "Vous aviez déjà une proposition pour cette annonce.";
      this.showMessage = true;
      this.isLoading = false;
      this.propositionForm.reset();
      this.closeAddProposition();
      this.getPropositionsByAnnonces();
      this.getProposition();
    }
  }

  getPropositionsByAnnonces() {
    this.propoService.getPropositionsByAnnonce(this.currentIdAnnonce).subscribe(
      data => {
        this.propoService.propositions = data;
      }, () => {

      }, () => {
      }
    );
  }

  getProposition() {
    this.propoService.getPropositions().subscribe(propositions => {
      this.propoService.propositions = propositions;
    });
  }

  closeAddProposition() {
    this.addPropositioDialogRef.close()
  }

  getShareData(): number {
    return this.sDSataService.idAnnonceCurrent;
  }

}
