import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {AnnonceService} from "../services/annonce.service";

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
})
export class DialogComponent implements OnInit {
  DIALOG_ERROR = "DIALOG_ERROR";
  DIALOG_SUCCES = "DIALOG_SUCCES";
  DIALOG_BLUE = "DIALOG_BLUE";

  constructor(public dialogRef: MatDialogRef<DialogComponent>,
              public annonceService: AnnonceService,
              @Inject(MAT_DIALOG_DATA) public data: { header: string, message: string, type: string, action: string }) {
  }

  ngOnInit(): void {
  }

  closeModal() {
    this.dialogRef.close();
  }
}
