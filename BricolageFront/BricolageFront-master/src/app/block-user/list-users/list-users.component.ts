import {Component, Input, OnInit} from '@angular/core';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {DialogComponent} from 'src/app/dialog/dialog.component';
import {DIALOG_ERROR} from 'src/app/models/TypeDialog';
import {User} from 'src/app/models/User';
import {AdminService} from 'src/app/services/admin.service';

@Component({
  selector: 'app-list-users',
  templateUrl: './list-users.component.html',
  styleUrls: ['./list-users.component.css']
})
export class ListUsersComponent implements OnInit {


  @Input()
  users: User[] | undefined;
  message: string | undefined;

  constructor(private adminService: AdminService, public matDialog: MatDialog) {
  }

  ngOnInit(): void {
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


  blockUser(id?: number) {

    this.adminService.blockUser(id!).subscribe(response => {

      let user = this.users?.find(user => user.id == id);

      if (response) {
        this.message = "Compte " + user?.email + " bloqué avec succés";
        this.users = this.users?.filter(user => user.id != id);
      } else
        this.message = "Une erreur est parvenue";
    })
  }
}
