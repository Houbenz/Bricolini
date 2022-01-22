import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {DialogComponent} from "../dialog/dialog.component";
import {DIALOG_ERROR} from "../models/TypeDialog";
import {AuthenticationService} from '../services/authentication.service';
import {AnnonceService} from '../services/annonce.service';
import {User} from '../models/User';
import {Domaine} from '../models/Domaine';
import {Router} from "@angular/router";

@Component({
  selector: 'app-my-profile',
  templateUrl: './my-profile.component.html',
  styleUrls: ['./my-profile.component.css']
})
export class MyProfileComponent implements OnInit {

  isLoading: boolean = false;
  domaines: Domaine[] | undefined;

  message: string = "";
  showMessage: boolean = false;

  currentUser: User | undefined;
  userForm = new FormGroup({
    nom: new FormControl('',),
    prenom: new FormControl('',),
    adresse: new FormControl('',),
    phone_number: new FormControl('',),
    role: new FormControl('',),
    domaine: new FormControl('',),
    premium: new FormControl('',),
    password: new FormControl('',),
    repassword: new FormControl('',),

  });

  constructor(public matDialog: MatDialog,
              private authenticationService: AuthenticationService,
              private annonceService: AnnonceService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.isLoading = true;

    this.annonceService.getDomaines().subscribe(
      (data) => {
        this.domaines = data;
      }, () => {
        // to handle error
      }, () => {
        this.isLoading = false;
      });

    this.authenticationService.getUser().subscribe(
      (user: any) => {
        this.currentUser = user;
        this.userForm.patchValue({
          nom: user.nom,
          prenom: user.prenom,
          adresse: user.adresse,
          phone_number: user.phone_number,
          role: user.adresse,
          domaine: user.domaine,
          premium: user.premium,
          password: '',
          repassword: ''
        })
      }, () => {
      }, () => {
        this.isLoading = false;
      }
    );

  }

  onUpdateInfo(data: any) {

    let user = this.userForm.value;


    let res = this.authenticationService.updateUser(user).subscribe(
      (data) => {
      }, () => {
        this.router.navigate(['login']);
      }, () => {
        this.isLoading = false;
        this.message = "utilisateur mis à jour";
        this.showMessage = true;
        setTimeout(() => {
          this.showMessage = false;
        }, 2000);
      });
  }

  openModal() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.id = "dialog-component";
    dialogConfig.height = "200px";
    dialogConfig.width = "500px";
    dialogConfig.data = {
      header: "SUPPRESSION DU COMPTE",
      message: "Etes-vous sur de vouloir supprimer votre compte?",
      type: DIALOG_ERROR
    };
    const modalDialog = this.matDialog.open(DialogComponent, dialogConfig);
    modalDialog.afterClosed().subscribe(
      data => {
        if (data == true) {
          let res = this.authenticationService.deleteUser().subscribe(
            (data) => {
            }, () => {
              // to handle error
            }, () => {
              this.router.navigate(['login']);
              /*this.isLoading = false;
              this.message = "utilisateur mis à jour";
              this.showMessage = true;
              setTimeout(() => {
                this.showMessage = false;
              }, 2000);*/

            });
        }
      }
    );
  }
}
