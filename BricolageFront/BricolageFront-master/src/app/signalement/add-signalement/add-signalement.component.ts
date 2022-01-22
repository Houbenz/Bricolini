import {Component, OnInit} from '@angular/core';
import {Signalement} from '../../models/Signalement';
import {SignalementService} from '../../services/signalement.service';
import {Observable} from 'rxjs';
import {ActivatedRoute, Router} from "@angular/router";
import {ShareDataService} from 'src/app/services/shareData.service';
import {MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-add-signalement',
  templateUrl: './add-signalement.component.html',
  styleUrls: ['./add-signalement.component.css']
})

export class AddSignalementComponent implements OnInit {

  idAnnonce: number = 0;//id annonce
  message: string = "";

  constructor(private router: Router,
              private signalementService: SignalementService,
              private route: ActivatedRoute,
              public shareDataService: ShareDataService,
              private dialogRef: MatDialogRef<AddSignalementComponent>) {
  }

  onRegister(data: any) {
    let signalement: Signalement = {
      idAnnonce: this.idAnnonce,
      idBricoleur: this.signalementService.idBricoleur,
      ...data.form.value
    };

    this.signalementService.addSignalement(signalement).subscribe(
      (data) => {
      }, (error) => {
      }, () => {
      });
    this.dialogRef.close();
  }

  ngOnInit(): void {
    this.route.url.subscribe(url => {
      this.idAnnonce = this.shareDataService.idAnnonceCurrent;//parseInt(url[1].path);
    });
  }

}
