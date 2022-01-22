import {Component, OnInit} from '@angular/core';
import {Avis} from '../models/Avis';
import {AvisService} from '../services/avis.service';
import {ActivatedRoute, Router} from "@angular/router";
import {ShareDataService} from '../services/shareData.service';
import {MatDialog, MatDialogConfig, MatDialogRef} from '@angular/material/dialog';
import {AddSignalementComponent} from '../signalement/add-signalement/add-signalement.component';

@Component({
  selector: 'app-add-avis',
  templateUrl: './add-avis.component.html',
  styleUrls: ['./add-avis.component.css']
})

export class AddAvisComponent implements OnInit {

  idAnnonce: number = 6;//id annonce
  rate: number = 0;

  constructor(private router: Router,
              private avisService: AvisService,
              private route: ActivatedRoute,
              public shareDataService: ShareDataService,
              public dialogRef: MatDialogRef<AddAvisComponent>,
              private matDialog: MatDialog) {
  }

  onRegister(data: any) {
    let avis: Avis = {
      id: this.idAnnonce,
      rate: this.rate,
      ...data.form.value
    };

    this.avisService.addAvis(avis).subscribe(
      (data) => {
        this.closeDialog();
        this.router.navigate(['/']);
      }, (error) => {
      }, () => {
      });
  }

  ngOnInit(): void {
    this.route.url.subscribe(url => {
      this.idAnnonce = this.shareDataService.idAnnonceCurrent;//parseInt(url[1].path);
    });
  }

  showSignalement() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.height = "40%";
    dialogConfig.width = "50%";
    //dialogConfig.disableClose = true;
    dialogConfig.id = "dialog-add-signalement";
    const modalDialog = this.matDialog.open(AddSignalementComponent, dialogConfig);
  }

  closeDialog() {
    this.dialogRef.close();
  }

}
