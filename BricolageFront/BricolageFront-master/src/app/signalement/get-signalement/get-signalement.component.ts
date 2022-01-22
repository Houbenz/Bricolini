import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {SignalementService} from "../../services/signalement.service";
import {Signalement} from "../../models/Signalement";
import { AdminService } from 'src/app/services/admin.service';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { DIALOG_ERROR } from 'src/app/models/TypeDialog';
import { DialogComponent } from 'src/app/dialog/dialog.component';
@Component({
  selector: 'app-get-signalement',
  templateUrl: './get-signalement.component.html',
  styleUrls: ['./get-signalement.component.css']
})
export class GetSignalementComponent implements OnInit {
  idAnnonce: number | undefined;
  signalements: Signalement[] | undefined;
  isLoading: boolean = false;

  constructor(private router: Router,
              private signalementService: SignalementService,
              private adminService: AdminService,
              private route: ActivatedRoute,
              public matDialog: MatDialog) {
  }

  ngOnInit(): void {
    this.isLoading = true;

    this.onGetSignalement();
  }


  onGetSignalement(){
    this.signalementService.getSignalements().subscribe(signalements =>{

      console.log(signalements);
      this.signalements = signalements;
      this.isLoading=false;
    }
      );
  }



  onBlock(id?: number) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.id = "dialog-component";
    dialogConfig.height = "200px";
    dialogConfig.width = "500px";
    dialogConfig.data = {
      header: "Bloquer cet Utilisateur",
      message: "Etes-vous sur de vouloir bloquer cet Utilisateur",
      type: DIALOG_ERROR
    };
    const modalDialog = this.matDialog.open(DialogComponent, dialogConfig);

    modalDialog.afterClosed().subscribe(response => {

      if (response)
        this.blockUser(id);

    });
  }

  blockUser(idBricoler?: number){

    this.adminService.blockUser(idBricoler!).subscribe(message => console.log(message));

  }

}
