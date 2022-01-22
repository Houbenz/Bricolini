import {Component, OnInit} from '@angular/core';
import {NgForm} from "@angular/forms";
import {MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-paiement',
  templateUrl: './paiement.component.html',
  styleUrls: ['./paiement.component.css']
})
export class PaiementComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<PaiementComponent>) {
  }

  ngOnInit(): void {
  }

  onPayService(updateInfoForm: NgForm) {

  }

  onClosePaiment() {
    this.dialogRef.close();
  }
}
