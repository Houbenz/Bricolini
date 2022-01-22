import {Component, OnInit} from '@angular/core';
import {DomaineService} from '../services/domaine.service';
import {Domaine} from '../models/Domaine';
import {DialogComponent} from '../dialog/dialog.component';
import {DIALOG_ERROR} from '../models/TypeDialog';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';

@Component({
  selector: 'app-all-domaines',
  templateUrl: './all-domaines.component.html',
  styleUrls: ['./all-domaines.component.css']
})
export class AllDomainesComponent implements OnInit {

  constructor(private domaineService: DomaineService, public matDialog: MatDialog) {
  }


  domaines: Domaine[] | undefined;

  ngOnInit(): void {
    this.loadDomaines();
  }

  loadDomaines() {
    this.domaineService.getDomaines().subscribe(domaines => this.domaines = domaines);
  }

  deleteDomaine(id: number) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.id = "dialog-component";
    dialogConfig.height = "200px";
    dialogConfig.width = "500px";
    dialogConfig.data = {
      header: "SUPPRESSION DU DOMAINE",
      message: "Etes-vous sur de vouloir supprimer ce domaine " + id,
      type: DIALOG_ERROR
    };
    const modalDialog = this.matDialog.open(DialogComponent, dialogConfig)
    modalDialog.afterClosed().subscribe(result => {

      if (result)
        this.domaineService.deleteDomaine(id).subscribe((response) => {
          if (response)
            this.domaines = this.domaines?.filter(domaine => domaine.id != id);
          else
            console.log("an error happened while trying to delete the domaine");
        });
    });
  }

}
